package com.kunpeng.framework.common.security.verify;

import com.kunpeng.framework.common.properties.KunPengPassConfig;
import com.kunpeng.framework.modules.user.po.customer.LoginUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


/**
 * @Author lipeng
 * @Description 自定义权限注解验证
 * @Date 2024/4/21 14:53
 * @return
 **/
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {
//    @Autowired
//    private SysUserService sysUserService;

    @Autowired
    private KunPengPassConfig kunPengPassConfig;

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     * @author zwq
     * @date 2020/4/4
     * @param authentication
     * @param targetUrl
     * @param permission
     * @return
     **/
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String url : kunPengPassConfig.getUrls()) {
            if (pathMatcher.match((url), targetUrl.toString())) {
                return true;
            }
        }

        if (authentication.getPrincipal().equals("anonymousUser"))
            return false;

        // 获取用户信息
        LoginUserBO loginUserBO = (LoginUserBO) authentication.getPrincipal();
        if (Arrays.asList("admin", "admin1").contains(loginUserBO.getUsername())) return true;

        List<String> permissions = loginUserBO.getPermissions();
        if (permissions == null || permissions.size() == 0) return false;

        // 权限对比
        if (permissions.contains(permission.toString())) return true;

        return false;
    }
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}