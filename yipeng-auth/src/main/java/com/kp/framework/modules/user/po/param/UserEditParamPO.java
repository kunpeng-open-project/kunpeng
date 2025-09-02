package com.kp.framework.modules.user.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPLength;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author lipeng
 * @Description 用户信息编辑入参
 * @Date 2025-04-21
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "UserEditParamPO对象", description = "用户信息编辑入参")
public class UserEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户Id", example = "用户Id", required = true)
    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入用户Id")
    @KPMaxLength(max = 36, errMeg = "用户Id不能超过36个字符")
    private String userId;

    @ApiModelProperty(value = "用户账号", example = "用户账号", required = true)
    @TableField("user_name")
    @KPNotNull(errMeg = "请输入用户账号")
    @KPLength(min = 1, max = 30, errMeg = "用户账号须1~30个字符")
    private String userName;

    @ApiModelProperty(value = "工号", example = "工号", required = true)
    @TableField("job_number")
    @KPNotNull(errMeg = "请输入工号")
    @KPLength(min = 1, max = 10, errMeg = "工号须1~10个字符")
    private String jobNumber;

    @ApiModelProperty(value = "真实姓名", example = "真实姓名", required = true)
    @TableField("real_name")
    @KPNotNull(errMeg = "请输入真实姓名")
    @KPLength(min = 1, max = 20, errMeg = "真实姓名须1~20个字符")
    private String realName;

    @ApiModelProperty(value = "用户昵称", example = "用户昵称", required = true)
    @TableField("nick_name")
    @KPNotNull(errMeg = "请输入用户昵称")
    @KPLength(min = 1, max = 30, errMeg = "用户昵称须1~30个字符")
    private String nickName;

    @ApiModelProperty(value = "用户邮箱", example = "用户邮箱")
    @TableField("email")
    @KPMaxLength(max = 50, errMeg = "用户邮箱不能超过50个字符")
    private String email;

    @ApiModelProperty(value = "手机号码", example = "手机号码", required = true)
    @TableField("phone_number")
    @KPNotNull(errMeg = "请输入手机号码")
    @KPMaxLength(max = 11, errMeg = "手机号码不能超过11个字符")
    private String phoneNumber;

    @ApiModelProperty(value = "用户性别 1男 0女 2未知", example = "0", required = true)
    @TableField("sex")
    @KPNotNull(errMeg = "请选择用户性别")
    private Integer sex;

    @ApiModelProperty(value = "用户状态 1实习 2 转正 3 离职", example = "0", required = true)
    @TableField("user_status")
    @KPNotNull(errMeg = "请选择用户状态")
    private Integer userStatus;

    @ApiModelProperty(value = "身份证", example = "身份证")
    @TableField("id_card")
    @KPMaxLength(max = 255, errMeg = "身份证不能超过255个字符")
    private String idCard;

    @ApiModelProperty(value = "入职时间", example = "2025-04-21 11:00:54")
    @TableField("entry_date")
    private LocalDate entryDate;

    @ApiModelProperty(value = "转正时间", example = "2025-04-21 11:00:54")
    @TableField("official_date")
    private LocalDate officialDate;

    @ApiModelProperty(value = "离职时间", example = "2025-04-21 11:00:54")
    @TableField("dimission_date")
    private LocalDate dimissionDate;

    @ApiModelProperty(value = "数据来源", example = "数据来源", required = true)
    @TableField("source")
    @KPNotNull(errMeg = "请输入数据来源")
    @KPMaxLength(max = 64, errMeg = "数据来源不能超过64个字符")
    private String source;

    @ApiModelProperty(value = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;

    @ApiModelProperty(value = "归宿部门", required = true)
    @KPNotNull(errMeg = "请输入归宿部门")
    private List<UserDeptParamPO> userDepts;

    @ApiModelProperty(value = "角色", required = true, example = "[\"4c2943e45aa513c079045020b0d1bd8e\"]")
    @KPNotNull(errMeg = "请选择角色")
    private List<String> roleIds;

    @ApiModelProperty(value = "岗位", required = true, example = "[\"a18ace9d40290e5b0b323fe3f3a237e2\"]")
    @KPNotNull(errMeg = "请选择岗位")
    private List<String> postIds;

    @ApiModelProperty(value = "可操作项目", example = "[\"bfff793893f9b3f08d736389529a1115\"]")
    private List<String> projectIds;
}
