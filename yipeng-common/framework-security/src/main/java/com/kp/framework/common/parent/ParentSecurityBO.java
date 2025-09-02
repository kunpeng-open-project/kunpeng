package com.kp.framework.common.parent;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class ParentSecurityBO<T extends ParentSecurityBO> extends Model implements Serializable {

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ParentSecurityBO(){}

    private static final long serialVersionUID = 1L;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private String createUserId;

    @TableField(value = "create_user_name", fill = FieldFill.INSERT)
    private String createUserName;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

    @TableField(value = "update_user_name", fill = FieldFill.INSERT_UPDATE)
    private String updateUserName;

    @TableLogic(value = "0",delval = "1")
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    private Integer deleteFlag;

}
