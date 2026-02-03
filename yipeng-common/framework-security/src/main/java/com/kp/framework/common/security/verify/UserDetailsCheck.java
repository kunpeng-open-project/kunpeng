package com.kp.framework.common.security.verify;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.common.enums.AuthCodeEnum;
import com.kp.framework.common.enums.LoginUserStatusEnum;
import com.kp.framework.common.enums.LoginUserTypeEnum;
import com.kp.framework.common.properties.KPTokenProperties;
import com.kp.framework.common.properties.KPUserProperties;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.common.security.exception.AuthenticationServiceException;
import com.kp.framework.common.util.CommonUtil;
import com.kp.framework.common.util.KPJWTUtil;
import com.kp.framework.modules.dept.mapper.AuthDeptMapper;
import com.kp.framework.modules.dept.po.AuthDeptPO;
import com.kp.framework.modules.menu.mapper.AuthMenuMapper;
import com.kp.framework.modules.menu.po.AuthMenuPO;
import com.kp.framework.modules.project.mapper.AuthProjectMapper;
import com.kp.framework.modules.project.po.AuthProjectMenuPO;
import com.kp.framework.modules.project.po.AuthProjectPO;
import com.kp.framework.modules.role.mapper.AuthRoleMapper;
import com.kp.framework.modules.role.po.AuthRoleMenuPO;
import com.kp.framework.modules.role.po.AuthRolePO;
import com.kp.framework.modules.role.po.AuthRoleProjectRelevancePO;
import com.kp.framework.modules.user.mapper.AuthLoginRecordMapper;
import com.kp.framework.modules.user.mapper.AuthUserDeptMapper;
import com.kp.framework.modules.user.mapper.AuthUserMapper;
import com.kp.framework.modules.user.po.AuthLoginRecordPO;
import com.kp.framework.modules.user.po.AuthUserDeptPO;
import com.kp.framework.modules.user.po.AuthUserPO;
import com.kp.framework.modules.user.po.AuthUserRolePO;
import com.kp.framework.modules.user.po.customer.LoginUserBO;
import com.kp.framework.modules.user.po.customer.LoginUserDeptPO;
import com.kp.framework.modules.user.po.customer.LoginUserTypeBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户验证处理。
 * @author lipeng
 * 2024/4/21
 */
@Service
public class UserDetailsCheck implements UserDetailsService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthMenuMapper authMenuMapper;

    @Autowired
    private KPTokenProperties kpTokenProperties;

    @Autowired
    private KPUserProperties kpUserProperties;

    @Autowired
    private AuthLoginRecordMapper authLoginRecordMapper;

    @Autowired
    private AuthProjectMapper authProjectMapper;

    @Autowired
    private AuthUserDeptMapper authUserDeptMapper;

    @Autowired
    private AuthDeptMapper authDeptMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserTypeBO loginUserTypeBO = CommonUtil.toJavaObject(username, LoginUserTypeBO.class);

        if (Arrays.asList(LoginUserTypeEnum.COMMON.code(), LoginUserTypeEnum.NOT_PASWORD.code(), LoginUserTypeEnum.SSO_LOGIN.code()).contains(loginUserTypeBO.getLoginType())) {
            AuthLoginRecordPO loginRecordPO = new AuthLoginRecordPO(loginUserTypeBO.getProjectId(), loginUserTypeBO.getIdentification(), loginUserTypeBO.getLoginType());
            try {
                LoginUserBO loginUserBO = this.common(loginUserTypeBO, loginRecordPO);
                this.saveLoginRecord(loginRecordPO, "登录成功");
                return loginUserBO;
            } catch (Exception ex) {
                if (ex instanceof UsernameNotFoundException) {
                    this.saveLoginRecord(loginRecordPO, "登录失败, " + ex.getMessage());
                    throw new AuthenticationServiceException(AuthCodeEnum.ACCOUNT_NUMBER_NULL.code(), ex.getMessage());
                }
                if (ex instanceof BadCredentialsException) {
                    this.saveLoginRecord(loginRecordPO, "登录失败, " + ex.getMessage());
                    throw new AuthenticationServiceException(AuthCodeEnum.INVALID.code(), ex.getMessage());
                }

                if (ex instanceof DisabledException) {
                    this.saveLoginRecord(loginRecordPO, "登录失败, " + AuthCodeEnum.FORBIDDEN_ACCOUNT.message());
                    throw new AuthenticationServiceException(AuthCodeEnum.FORBIDDEN_ACCOUNT.code(), AuthCodeEnum.FORBIDDEN_ACCOUNT.message());
                }

                if (ex instanceof AccountExpiredException) {
                    this.saveLoginRecord(loginRecordPO, "登录失败, " + AuthCodeEnum.REMOVED.message());
                    throw new AuthenticationServiceException(AuthCodeEnum.REMOVED.code(), AuthCodeEnum.REMOVED.message());
                }

                if (ex instanceof LockedException) {
                    this.saveLoginRecord(loginRecordPO, "登录失败, " + ex.getMessage());
                    throw new AuthenticationServiceException(AuthCodeEnum.LOCK_ACCOUNT.code(), ex.getMessage());
                }
            }
        } else if (loginUserTypeBO.getLoginType().equals(LoginUserTypeEnum.AUTHORIZATION.code())) {
            AuthLoginRecordPO loginRecordPO = new AuthLoginRecordPO(loginUserTypeBO.getProjectId(), loginUserTypeBO.getIdentification(), loginUserTypeBO.getLoginType());

            try {
                LoginUserBO loginUserBO = this.authorization(loginUserTypeBO);
                this.saveLoginRecord(loginRecordPO, "登录成功");
                return loginUserBO;
            } catch (Exception ex) {
                if (ex instanceof UsernameNotFoundException) {
                    this.saveLoginRecord(loginRecordPO, "登录失败, " + ex.getMessage());
                    throw new AuthenticationServiceException(AuthCodeEnum.ACCOUNT_NUMBER_NULL.code(), ex.getMessage());
                }
                if (ex instanceof BadCredentialsException) {
                    this.saveLoginRecord(loginRecordPO, "登录失败, " + ex.getMessage());
                    throw new AuthenticationServiceException(AuthCodeEnum.INVALID.code(), ex.getMessage());
                }
                if (ex instanceof LockedException) {
                    this.saveLoginRecord(loginRecordPO, "登录失败, " + ex.getMessage());
                    throw new AuthenticationServiceException(AuthCodeEnum.LOCK_ACCOUNT.code(), ex.getMessage());
                }
            }

        }
        return null;
    }

    /**
     * 普通登录。
     * @author lipeng
     * 2024/4/22
     * @param loginUserTypeBO 登录用户
     * @param loginRecordPO 登录记录
     * @return com.kp.framework.modules.user.po.customer.LoginUserBO
     */
    private LoginUserBO common(LoginUserTypeBO loginUserTypeBO, AuthLoginRecordPO loginRecordPO) throws UsernameNotFoundException {
        LambdaQueryWrapper<AuthUserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AuthUserPO::getUserName, loginUserTypeBO.getIdentification());

        List<AuthUserPO> userPOList = authUserMapper.selectList(queryWrapper);
        if (userPOList.size() == 0) throw new UsernameNotFoundException("用户：" + loginUserTypeBO.getIdentification() + " 不存在");
        if (userPOList.size() > 1) throw new UsernameNotFoundException("用户：" + loginUserTypeBO.getIdentification() + " 账号异常！");

        LoginUserBO loginUserBO = new LoginUserBO(userPOList.get(0));
        loginUserBO.setName(loginUserBO.getUser().getRealName());
        loginUserBO.setIdentification(loginUserBO.getUser().getUserId());

        //设置登录日志
        loginRecordPO.setUserId(userPOList.get(0).getUserId());


        if (loginUserBO.getUser().getStatus().equals(LoginUserStatusEnum.FORBIDDEN.code()))
            throw new DisabledException("用户：" + loginUserTypeBO.getIdentification() + "已被禁用");
        if (loginUserBO.getUser().getStatus().equals(LoginUserStatusEnum.LOGOUT.code()))
            throw new AccountExpiredException("用户：" + loginUserTypeBO.getIdentification() + "已被注销");
        if (loginUserBO.getUser().getStatus().equals(LoginUserStatusEnum.LOCK.code())) {
            Date jianSuoTime = CommonUtil.dateAddMinutes(CommonUtil.LocalDateTimeByDate(loginUserBO.getUser().getLockDate()), kpUserProperties.getLockTime());
            if (CommonUtil.dateCompare(new Date(), jianSuoTime) == -1)
                throw new LockedException(CommonUtil.format("用户：{0} 已被锁定, {1} 后解锁", loginUserTypeBO.getIdentification(), CommonUtil.formatDuration(CommonUtil.getSecondsBetween(LocalDateTime.now(), CommonUtil.dateByLocalDateTime(jianSuoTime)) * 1000)));

            AuthUserPO authUserPO = new AuthUserPO();
            authUserPO.setUserId(loginUserBO.getUser().getUserId());
            authUserPO.setStatus(LoginUserStatusEnum.NORMAL.code());
            authUserMapper.updateById(authUserPO);
            CommonUtil.remove(RedisSecurityConstant.LOGIN_ERROR_NUMBER + ":" + loginUserBO.getUser().getUserName());
        }

        switch (LoginUserTypeEnum.getCode(loginUserTypeBO.getLoginType())) {
            case COMMON: // 普通密码登录
                if (!new BCryptPasswordEncoder().matches(loginUserTypeBO.getCheck(), loginUserBO.getPassword())) {
                    throw new BadCredentialsException(this.loginError(loginUserBO.getUser().getUserId()));
                }
                break;
            case NOT_PASWORD: //免密登录
            case SSO_LOGIN: //单点登录
                //这样做为了登录的时候使用
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(KPJsonUtil.toJson(loginUserTypeBO), "notPasswordAutomaticLogon");
//                // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
//                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                loginUserBO.getUser().setPassword(new BCryptPasswordEncoder().encode(loginUserTypeBO.getCheck()));
//                如果没有这个操作 登录只能
//                LoginUserBO loginUserBO = (LoginUserBO) userDetailsCheck.loadUserByUsername(KPJsonUtil.toJsonString(loginUserTypeBO));
                break;
        }

        //查询当前用户所登录的项目中所有角色
        List<AuthRolePO> rolePOList = authRoleMapper.selectJoinList(AuthRolePO.class, new MPJLambdaWrapper<AuthRolePO>()
                .selectAll(AuthRolePO.class)
                .leftJoin(AuthUserRolePO.class, "userRole", on -> on
                        .eq(AuthUserRolePO::getRoleId, AuthRolePO::getRoleId)
                        .eq(AuthUserRolePO::getDeleteFlag, 0)
                )
                .leftJoin(AuthRoleProjectRelevancePO.class, "roleProjectRelevance", on -> on
                        .eq(AuthRoleProjectRelevancePO::getRoleId, AuthRolePO::getRoleId)
                        .eq(AuthRoleProjectRelevancePO::getDeleteFlag, 0)
                )
                .disableSubLogicDel()
                .eq(AuthUserRolePO::getUserId, loginUserBO.getUser().getUserId())
                .eq(AuthRolePO::getStatus, 1)
                .eq(AuthRoleProjectRelevancePO::getProjectId, loginUserTypeBO.getProjectId()));

        if (rolePOList.size() != 0) {
            loginUserBO.setRoles(rolePOList);
            loginUserBO.setRoleKeys(rolePOList.stream().map(AuthRolePO::getRoleCode).filter(StringUtils::isNotEmpty).collect(Collectors.toList()));

            List<String> roleIds = rolePOList.stream().map(AuthRolePO::getRoleId).collect(Collectors.toList());
            loginUserBO.setAuthorities(rolePOList.stream().map(authRolePO -> new SimpleGrantedAuthority(authRolePO.getRoleName())).collect(Collectors.toList()));

            //查询权限
            List<AuthMenuPO> menuPOList = authMenuMapper.selectJoinList(AuthMenuPO.class, new MPJLambdaWrapper<AuthMenuPO>()
                    .selectAll(AuthMenuPO.class)
                    .leftJoin(AuthRoleMenuPO.class, "roleMenu", on -> on
                            .eq(AuthRoleMenuPO::getMenuId, AuthMenuPO::getMenuId)
                            .eq(AuthRoleMenuPO::getDeleteFlag, 0)
                    )
                    .disableSubLogicDel()
                    //后期是否隔离 有待商量
                    .eq(AuthRoleMenuPO::getProjectId, loginUserTypeBO.getProjectId())
                    .in(AuthRoleMenuPO::getRoleId, roleIds));
            if (menuPOList.size() != 0) {
                List<String> permissions = menuPOList.stream().filter(authMenuPO -> StringUtils.isNotEmpty(authMenuPO.getPerms())).map(AuthMenuPO::getPerms).distinct().collect(Collectors.toList());
                loginUserBO.setPermissions(permissions);
            }
        }

        //查询当前用户所属部门
        loginUserBO.setDeptList(authDeptMapper.selectJoinList(LoginUserDeptPO.class, new MPJLambdaWrapper<AuthDeptPO>()
                .selectAll(AuthDeptPO.class)
                .select(AuthUserDeptPO::getPrincipal)
                .leftJoin(AuthUserDeptPO.class, "userDept", on -> on
                        .eq(AuthUserDeptPO::getDeptId, AuthDeptPO::getDeptId)
                        .eq(AuthUserDeptPO::getDeleteFlag, 0)
                )
                .disableSubLogicDel()
                .eq(AuthUserDeptPO::getUserId, loginUserBO.getUser().getUserId())
        ));

        //查询用户子部门
        if (loginUserBO.getDeptList().size() != 0) {
            //查询用户的子部门
            loginUserBO.setSubDeptList(authDeptMapper.selectJoinList(LoginUserDeptPO.class, new MPJLambdaWrapper<AuthDeptPO>()
                    .selectAll(AuthDeptPO.class)
                    .distinct()
                    .select(AuthUserDeptPO::getPrincipal)
                    .leftJoin(AuthUserDeptPO.class, "userDept", on -> on
                            .eq(AuthUserDeptPO::getDeptId, AuthDeptPO::getDeptId)
                            .eq(AuthUserDeptPO::getDeleteFlag, 0)
                    )
                    .disableSubLogicDel()
                    .in(AuthDeptPO::getParentId, loginUserBO.getDeptList().stream().map(LoginUserDeptPO::getDeptId).collect(Collectors.toList()))
            ));

//            authUserDeptMapper.selectJoinList(LoginUserDeptPO.class, new MPJLambdaWrapper<AuthUserDeptPO>()
//                    .selectAll(AuthUserDeptPO.class)
//                    .select("userDept.dept_name")
//                    .leftJoin(AuthDeptPO.class, "userDept", on -> on
//                            .eq(AuthDeptPO::getDeptId, AuthUserDeptPO::getDeptId)
//                            .eq(AuthDeptPO::getDeleteFlag, 0)
//                    )
//                    .disableSubLogicDel()
//                    .in(AuthDeptPO::getParentId, loginUserBO.getDeptList().stream().map(LoginUserDeptPO::getDeptId).collect(Collectors.toList()))
//            )
        }

        String loginIp = CommonUtil.getClinetIP(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        LocalDateTime loginDate = LocalDateTime.now();


        loginUserBO.getUser().setLoginIp(loginIp);
        loginUserBO.getUser().setLoginDate(loginDate);

        String accessToken = KPJWTUtil.createToken(CommonUtil.toJsonString(loginUserTypeBO), KPJWTUtil.DAY_1);
        String refreshToken = KPJWTUtil.createToken(CommonUtil.toJsonString(loginUserTypeBO), KPJWTUtil.DAY_10);
        CommonUtil.set(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), accessToken, kpTokenProperties.getExpireTime());
        CommonUtil.set(RedisSecurityConstant.REDIS_AUTHENTICATION_REFRESHTOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), refreshToken, kpTokenProperties.getExpireTime() * 10);
        CommonUtil.set(RedisSecurityConstant.REDIS_AUTHENTICATION_LOGINUSER_MESSAGE + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), CommonUtil.toJsonString(loginUserBO), kpTokenProperties.getExpireTime());


        //删除失败记录
        CommonUtil.remove(RedisSecurityConstant.LOGIN_ERROR_NUMBER + ":" + loginUserBO.getUser().getUserName());
        //记录登录信息
        AuthUserPO authUserPO = new AuthUserPO();
        authUserPO.setUserId(loginUserBO.getUser().getUserId());
        authUserPO.setLoginIp(loginIp);
        authUserPO.setLoginDate(loginDate);
        authUserMapper.updateById(authUserPO);
        return loginUserBO;
    }

    /**
     * 授权登录。
     * @author lipeng
     * 2024/7/10
     * @param loginUserTypeBO 登录用户
     * @return com.kp.framework.modules.user.po.customer.LoginUserBO
     */
    private LoginUserBO authorization(LoginUserTypeBO loginUserTypeBO) {
        LambdaQueryWrapper<AuthProjectPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AuthProjectPO::getAppId, loginUserTypeBO.getIdentification());
        List<AuthProjectPO> authProjectPOList = authProjectMapper.selectList(queryWrapper);
        if (authProjectPOList.size() == 0) throw new UsernameNotFoundException("项目：" + loginUserTypeBO.getIdentification() + " 不存在");
        if (authProjectPOList.size() > 1) throw new UsernameNotFoundException("项目：" + loginUserTypeBO.getIdentification() + " 异常，请联系管理员！");

        AuthProjectPO authProjectPO = authProjectPOList.get(0);

        AuthUserPO authUserPO = new AuthUserPO();
        authUserPO.setPassword(authProjectPO.getVoucher());
        LoginUserBO loginUserBO = new LoginUserBO(authUserPO);
        loginUserBO.setName(authProjectPO.getProjectName());
        loginUserBO.setIdentification(authProjectPO.getProjectCode());


        Integer loginNum = 0;
        if (CommonUtil.hasKey(RedisSecurityConstant.AUTHORIZATION_NUMBER + ":" + authProjectPO.getProjectName() + ":" + authProjectPO.getProjectCode()))
            loginNum = Integer.valueOf(CommonUtil.get(RedisSecurityConstant.AUTHORIZATION_NUMBER + ":" + authProjectPO.getProjectName() + ":" + authProjectPO.getProjectCode()));

        if (!authProjectPO.getAppSecret().equals(loginUserTypeBO.getCheck()))
            throw new BadCredentialsException("认证失败, 请检查appId 和 appSecret是否正确");
        if (loginNum >= authProjectPO.getTokenGainMaxNum())
            throw new LockedException("今日授权次数已用完！");
        if (!new BCryptPasswordEncoder().matches(loginUserTypeBO.getCheck(), authProjectPO.getVoucher()))
            throw new BadCredentialsException("加密认证失败, 请检查appId 和 appSecret是否正确");


        //查询权限
        MPJLambdaWrapper<AuthMenuPO> queryMenuWrapper = new MPJLambdaWrapper<AuthMenuPO>()
                .selectAll(AuthMenuPO.class)
                .leftJoin(AuthProjectMenuPO.class, "projectMenu", on -> on
                        .eq(AuthProjectMenuPO::getMenuId, AuthMenuPO::getMenuId)
                        .eq(AuthProjectMenuPO::getDeleteFlag, 0)
                )
                .disableSubLogicDel()
                .in(AuthProjectMenuPO::getProjectId, authProjectPO.getProjectId());

        List<AuthMenuPO> menuPOList = authMenuMapper.selectJoinList(AuthMenuPO.class, queryMenuWrapper);
        if (menuPOList.size() != 0) {
            List<String> permissions = menuPOList.stream().filter(authMenuPO -> StringUtils.isNotEmpty(authMenuPO.getPerms())).map(AuthMenuPO::getPerms).distinct().collect(Collectors.toList());
            loginUserBO.setPermissions(permissions);
        }


        Integer invalidTime = authProjectPO.getTokenFailure() * 60 * 60 + 300;
        String accessToken = KPJWTUtil.createToken(CommonUtil.toJsonString(loginUserTypeBO), invalidTime);
        String refreshToken = KPJWTUtil.createToken(CommonUtil.toJsonString(loginUserTypeBO), KPJWTUtil.DAY_10);
        CommonUtil.set(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), accessToken, invalidTime);
        CommonUtil.set(RedisSecurityConstant.REDIS_AUTHENTICATION_REFRESHTOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), refreshToken, invalidTime * 10);
        CommonUtil.set(RedisSecurityConstant.REDIS_AUTHENTICATION_LOGINUSER_MESSAGE + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification(), CommonUtil.toJsonString(loginUserBO), invalidTime);

        //记录登录次数
        CommonUtil.set(RedisSecurityConstant.AUTHORIZATION_NUMBER + ":" + authProjectPO.getProjectName() + ":" + authProjectPO.getProjectCode(), String.valueOf(++loginNum), CommonUtil.secondsUntilEndOfDay());
        return loginUserBO;
    }

    /**
     * 登录失败。
     * @author lipeng
     * 2024/6/26
     * @param userId 用户ID
     * @return java.lang.String
     */
    private String loginError(String userId) {
        AuthUserPO authUserPO = authUserMapper.selectById(userId);
        if (authUserPO == null) return "用户不存在";

        Integer errorNumber = 1;
        if (!CommonUtil.hasKey(RedisSecurityConstant.LOGIN_ERROR_NUMBER + ":" + authUserPO.getUserName())) {
            CommonUtil.set(RedisSecurityConstant.LOGIN_ERROR_NUMBER + ":" + authUserPO.getUserName(), errorNumber.toString());
            return CommonUtil.format("密码错误,您还有{0}次机会", kpUserProperties.getErrorNumber() - errorNumber);
        }


        errorNumber = Integer.valueOf(CommonUtil.get(RedisSecurityConstant.LOGIN_ERROR_NUMBER + ":" + authUserPO.getUserName())) + 1;
        CommonUtil.set(RedisSecurityConstant.LOGIN_ERROR_NUMBER + ":" + authUserPO.getUserName(), errorNumber.toString());

        if (errorNumber >= kpUserProperties.getErrorNumber()) {
            authUserPO.setStatus(LoginUserStatusEnum.LOCK.code());
            authUserPO.setLockDate(LocalDateTime.now());
            authUserMapper.updateById(authUserPO);
        }
        return CommonUtil.format("密码错误,您还有{0}次机会", kpUserProperties.getErrorNumber() - errorNumber);
    }

    /**
     * 记录登录日志。
     * @author lipeng
     * 2024/6/26
     * @param loginRecordPO 登录日志
     * @param loginResult 登录结果
     */
    private void saveLoginRecord(AuthLoginRecordPO loginRecordPO, String loginResult) {
        loginRecordPO.setLoginResult(loginResult);
        authLoginRecordMapper.insert(loginRecordPO);
    }
}
