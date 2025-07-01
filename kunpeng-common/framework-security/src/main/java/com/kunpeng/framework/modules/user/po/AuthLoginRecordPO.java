package com.kunpeng.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.common.parent.ParentSecurityBO;
import com.kunpeng.framework.common.util.CommonUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户登录记录表
 * </p>
 *
 * @author lipeng
 * @since 2024-06-25
 */
@Data
@TableName("auth_login_record")
public class AuthLoginRecordPO extends ParentSecurityBO<AuthLoginRecordPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录记录id")
    @TableId(value = "alr_id", type = IdType.ASSIGN_UUID)
    private String alrId;

    @ApiModelProperty(value = "用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "用户账号")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "登录类型 1平台登录 2授权登录")
    @TableField("login_type")
    private Integer loginType;

    @ApiModelProperty(value = "登录的项目")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty(value = "登录浏览器信息")
    @TableField("user_agent")
    private String userAgent;

    @ApiModelProperty(value = "用户操作来源")
    @TableField("user_referer")
    private String userReferer;

    @ApiModelProperty(value = "用户代理平台")
    @TableField("user_plat_form")
    private String userPlatForm;

    @ApiModelProperty(value = "登录IP")
    @TableField("login_ip")
    private String loginIp;

    @ApiModelProperty(value = "登录结果")
    @TableField("login_result")
    private String loginResult;


    public AuthLoginRecordPO(){

    }


    public AuthLoginRecordPO(String projectId, String userName, Integer loginType){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        this.projectId = projectId;
        this.userAgent = req.getHeader("User-Agent");
        this.userReferer = req.getHeader("Accept-Language");
        this.userPlatForm = req.getHeader("Sec-Ch-Ua-Platform") == null ? "" : req.getHeader("Sec-Ch-Ua-Platform").replaceAll("\"", "");
        this.loginIp = CommonUtil.getClinetIP(req);
        this.userName = userName;
        this.loginType = loginType;
    }


}
