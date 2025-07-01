package com.kunpeng.framework.modules.user.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kunpeng.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lipeng
 * @Description 用户登录记录列表查询入参
 * @Date 2025-06-10
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "LoginRecordListParamPO对象", description = "用户登录记录列表查询入参")
public class LoginRecordListParamPO extends PageBO {

    @ApiModelProperty("用户账号 1 用户的工号 2 appid")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "登录类型 1账号登录 2 授权登录 3免密登录 4泛微单点登录")
    @TableField("login_type")
    private Integer loginType;

    @ApiModelProperty(value = "登录的项目")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty(value = "登录时间")
    private LocalDate loginDate;

    @ApiModelProperty(value = "选项")
    private List<String> options = new ArrayList<>();

    @ApiModelProperty("登录IP")
    @TableField("login_ip")
    private String loginIp;
}
