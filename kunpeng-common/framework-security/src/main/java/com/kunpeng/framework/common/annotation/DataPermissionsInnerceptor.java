package com.kunpeng.framework.common.annotation;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.kunpeng.framework.common.enums.LoginUserTypeEnum;
import com.kunpeng.framework.common.enums.PermissionTypeEnum;
import com.kunpeng.framework.common.util.CommonUtil;
import com.kunpeng.framework.common.util.ServiceUtil;
import com.kunpeng.framework.modules.role.mapper.AuthRolePermissionMapper;
import com.kunpeng.framework.modules.role.po.AuthRolePO;
import com.kunpeng.framework.modules.role.po.AuthRolePermissionPO;
import com.kunpeng.framework.modules.user.po.customer.LoginUserBO;
import com.kunpeng.framework.modules.user.po.customer.LoginUserDeptPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author lipeng
 * @Description 数据权限拦截器
 * @Date 2024/9/20 10:27
 * @return
 **/
@Slf4j
@Component
public class DataPermissionsInnerceptor implements InnerInterceptor {


    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (Arrays.asList(
                "com.framework.security.modules.role.mapper.AuthRolePermissionMapper.selectList"
        ).contains(ms.getId())){
            InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
            return;
        }


        try {
            HandlerMethod handlerMethod = (HandlerMethod) CommonUtil.queryHandlerMethod(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            KPDataPermissions kpDataPermissions = handlerMethod.getMethodAnnotation(KPDataPermissions.class);
            if (kpDataPermissions != null) {
                //查询表名
                String tableName = getTableName(boundSql.getSql()).trim();
                //排除不用执行权限的表
                if (StringUtils.isNotEmpty(kpDataPermissions.excludeTableName())){
                    if (Arrays.asList(kpDataPermissions.excludeTableName().split(".")).contains(tableName)) {
                        InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
                        return;
                    }
                }
                //查询条件
                AtomicReference<String> condition = new AtomicReference<>(null);
                // 填充sql查询条件
                LoginUserBO loginUserBO = LoginUserBO.getLoginUser();
                if (loginUserBO.getLoginType().equals(LoginUserTypeEnum.COMMON.code())){
                    //正常登录
                    splitWhereByCommon(kpDataPermissions, condition, loginUserBO);
                }else{
                    //授权登录 目前授权登录没有数据权限
//                    splitWhereByAuthorization(modifiedSql, loginUserBO);
                }

                // 重新组装sql
                String modifiedSql = addSearchConditions(boundSql.getSql(), condition.get());
                //把最新的sql放入待执行区
                PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
                mpBs.sql(modifiedSql);
            }
        }catch (Exception ex){
            log.error("[数据权限设置异常] {}", ex.getMessage());
            log.info("[数据权限设置异常] 如果是在线程里面， 请在开启线程前 调用 KPRequsetUtil.setRequest");
        }
        InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    private void splitWhereByAuthorization(String modifiedSql, LoginUserBO loginUserBO) {
        throw new RuntimeException("授权登录暂不支持数据权限！");
    }



    /**
     * @Author lipeng
     * @Description 普通登录拼接查询条件
     * @Date 2024/9/25 9:03
     * @param kpDataPermissions 注解
     * @param condition 条件
     * @param loginUserBO 登录用户
     * @return void
     **/
    private void splitWhereByCommon(KPDataPermissions kpDataPermissions, AtomicReference<String> condition, LoginUserBO loginUserBO) {
        if (loginUserBO.getRoles() ==null || loginUserBO.getRoles().size() == 0)
            return;

        StringBuffer sb = new StringBuffer();

        AuthRolePermissionMapper authRolePermissionMapper = ServiceUtil.getBean(AuthRolePermissionMapper.class);
        //查询所有角色下的数据权限
        LambdaQueryWrapper<AuthRolePermissionPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AuthRolePermissionPO::getProjectId, loginUserBO.getProjectId())
                .in(AuthRolePermissionPO::getRoleId, loginUserBO.getRoles().stream().map(AuthRolePO::getRoleId).collect(Collectors.toList()))
                .orderByDesc(AuthRolePermissionPO::getPermissionType);
        List<AuthRolePermissionPO> authRolePermissionPOList = authRolePermissionMapper.selectList(wrapper);
        if (authRolePermissionPOList == null || authRolePermissionPOList.size() ==0)
            return;

        List<Integer> permissionTypes = authRolePermissionPOList.stream().map(AuthRolePermissionPO::getPermissionType).collect(Collectors.toList());
        if (permissionTypes.contains(PermissionTypeEnum.ALL.code()))
            return;

        if (permissionTypes.contains(PermissionTypeEnum.ONESELF_DEPARTMENT_UNDER.code())){
            condition.set(CommonUtil.format("{0} in ({1})", kpDataPermissions.deptFileName(), loginUserBO.getDeptAndSubDept().stream().map(LoginUserDeptPO::getDeptId).map(id -> "'" + id + "'").collect(Collectors.joining(","))));
            return;
        }

        if (permissionTypes.contains(PermissionTypeEnum.ONESELF_DEPARTMENT.code())){
            condition.set(CommonUtil.format("{0} in ({1})", kpDataPermissions.deptFileName(), loginUserBO.getDeptList().stream().map(LoginUserDeptPO::getDeptId).map(id -> "'" + id + "'").collect(Collectors.joining(","))));
            return;
        }

        if (permissionTypes.contains(PermissionTypeEnum.ONESELF.code())){
            condition.set(CommonUtil.format("{0} = '{1}'", kpDataPermissions.userFileName(), loginUserBO.getIdentification()));
            return;
        }



        if (permissionTypes.contains(PermissionTypeEnum.CUSTOM_USER.code())){
            sb.append(CommonUtil.format("{0} in ({1})", kpDataPermissions.userFileName(),  authRolePermissionPOList.stream().filter(authRolePermissionPO -> (authRolePermissionPO.getPermissionType().equals(PermissionTypeEnum.CUSTOM_USER.code()) && StringUtils.isNotEmpty(authRolePermissionPO.getUserId()))).map(AuthRolePermissionPO::getUserId).map(id -> "'" + id + "'").collect(Collectors.joining(","))));
        }

        if (permissionTypes.contains(PermissionTypeEnum.CUSTOM.code())){
            if (StringUtils.isNotEmpty(sb.toString()))
                sb.append(" OR ");

            sb.append(CommonUtil.format("{0} in ({1})", kpDataPermissions.deptFileName(),  authRolePermissionPOList.stream().filter(authRolePermissionPO -> (authRolePermissionPO.getPermissionType().equals(PermissionTypeEnum.CUSTOM.code()) && StringUtils.isNotEmpty(authRolePermissionPO.getDeptId()))) .map(AuthRolePermissionPO::getDeptId).map(id -> "'" + id + "'").collect(Collectors.joining(","))));
        }


        if (StringUtils.isEmpty(sb.toString())) return;

        condition.set(CommonUtil.format("( {0} )", sb.toString()));
    }



    /**
     * @Author lipeng
     * @Description 添加搜索条件
     * @Date 2024/9/25 9:03
     * @param originalSql 原生sql
     * @param condition 条件
     * @return java.lang.String
     **/
    private String addSearchConditions(String originalSql, String condition) {
        if (StringUtils.isEmpty(condition)) return originalSql;

        StringBuilder sql = new StringBuilder();
        int whereIndex = originalSql.toLowerCase().lastIndexOf("where");
        if (whereIndex != -1) {
            sql.append(originalSql.substring(0, whereIndex))
                    .append(" WHERE {0} AND ")
                    .append(originalSql.substring(whereIndex + 5));
        } else {
            int fromIndex = originalSql.toLowerCase().lastIndexOf("from");
            String tableName = originalSql.substring(fromIndex + 4).trim().split(" ")[0];
            sql.append(originalSql.substring(0, fromIndex))
                    .append(" FROM ")
                    .append(tableName)
                    .append(" WHERE {0} ")
                    .append(originalSql.substring(originalSql.lastIndexOf(tableName) + tableName.length()));

        }

        return CommonUtil.format(sql.toString(), condition);
    }


    /**
     * @Author lipeng
     * @Description 获取表名
     * @Date 2024/9/25 8:57
     * @param originalSql
     * @return java.lang.String
     **/
    private String getTableName(String originalSql){
        int fromIndex = originalSql.toLowerCase().lastIndexOf("from");
        return originalSql.substring(fromIndex + 4).trim().split(" ")[0];
    }
}
