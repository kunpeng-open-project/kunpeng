package com.kp.framework.modules.monthly.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 月度计划责任人信息列表查询入参。
 * @author lipeng
 * 2025-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MonthlyReportUserListParamPO对象", description = "月度计划责任人信息列表查询入参")
public class MonthlyReportUserListParamPO extends PageBO {

    @Schema(description = "月度计划责任人Id")
    @TableId(value = "mru_id", type = IdType.ASSIGN_UUID)
    private String mruId;

    @Schema(description = "用户id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "用户姓名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
