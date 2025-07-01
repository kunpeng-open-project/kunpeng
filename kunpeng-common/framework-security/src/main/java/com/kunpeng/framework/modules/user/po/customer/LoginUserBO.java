package com.kunpeng.framework.modules.user.po.customer;

import com.alibaba.fastjson2.annotation.JSONField;
import com.kunpeng.framework.common.properties.KunPengTokenProperties;
import com.kunpeng.framework.common.properties.RedisSecurityConstant;
import com.kunpeng.framework.common.util.CommonUtil;
import com.kunpeng.framework.common.util.KPJWTUtil;
import com.kunpeng.framework.common.util.ServiceUtil;
import com.kunpeng.framework.modules.role.po.AuthRolePO;
import com.kunpeng.framework.modules.user.po.AuthUserPO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-18
 */
@Data
public class LoginUserBO implements UserDetails {


    private static final long serialVersionUID = 1L;



    //登录用户
    @JSONField(name = "authUserPO")
    private AuthUserPO user;

    //角色列表
    private List<AuthRolePO> roles;

    //权限列表
    private List<String> roleKeys;

    //权限列表
    private List<String> permissions;

    //用户部门
    private List<LoginUserDeptPO> deptList = new ArrayList<>();

    //用户子部门
    private List<LoginUserDeptPO> subDeptList = new ArrayList<>();

    //登录类型 1 普通账号登录 2 授权登录 3 免密登录
    private Integer loginType;

    //登录的项目id
    private String projectId;

    //用户真实姓名 或者 项目名称
    private String name;

    //唯一标识  如果是账号密码登录 就是用户id  如果是授权登录 是 项目code
    private String identification;

    public LoginUserBO(AuthUserPO authUserPO){
        this.user = authUserPO;
    }

    public LoginUserBO(){}

    public List<LoginUserDeptPO> getDeptAndSubDept(){
        List<LoginUserDeptPO> combinedList = new ArrayList<>();
        combinedList.addAll(deptList);
        subDeptList.forEach(subDept -> {
            if (!combinedList.stream().anyMatch(userDeptPO -> userDeptPO.getDeptId().equals(subDept.getDeptId()))){
                combinedList.add(subDept);
            }
        });

        return combinedList;
    }



    /**
     * security角色
     */
    private Collection<GrantedAuthority> authorities;

    @Override
    @JSONField(serialize = false)
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JSONField(serialize = false)
    public String getUsername() {
        return user.getUserName();
    }


    /**
     * 账户是否未过期,过期无法验证
     **/
    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }


    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     **/
    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     **/
    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * 是否可用 ,禁用的用户不能身份验证
     **/
    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return true;
    }


    /**
     * @Author lipeng
     * @Description 获取当前登录用户的token
     * @Date 2024/4/22 14:05
     * @param
     * @return com.framework.security.modules.user.po.customer.LoginUserBO
     **/
    public static LoginUserBO getLoginUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return getLoginUser(request);
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * @Author lipeng
     * @Description 获取当前登录用户的token
     * @Date 2024/4/22 12:02
     * @param request
     * @return com.framework.security.modules.user.po.customer.LoginUserBO
     **/
    public static LoginUserBO getLoginUser(HttpServletRequest request){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(ServiceUtil.getBean(KunPengTokenProperties.class).getHeader());
        if (StringUtils.isEmpty(token)) return null;
        if (token.startsWith(ServiceUtil.getBean(KunPengTokenProperties.class).getHead()))
            token = token.replace(ServiceUtil.getBean(KunPengTokenProperties.class).getHead(), "").trim();


            LoginUserTypeBO loginUserTypeBO = CommonUtil.toJavaObject(KPJWTUtil.parseToken(token).asString(), LoginUserTypeBO.class);

        String body = CommonUtil.get(RedisSecurityConstant.REDIS_AUTHENTICATION_LOGINUSER_MESSAGE + loginUserTypeBO.getProjectCode() + ":" + loginUserTypeBO.getIdentification());
        if (StringUtils.isEmpty(body)) return null;

        LoginUserBO loginUserBO = CommonUtil.toJavaObject(body, LoginUserBO.class);
        loginUserBO.setLoginType(loginUserTypeBO.getLoginType());
        loginUserBO.setProjectId(loginUserTypeBO.getProjectId());
        return loginUserBO;
    }



}
