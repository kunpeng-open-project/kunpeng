package com.kp.framework.modules.menu.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MenuSortParamPO", description = "MenuSortParamPO")
public class MenuSortParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "menu_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入菜单Id")
    @KPMaxLength(max = 36, errMeg = "菜单Id不能超过36个字符")
    private String menuId;


    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("sort")
    @KPNotNull(errMeg = "请输入显示顺序")
    private Integer sort;
}
