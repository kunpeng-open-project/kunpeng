package com.kp.framework.entity.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类父类 公共字段。
 * @author lipeng
 * 2020/1/21
 */
@Getter
@Setter
@NoArgsConstructor
public class ParentBO<T extends ParentBO<?>> extends Model<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @Schema(description = "创建用户编号")
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private String createUserId;

    @Schema(description = "创建用户名称")
    @TableField(value = "create_user_name", fill = FieldFill.INSERT)
    private String createUserName;

    @Schema(description = "修改时间")
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    @Schema(description = "修改用户编号")
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

    @Schema(description = "修改用户名称")
    @TableField(value = "update_user_name", fill = FieldFill.INSERT_UPDATE)
    private String updateUserName;

    @Schema(description = "删除状态 0正常 1删除", hidden = true)
    @TableLogic(value = "0", delval = "1")
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    private Integer deleteFlag;
}
