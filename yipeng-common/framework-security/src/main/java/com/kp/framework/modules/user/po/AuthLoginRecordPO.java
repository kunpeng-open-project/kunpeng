package com.kp.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.common.parent.ParentSecurityBO;
import com.kp.framework.common.util.CommonUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * <p>
 * 用户登录记录表
 * </p>
 *
 * @author lipeng
 * @since 2024-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("auth_login_record")
public class AuthLoginRecordPO extends ParentSecurityBO<AuthLoginRecordPO> {

    @Schema(description = "登录记录id")
    @TableId(value = "alr_id", type = IdType.ASSIGN_UUID)
    private String alrId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "用户账号")
    @TableField("user_name")
    private String userName;

    @Schema(description = "登录类型 1平台登录 2授权登录")
    @TableField("login_type")
    private Integer loginType;

    @Schema(description = "登录的项目")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "登录浏览器信息")
    @TableField("user_agent")
    private String userAgent;

    @Schema(description = "用户操作来源")
    @TableField("user_referer")
    private String userReferer;

    @Schema(description = "用户代理平台")
    @TableField("user_plat_form")
    private String userPlatForm;

    @Schema(description = "登录IP")
    @TableField("login_ip")
    private String loginIp;

    @Schema(description = "登录结果")
    @TableField("login_result")
    private String loginResult;


    public AuthLoginRecordPO(String projectId, String userName, Integer loginType) {
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
