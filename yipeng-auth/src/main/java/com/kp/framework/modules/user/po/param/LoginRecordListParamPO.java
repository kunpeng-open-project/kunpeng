package com.kp.framework.modules.user.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录记录列表查询入参。
 * @author lipeng
 * 2025-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "LoginRecordListParamPO", description = "用户登录记录表列表查询入参")
public class LoginRecordListParamPO extends PageBO {

    @Schema(description = "用户账号 1 用户的工号 2 appid")
    @TableField("user_name")
    private String userName;

    @Schema(description = "登录类型 1账号登录 2 授权登录 3免密登录")
    @TableField("login_type")
    private Integer loginType;

    @Schema(description = "登录的项目")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "登录时间")
    private LocalDate loginDate;

    @Schema(description = "登录IP")
    @TableField("login_ip")
    private String loginIp;

    @Schema(description = "选项")
    private List<String> options = new ArrayList<>();
}
