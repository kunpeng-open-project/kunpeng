package com.kp.framework.modules.user.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.common.cache.ProjectCache;
import com.kp.framework.common.enums.LoginUserStatusEnum;
import com.kp.framework.common.properties.KPUserProperties;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.constant.MinioConstant;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.controller.server.EhcacheService;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.entity.bo.PageBO;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.dept.mapper.customer.DeptCustomerMapper;
import com.kp.framework.modules.dept.po.DeptPO;
import com.kp.framework.modules.post.po.PostPO;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.modules.role.po.RolePO;
import com.kp.framework.modules.user.enums.UserStatusEnum;
import com.kp.framework.modules.user.mapper.UserMapper;
import com.kp.framework.modules.user.mapper.UserRoleMapper;
import com.kp.framework.modules.user.po.UserDeptPO;
import com.kp.framework.modules.user.po.UserPO;
import com.kp.framework.modules.user.po.UserPostPO;
import com.kp.framework.modules.user.po.UserProjectPO;
import com.kp.framework.modules.user.po.UserRolePO;
import com.kp.framework.modules.user.po.customer.LoginUserBO;
import com.kp.framework.modules.user.po.customer.UserDetailsCustomerPO;
import com.kp.framework.modules.user.po.customer.UserListCustomerPO;
import com.kp.framework.modules.user.po.param.UserEditParamPO;
import com.kp.framework.modules.user.po.param.UserListParamPO;
import com.kp.framework.modules.user.util.UserUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPMinioUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPServiceUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lipeng
 * @Description 用户信息表 服务实现类
 * @Date 2025-04-21
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, UserPO> {

    @Autowired
    private DeptCustomerMapper deptCustomerMapper;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private KPUserProperties kpUserProperties;

    /**
     * @Author lipeng
     * @Description 查询用户信息列表
     * @Date 2025-04-21
     * @param userListParamPO
     * @return java.util.List<UserListCustomerPO>
     **/
    public List<UserListCustomerPO> queryPageList(UserListParamPO userListParamPO) {
        List<String> deptList = new ArrayList<>();
        if (KPStringUtil.isNotEmpty(userListParamPO.getDeptId()))
            deptList = deptCustomerMapper.queryDepeSubsetId(userListParamPO.getDeptId());

        MPJLambdaWrapper<UserPO> wrapper = new MPJLambdaWrapper<UserPO>("user")
                .selectAll(UserPO.class, "user")
                .select("GROUP_CONCAT(DISTINCT post.post_name SEPARATOR ', ' ) AS postNames")
                .select("GROUP_CONCAT(DISTINCT dept.dept_name SEPARATOR ', ' ) AS deptNames")
                .leftJoin(UserPostPO.class, on -> on
                        .eq(UserPostPO::getUserId, UserPO::getUserId)
                        .eq(UserPostPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(PostPO.class, "post", on -> on
                        .eq(PostPO::getPostId, UserPostPO::getPostId)
                        .eq(PostPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(UserDeptPO.class, on -> on
                        .eq(UserDeptPO::getUserId, UserPO::getUserId)
                        .eq(UserDeptPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(DeptPO.class, "dept", on -> on
                        .eq(DeptPO::getDeptId, UserDeptPO::getDeptId)
                        .eq(DeptPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(UserRolePO.class, "userRole", on -> on
                        .eq(UserRolePO::getUserId, UserPO::getUserId)
                        .eq(UserRolePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .groupBy(UserPO::getUserId)
                .like(KPStringUtil.isNotEmpty(userListParamPO.getUserName()), UserPO::getUserName, userListParamPO.getUserName())
                .like(KPStringUtil.isNotEmpty(userListParamPO.getJobNumber()), UserPO::getJobNumber, userListParamPO.getJobNumber())
                .like(KPStringUtil.isNotEmpty(userListParamPO.getPhoneNumber()), UserPO::getPhoneNumber, userListParamPO.getPhoneNumber())
                .like(KPStringUtil.isNotEmpty(userListParamPO.getIdCard()), UserPO::getIdCard, userListParamPO.getIdCard())
                .eq(KPStringUtil.isNotEmpty(userListParamPO.getSex()), UserPO::getSex, userListParamPO.getSex())
                .eq(KPStringUtil.isNotEmpty(userListParamPO.getStatus()), UserPO::getStatus, userListParamPO.getStatus())
                .eq(KPStringUtil.isNotEmpty(userListParamPO.getUserStatus()), UserPO::getUserStatus, userListParamPO.getUserStatus())
                .in(KPStringUtil.isNotEmpty(userListParamPO.getDeptId()), UserDeptPO::getDeptId, deptList)
                .in(KPStringUtil.isNotEmpty(userListParamPO.getRoleId()), UserRolePO::getRoleId, userListParamPO.getRoleId())
                .notIn(KPStringUtil.isNotEmpty(userListParamPO.getNeUserIds()), UserPO::getUserId, userListParamPO.getNeUserIds())
                .in(KPStringUtil.isNotEmpty(userListParamPO.getEqUserIds()), UserRolePO::getUserId, userListParamPO.getEqUserIds());

        if (KPStringUtil.isNotEmpty(userListParamPO.getName())) {
            wrapper.and(e -> e.like(UserPO::getRealName, userListParamPO.getName())
                    .or()
                    .like(UserPO::getNickName, userListParamPO.getName()));
        }

        Page page = PageHelper.startPage(userListParamPO.getPageNum(), userListParamPO.getPageSize(), userListParamPO.getOrderBy(UserPO.class));
        page.setCountColumn("distinct user_id");
        List<UserListCustomerPO> row = this.baseMapper.selectJoinList(UserListCustomerPO.class, wrapper);

        row.forEach(userListCustomerPO -> {
            userListCustomerPO.setAvatar(KPMinioUtil.getUrl(userListCustomerPO.getAvatar(), 24));
        });
        return row;
    }


    /**
     * @Author lipeng
     * @Description 查询用户列表
     * @Date 2025/5/14
     * @param userListParamPO
     * @return java.lang.Object
     **/
    public List<UserListCustomerPO> queryList(UserListParamPO userListParamPO) {
        List<String> deptList = new ArrayList<>();
        if (KPStringUtil.isNotEmpty(userListParamPO.getDeptId()))
            deptList = deptCustomerMapper.queryDepeSubsetId(userListParamPO.getDeptId());

        MPJLambdaWrapper<UserPO> wrapper = new MPJLambdaWrapper<UserPO>("user")
                .selectAll(UserPO.class, "user")
                .select("GROUP_CONCAT(DISTINCT post.post_name SEPARATOR ', ' ) AS postNames")
                .select("GROUP_CONCAT(DISTINCT dept.dept_name SEPARATOR ', ' ) AS deptNames")
                .select("GROUP_CONCAT(DISTINCT role.role_name SEPARATOR ', ' ) AS roleNames")
                .leftJoin(UserPostPO.class, on -> on
                        .eq(UserPostPO::getUserId, UserPO::getUserId)
                        .eq(UserPostPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(PostPO.class, "post", on -> on
                        .eq(PostPO::getPostId, UserPostPO::getPostId)
                        .eq(PostPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(UserDeptPO.class, on -> on
                        .eq(UserDeptPO::getUserId, UserPO::getUserId)
                        .eq(UserDeptPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(DeptPO.class, "dept", on -> on
                        .eq(DeptPO::getDeptId, UserDeptPO::getDeptId)
                        .eq(DeptPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(UserRolePO.class, "userRole", on -> on
                        .eq(UserRolePO::getUserId, UserPO::getUserId)
                        .eq(UserRolePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(RolePO.class, "role", on -> on
                        .eq(RolePO::getRoleId, UserRolePO::getRoleId)
                        .eq(RolePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .groupBy(UserPO::getUserId)
                .like(KPStringUtil.isNotEmpty(userListParamPO.getUserName()), UserPO::getUserName, userListParamPO.getUserName())
                .like(KPStringUtil.isNotEmpty(userListParamPO.getJobNumber()), UserPO::getJobNumber, userListParamPO.getJobNumber())
                .like(KPStringUtil.isNotEmpty(userListParamPO.getPhoneNumber()), UserPO::getPhoneNumber, userListParamPO.getPhoneNumber())
                .like(KPStringUtil.isNotEmpty(userListParamPO.getIdCard()), UserPO::getIdCard, userListParamPO.getIdCard())
                .eq(KPStringUtil.isNotEmpty(userListParamPO.getSex()), UserPO::getSex, userListParamPO.getSex())
                .eq(KPStringUtil.isNotEmpty(userListParamPO.getStatus()), UserPO::getStatus, userListParamPO.getStatus())
                .eq(KPStringUtil.isNotEmpty(userListParamPO.getUserStatus()), UserPO::getUserStatus, userListParamPO.getUserStatus())
                .in(KPStringUtil.isNotEmpty(userListParamPO.getDeptId()), UserDeptPO::getDeptId, deptList)
                .in(KPStringUtil.isNotEmpty(userListParamPO.getRoleId()), UserRolePO::getRoleId, userListParamPO.getRoleId())
                .notIn(KPStringUtil.isNotEmpty(userListParamPO.getNeUserIds()), UserPO::getUserId, userListParamPO.getNeUserIds());

        if (KPStringUtil.isNotEmpty(userListParamPO.getName())) {
            wrapper.and(e -> e.like(UserPO::getRealName, userListParamPO.getName())
                    .or()
                    .like(UserPO::getNickName, userListParamPO.getName()));
        }

        PageHelper.orderBy(new PageBO().getOrderBy(userListParamPO.getOrderBy(), UserPO.class));
        return this.baseMapper.selectJoinList(UserListCustomerPO.class, wrapper);
    }


    /**
     * @Author lipeng
     * @Description 根据用户Id查询详情
     * @Date 2025-04-21
     * @param parameter
     * @return UserDetailsCustomerPO
     **/
    public UserDetailsCustomerPO queryDetailsById(JSONObject parameter) {
        UserPO userPO = KPJsonUtil.toJavaObject(parameter, UserPO.class);
        KPVerifyUtil.notNull(userPO.getUserId(), "请输入userId");

        MPJLambdaWrapper<UserPO> wrapper = new MPJLambdaWrapper<UserPO>("user")
                .selectAll(UserPO.class, "user")
//                .selectCollection(UserDeptPO.class, UserDetailsCustomerPO::getUserDeptList, map->map
//                        .result(UserDeptPO::getDeptId)
//                        .result(UserDeptPO::getPrincipal)
//                )
                .selectCollection(UserDeptPO.class, UserDetailsCustomerPO::getUserDepts)
                .leftJoin(UserDeptPO.class, "userDept", on -> on
                        .eq(UserDeptPO::getUserId, UserPO::getUserId)
                        .eq(UserDeptPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .selectCollection(DeptPO.class, UserDetailsCustomerPO::getDeptList)
                .leftJoin(DeptPO.class, "dept", on -> on
                        .eq(DeptPO::getDeptId, UserDeptPO::getDeptId)
                        .eq(DeptPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                        .eq(DeptPO::getStatus, YesNoEnum.YES.code())
                )
                .selectCollection(PostPO.class, UserDetailsCustomerPO::getPostList)
                .leftJoin(UserPostPO.class, "userPost", on -> on
                        .eq(UserPostPO::getUserId, UserPO::getUserId)
                        .eq(UserPostPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(PostPO.class, "post", on -> on
                        .eq(PostPO::getPostId, UserPostPO::getPostId)
                        .eq(PostPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                        .eq(PostPO::getStatus, YesNoEnum.YES.code())
                )
                .selectCollection(RolePO.class, UserDetailsCustomerPO::getRoleList)
                .leftJoin(UserRolePO.class, "userRole", on -> on
                        .eq(UserRolePO::getUserId, UserPO::getUserId)
                        .eq(UserRolePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(RolePO.class, "role", on -> on
                        .eq(RolePO::getRoleId, UserRolePO::getRoleId)
                        .eq(RolePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                        .eq(RolePO::getStatus, YesNoEnum.YES.code())
                )
                .selectCollection(UserProjectPO.class, UserDetailsCustomerPO::getProjectIds, map -> map
                        .result(UserProjectPO::getProjectId)
                )
                .leftJoin(UserProjectPO.class, "userProject", on -> on
                        .eq(UserProjectPO::getUserId, UserPO::getUserId)
                        .eq(UserProjectPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .eq(KPStringUtil.isNotEmpty(parameter.getString("jobNumber")), UserPO::getJobNumber, parameter.getString("jobNumber"))
                .eq(KPStringUtil.isNotEmpty(parameter.getString("userId")), UserPO::getUserId, parameter.getString("userId"));

        List<UserDetailsCustomerPO> customerPOList = this.baseMapper.selectJoinList(UserDetailsCustomerPO.class, wrapper);

        if (KPStringUtil.isEmpty(customerPOList)) return new UserDetailsCustomerPO();
        if (customerPOList.size() > 1) throw new KPServiceException("查询结果异常");

        UserDetailsCustomerPO row = customerPOList.get(0);

        if (KPStringUtil.isNotEmpty(row.getRoleList())) {
            row.setRoleIds(row.getRoleList().stream().map(RolePO::getRoleId).collect(Collectors.toList()));
            row.setRoleNames(row.getRoleList().stream().map(RolePO::getRoleName).collect(Collectors.toList()));
        }

        if (KPStringUtil.isNotEmpty(row.getPostList())) {
            row.setPostIds(row.getPostList().stream().map(PostPO::getPostId).collect(Collectors.toList()));
            row.setPostNames(row.getPostList().stream().map(PostPO::getPostName).collect(Collectors.toList()));
        }

        if (KPStringUtil.isNotEmpty(row.getProjectIds())) {
            List<ProjectPO> projectPOList = ProjectCache.getProjectsByProjectIds(row.getProjectIds());
            if (KPStringUtil.isNotEmpty(projectPOList))
                row.setProjectNames(projectPOList.stream().map(ProjectPO::getProjectName).collect(Collectors.toList()));
        }

        if (KPStringUtil.isNotEmpty(row.getUserDepts())) {
            Map<String, DeptPO> deptMap = row.getDeptList().stream().collect(Collectors.toMap(DeptPO::getDeptId, e -> e));
            row.setDeptIds(row.getUserDepts().stream().map(UserDeptPO::getDeptId).collect(Collectors.toList()));

            List<String> deptNameList = new ArrayList<>();
            row.getUserDepts().forEach(userDeptPO -> {
                if (userDeptPO.getPrincipal().equals(1)) {
                    deptNameList.add(deptMap.get(userDeptPO.getDeptId()).getDeptName() + "(负责人)");
                } else {
                    deptNameList.add(deptMap.get(userDeptPO.getDeptId()).getDeptName());
                }
            });
            row.setDeptNames(deptNameList);
        }


        return row;
    }


    /**
     * @Author lipeng
     * @Description 新增用户信息
     * @Date 2025-04-21
     * @param userEditParamPO
     * @return void
     **/
    public void saveUser(UserEditParamPO userEditParamPO) {
        UserPO userPO = KPJsonUtil.toJavaObjectNotEmpty(userEditParamPO, UserPO.class);

        List<UserPO> userPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(UserPO.class)
                .eq(UserPO::getJobNumber, userPO.getJobNumber())
                .or()
                .eq(UserPO::getUserName, userPO.getUserName())
                .or()
                .eq(KPStringUtil.isNotEmpty(userPO.getIdCard()), UserPO::getIdCard, userPO.getIdCard()));

        if (userPOList.size() > 0) {
            userPOList.forEach(userVerify -> {
                if (KPStringUtil.isNotEmpty(userVerify.getJobNumber()) && userVerify.getJobNumber().equalsIgnoreCase(userPO.getJobNumber()))
                    throw new KPServiceException("工号已存在，请勿重复添加");
                if (KPStringUtil.isNotEmpty(userVerify.getUserName()) && userVerify.getUserName().equalsIgnoreCase(userPO.getUserName()))
                    throw new KPServiceException("用户名已存在，请勿重复添加");
                // 二次入职 工号不一样 算俩条数据
                if (KPStringUtil.isNotEmpty(userVerify.getIdCard()) && userVerify.getIdCard().equalsIgnoreCase(userPO.getIdCard()) && !userVerify.getUserStatus().equals(UserStatusEnum.QUIT.code()))
                    throw new KPServiceException("该身份证号对应的用户已存在，并且不是离职人员， 请勿重复添加");
            });
        }
        userPO.setPassword(new BCryptPasswordEncoder().encode(kpUserProperties.getDefaultPassword()));

        //处理人员状态
        if (userPO.getUserStatus().equals(UserStatusEnum.QUIT.code())) {
            userPO.setStatus(LoginUserStatusEnum.LOGOUT.code());
            if (KPStringUtil.isEmpty(userEditParamPO.getDimissionDate())) userPO.setDimissionDate(LocalDate.now());
        } else {
            userPO.setStatus(LoginUserStatusEnum.NORMAL.code());
            userPO.setDimissionDate(null);
        }

        if (this.baseMapper.insert(userPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        userEditParamPO.setUserId(userPO.getUserId());

        userUtil.editRest(userEditParamPO, userPO.getUserId());
    }


    /**
     * @Author lipeng
     * @Description 修改用户信息
     * @Date 2025-04-21
     * @param userEditParamPO
     * @return void
     **/
    public void updateUser(UserEditParamPO userEditParamPO) {
        UserPO userPO = KPJsonUtil.toJavaObjectNotEmpty(userEditParamPO, UserPO.class);
        //禁止自己给自己分配管理员角色

        if (KPStringUtil.isNotEmpty(userEditParamPO.getRoleIds()) && userEditParamPO.getRoleIds().contains("2d0fb4174e9a106140a5793d2598ac00")) {
            if (!Arrays.asList("admin", "admin1").contains(LoginUserBO.getLoginUser().getUser().getUserName())) {
                //查询用户角色
                if (!userRoleMapper.selectList(Wrappers.lambdaQuery(UserRolePO.class).eq(UserRolePO::getUserId, userEditParamPO.getUserId()))
                        .stream().map(UserRolePO::getRoleId).collect(Collectors.toList())
                        .contains("68c12620cc4ac471cb32e39829e210bd")) {
                    throw new KPServiceException("不允许非超管账号添加管理员角色");
                }
            }
        }

        List<UserPO> userPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(UserPO.class)
                .ne(UserPO::getUserId, userPO.getUserId())
                .and(e -> e.eq(UserPO::getJobNumber, userPO.getJobNumber())
                        .or()
                        .eq(UserPO::getUserName, userPO.getUserName())
                        .or()
                        .eq(KPStringUtil.isNotEmpty(userPO.getIdCard()), UserPO::getIdCard, userPO.getIdCard())
                ));

        if (userPOList.size() > 0) {
            userPOList.forEach(userVerify -> {
                if (KPStringUtil.isNotEmpty(userVerify.getJobNumber()) && userVerify.getJobNumber().equalsIgnoreCase(userPO.getJobNumber()))
                    throw new KPServiceException("工号已存在，请勿重复添加");
                if (KPStringUtil.isNotEmpty(userVerify.getUserName()) && userVerify.getUserName().equalsIgnoreCase(userPO.getUserName()))
                    throw new KPServiceException("用户名已存在，请勿重复添加");
                // 二次入职 工号不一样 算俩条数据
                if (KPStringUtil.isNotEmpty(userVerify.getIdCard()) && userVerify.getIdCard().equalsIgnoreCase(userPO.getIdCard()) && !userVerify.getUserStatus().equals(UserStatusEnum.QUIT.code()))
                    throw new KPServiceException("该身份证号对应的用户已存在，并且不是离职人员， 请勿重复添加");
            });
        }

        userPO.setPassword(null);
        userPO.setUserName(null);
        userPO.setSource(null);
        //处理人员状态
        if (userPO.getUserStatus().equals(UserStatusEnum.QUIT.code())) {
            userPO.setStatus(LoginUserStatusEnum.LOGOUT.code());
            if (KPStringUtil.isEmpty(userEditParamPO.getDimissionDate())) userPO.setDimissionDate(LocalDate.now());
        } else {
            userPO.setStatus(LoginUserStatusEnum.NORMAL.code());
            userPO.setDimissionDate(null);
        }
        if (this.baseMapper.updateById(userPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        userUtil.editRest(userEditParamPO, userPO.getUserId());
    }


    /**
     * @Author lipeng
     * @Description 批量删除用户信息
     * @Date 2025-04-21
     * @param ids
     * @return String
     **/
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        return KPStringUtil.format("删除成功{0}条数据", row);
    }


    /**
     * @Author lipeng
     * @Description 禁用或者取消禁用
     * @Date 2025/4/29
     * @param parameter
     * @return void
     **/
    public void doForbidden(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("userId"), "用户ID不能为空");

        UserPO userPO = this.baseMapper.selectById(parameter.getString("userId"));
        if (userPO == null) throw new KPServiceException("用户不存在");

        switch (LoginUserStatusEnum.getCode(userPO.getStatus())) {
            case NORMAL:
                userPO.setStatus(LoginUserStatusEnum.FORBIDDEN.code());
                break;
            case FORBIDDEN:
                userPO.setStatus(LoginUserStatusEnum.NORMAL.code());
                break;
            case LOCK:
                throw new KPServiceException("账号已锁定，不允许进行该操作！");
            case LOGOUT:
                throw new KPServiceException("账号已注销，不允许进行该操作！");
            default:
                throw new KPServiceException("用户状态异常");
        }

        if (this.baseMapper.updateById(userPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 批量注销
     * @Date 2025/5/8
     * @param ids
     * @return com.kp.framework.entity.bo.KPResult
     **/
    public KPResult doCancel(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要注销的用户！");

        Integer row = this.baseMapper.update(
                new UserPO().setStatus(LoginUserStatusEnum.LOGOUT.code()),
                Wrappers.lambdaQuery(UserPO.class).in(UserPO::getUserId, ids));
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
        return KPResult.success(KPStringUtil.format("注销成功{0}个用户", row));
    }


    /**
     * @Author lipeng
     * @Description 管理员密码重置
     * @Date 2025/5/8
     * @param parameter
     * @return void
     **/
    public void doReset(JSONObject parameter) {
        UserPO userPO = KPJsonUtil.toJavaObjectNotEmpty(parameter, UserPO.class);
        KPVerifyUtil.notNull(parameter.getString("userId"), "用户ID不能为空");

        userPO.setPassword(new BCryptPasswordEncoder().encode(kpUserProperties.getDefaultPassword()));

        if (this.baseMapper.updateById(userPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 管理员手动解锁
     * @Date 2025/5/8
     * @param parameter
     * @return void
     **/
    public void doUnlock(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("userId"), "用户ID不能为空");
        KPServiceUtil.getBean(EhcacheService.class).clear(UserMapper.class);

        UserPO userPO = this.baseMapper.selectById(parameter.getString("userId"));
        if (userPO == null) throw new KPServiceException("用户不存在");

        switch (LoginUserStatusEnum.getCode(userPO.getStatus())) {
            case NORMAL:
                throw new KPServiceException("账号正常，无需解锁！");
            case FORBIDDEN:
                throw new KPServiceException("账号已禁用，不允许进行该操作！");
            case LOCK:
                userPO.setStatus(LoginUserStatusEnum.NORMAL.code());
                KPRedisUtil.remove(RedisSecurityConstant.LOGIN_ERROR_NUMBER + ":" + userPO.getUserName());
                break;
            case LOGOUT:
                throw new KPServiceException("账号已注销，不允许进行该操作！");
            default:
                throw new KPServiceException("用户状态异常");
        }

        if (this.baseMapper.updateById(userPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 修改密码
     * @Date 2025/7/7
     * @param parameter
     * @return void
     **/
    public void updatePassword(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("userId"), "请输入用户id！");
        KPVerifyUtil.notNull(parameter.getString("oldPassword"), "请输入老密码！");
        KPVerifyUtil.notNull(parameter.getString("newPassword"), "请输入新密码！");
        KPVerifyUtil.notNull(parameter.getString("okPassword"), "请输入确认密码！");
        KPVerifyUtil.length(parameter.getString("newPassword"), 6, 18, "新密码须6~18个字符！");

        if (parameter.getString("oldPassword").equals(parameter.getString("newPassword")))
            throw new KPServiceException("新密码和老密码一致！");

        if (!parameter.getString("newPassword").equals(parameter.getString("okPassword")))
            throw new KPServiceException("新密码与确认密码不一致！");

        UserPO userPO = this.baseMapper.selectById(parameter.getString("userId"));
        if (userPO == null) throw new KPServiceException("用户不存在！");

        if (!new BCryptPasswordEncoder().matches(parameter.getString("oldPassword"), userPO.getPassword()))
            throw new KPServiceException("老密码不正确, 如果忘记老密码，请联系管理人员重置密码");

        userPO.setPassword(new BCryptPasswordEncoder().encode(parameter.getString("newPassword")));

        if (this.baseMapper.updateById(userPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }

    public void updateMessage(JSONObject parameter) {
        UserPO userParameter = KPJsonUtil.toJavaObjectNotEmpty(parameter, UserPO.class);
        KPVerifyUtil.notNull(userParameter.getUserId(), "请输入用户id");
        KPVerifyUtil.length(userParameter.getNickName(), 1, 30, "用户昵称须1~30个字符");
        KPVerifyUtil.maxLength(userParameter.getPhoneNumber(), 11, "手机号码不能超过11个字符");
        KPVerifyUtil.notNull(userParameter.getSex(), "请选择用户性别");
        KPVerifyUtil.maxLength(userParameter.getEmail(), 50, "用户邮箱不能超过50个字符");

        UserPO userPO = this.baseMapper.selectById(userParameter.getUserId());
        if (userPO == null) throw new KPServiceException("用户不存在");

        UserPO updateUser = new UserPO()
                .setUserId(userParameter.getUserId())
                .setNickName(userParameter.getNickName())
                .setPhoneNumber(userParameter.getPhoneNumber())
                .setSex(userParameter.getSex())
                .setEmail(userParameter.getEmail())
                .setAvatar(KPMinioUtil.copyTemporaryFile("userHead/" + userPO.getJobNumber(), userParameter.getAvatar(), MinioConstant.AUTH_BUCKET_NAME));

        if (this.baseMapper.updateById(updateUser) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin123"));
    }


    /**
     * @Author lipeng
     * @Description 根据用户id集合查询用户列表
     * @Date 2025/8/26
     * @param userIds
     * @return java.util.List<com.kp.framework.modules.user.po.UserPO>
     **/
    public List<UserPO> queryUserIdList(List<String> userIds) {
        KPVerifyUtil.notNull(userIds, "用户id集合不能为空");

        List<UserPO> row = this.baseMapper.selectList(Wrappers.lambdaQuery(UserPO.class)
                .in(UserPO::getUserId, userIds)
                .orderByDesc(UserPO::getCreateDate));

        row.forEach(userListCustomerPO -> {
            userListCustomerPO.setAvatar(KPMinioUtil.getUrl(userListCustomerPO.getAvatar(), 24));
        });

        return row;
    }
}
