package com.kunpeng.framework.modules.user.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kunpeng.framework.common.cache.ProjectCache;
import com.kunpeng.framework.common.enums.LoginUserTypeEnum;
import com.kunpeng.framework.common.properties.RedisSecurityConstant;
import com.kunpeng.framework.configruation.properties.KunPengFrameworkConfig;
import com.kunpeng.framework.constant.MinioConstant;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.project.po.ProjectPO;
import com.kunpeng.framework.modules.user.mapper.UserMapper;
import com.kunpeng.framework.modules.user.po.UserPO;
import com.kunpeng.framework.modules.user.po.customer.AuthorizationCustomerPO;
import com.kunpeng.framework.modules.user.po.customer.LoginUserBO;
import com.kunpeng.framework.modules.user.po.customer.LoginUserTypeBO;
import com.kunpeng.framework.modules.user.po.customer.UserLoginCustomerPO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPMinioUtil;
import com.kunpeng.framework.utils.kptool.KPRedisUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lipeng
 * @Description 登录服务层
 * @Date 2025/3/7
 * @return
 **/
@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private KunPengFrameworkConfig kunPengFrameworkConfig;
    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private UserDetailsCheck userDetailsCheck;


    /**
     * @Author lipeng
     * @Description 普通登录
     * @Date 2025/3/7
     * @param parameter
     * @return com.kunpeng.framework.modules.user.po.customer.UserLoginCustomerPO
     **/
    public UserLoginCustomerPO login(JSONObject parameter) {
        UserPO user = KPJsonUtil.toJavaObject(parameter, UserPO.class);
        KPVerifyUtil.notNull(user.getUserName(), "请输入用户名！");
        KPVerifyUtil.notNull(user.getPassword(), "请输入密码！");
        KPVerifyUtil.notNull(parameter.getString("projectCode"), "请输入项目编号！");

        //根据项目编号 查询项目
        ProjectPO projectPO = ProjectCache.getProjectByCode(parameter.getString("projectCode"));
        if (projectPO == null)
            throw new KPServiceException("该项目不存在！");
        if (projectPO.getStatus().equals(YesNoEnum.NO.code()))
            throw new KPServiceException("该项目已停用！");

        LoginUserTypeBO loginUserTypeBO = new LoginUserTypeBO()
                .setLoginType(LoginUserTypeEnum.COMMON.code())
                .setIdentification(user.getUserName())
                .setProjectId(projectPO.getProjectId())
                .setProjectCode(projectPO.getProjectCode())
                .setCheck(user.getPassword());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(KPJsonUtil.toJson(loginUserTypeBO), user.getPassword());
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication == null) throw new KPServiceException("登录校验失败");
        //获取用户信息
        LoginUserBO loginUserBO = (LoginUserBO) authentication.getPrincipal();
        UserLoginCustomerPO loginCustomerPO = KPJsonUtil.toJavaObject(loginUserBO.getUser(), UserLoginCustomerPO.class);
        loginCustomerPO.setAccessToken(KPRedisUtil.getString(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification()));
        //刷新的token  未启用
        //        loginCustomerPO.setRefreshToken(KPRedisUtil.get(RedisSecurityConstant.REDIS_AUTHENTICATION_REFRESHTOKEN + loginUserTypeBO.getIdentification() + ":" + loginUserTypeBO.getProjectId()));
        if (KPStringUtil.isNotEmpty(loginUserBO.getRoles()))
            loginCustomerPO.setRoles(loginUserBO.getRoleKeys());
        loginCustomerPO.setPermissions(loginUserBO.getPermissions());

        loginCustomerPO.setAvatar(KPMinioUtil.getUrl(MinioConstant.AUTH_BUCKET_NAME, loginCustomerPO.getAvatar(), 168));

        return loginCustomerPO;
    }


    /**
     * @Author lipeng
     * @Description 免密登录
     * @Date 2025/6/4
     * @param parameter
     * @return com.kunpeng.framework.modules.user.po.customer.UserLoginCustomerPO
     **/
    public UserLoginCustomerPO exemptLogin(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("projectId"), "请输入项目id！");
        KPVerifyUtil.twoChoiceOne(parameter.getString("jobNumber"), "工号", parameter.getString("userName"), "账号", false);

        //根据项目编号 查询项目
        ProjectPO projectPO = ProjectCache.getProjectByProjectId(parameter.getString("projectId"));
        if (projectPO == null)
            throw new KPServiceException("该项目不存在！");
        if (projectPO.getStatus().equals(YesNoEnum.NO.code()))
            throw new KPServiceException("该项目已停用！");

        if (!kunPengFrameworkConfig.getGreenChannelLogin().contains(projectPO.getProjectCode()))
            throw new KPServiceException("该项目不支持免密登录！");

        if (KPStringUtil.isNotEmpty(parameter.getString("jobNumber"))) {
            List<UserPO> userPOList = userMapper.selectList(Wrappers.lambdaQuery(UserPO.class)
                    .eq(UserPO::getJobNumber, parameter.getString("jobNumber")));
            if (KPStringUtil.isEmpty(userPOList)) throw new KPServiceException("用户不存在");
            if (userPOList.size() > 1) throw new KPServiceException("用户异常，请联系管理员");
            parameter.put("userName", userPOList.get(0).getUserName());
        }

        LoginUserTypeBO loginUserTypeBO = new LoginUserTypeBO()
                .setLoginType(LoginUserTypeEnum.NOT_PASWORD.code())
                .setIdentification(parameter.getString("userName"))
                .setCheck("notPasswordAutomaticLogon")
                .setProjectId(projectPO.getProjectId())
                .setProjectCode(projectPO.getProjectCode());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(KPJsonUtil.toJson(loginUserTypeBO), "notPasswordAutomaticLogon");
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication == null) throw new KPServiceException("登录校验失败");
        //获取用户信息
        LoginUserBO loginUserBO = (LoginUserBO) authentication.getPrincipal();
//        LoginUserBO loginUserBO = (LoginUserBO) userDetailsCheck.loadUserByUsername(KPJsonUtil.toJsonString(loginUserTypeBO));
        if (loginUserBO == null) throw new KPServiceException("登录校验失败");
        UserLoginCustomerPO loginCustomerPO = KPJsonUtil.toJavaObject(loginUserBO.getUser(), UserLoginCustomerPO.class);
        loginCustomerPO.setAccessToken(KPRedisUtil.getString(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification()));
        //刷新的token  未启用
        //        loginCustomerPO.setRefreshToken(KPRedisUtil.get(RedisSecurityConstant.REDIS_AUTHENTICATION_REFRESHTOKEN + loginUserTypeBO.getIdentification() + ":" + loginUserTypeBO.getProjectId()));
        if (KPStringUtil.isNotEmpty(loginUserBO.getRoles()))
            loginCustomerPO.setRoles(loginUserBO.getRoleKeys());
        loginCustomerPO.setPermissions(loginUserBO.getPermissions());

        loginCustomerPO.setAvatar(KPMinioUtil.getUrl(MinioConstant.AUTH_BUCKET_NAME, loginCustomerPO.getAvatar(), 168));
        return loginCustomerPO;
    }


    /**
     * @Author lipeng
     * @Description 授权登录
     * @Date 2025/6/9
     * @param parameter
     * @return com.kunpeng.framework.modules.user.po.customer.AuthorizationCustomerPO
     **/
    public AuthorizationCustomerPO authorization(JSONObject parameter) {
        ProjectPO projectParameter = KPJsonUtil.toJavaObject(parameter, ProjectPO.class);
        KPVerifyUtil.notNull(projectParameter.getAppId(), "请输入appId！");
        KPVerifyUtil.notNull(projectParameter.getAppSecret(), "请输入appSecret！");

        //根据项目编号 查询项目
        ProjectPO projectPO = ProjectCache.getProjectByAppId(projectParameter.getAppId());
        if (projectPO == null) throw new KPServiceException("该项目不存在！");
        if (projectPO.getStatus().equals(YesNoEnum.NO.code())) throw new KPServiceException("该项目已停用！");

        LoginUserTypeBO loginUserTypeBO = new LoginUserTypeBO()
                .setLoginType(LoginUserTypeEnum.AUTHORIZATION.code())
                .setIdentification(projectParameter.getAppId())
                .setProjectId(projectPO.getProjectId())
                .setCheck(projectParameter.getAppSecret())
                .setProjectCode(projectPO.getProjectCode());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(KPJsonUtil.toJson(loginUserTypeBO), projectParameter.getAppSecret());
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication == null) throw new KPServiceException("登录校验失败");

        Integer loginNum = 0;

        if (KPRedisUtil.hasKey(RedisSecurityConstant.AUTHORIZATION_NUMBER + ":" + projectPO.getProjectName() + ":" + projectPO.getProjectCode()))
            loginNum = Integer.valueOf(KPRedisUtil.getString(RedisSecurityConstant.AUTHORIZATION_NUMBER + ":" + projectPO.getProjectName() + ":" + projectPO.getProjectCode()));


        //获取用户信息
        LoginUserBO loginUserBO = (LoginUserBO) authentication.getPrincipal();
        return new AuthorizationCustomerPO()
                .setAccessToken(KPRedisUtil.getString(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification()))
                .setInvalidTime(projectPO.getTokenFailure() * 60 * 60)
                .setProjectCode(loginUserBO.getIdentification())
                .setProjectName(loginUserBO.getName())
                .setRemainingNum(projectPO.getTokenGainMaxNum() - loginNum)
                .setPermissions(loginUserBO.getPermissions());
    }
}
