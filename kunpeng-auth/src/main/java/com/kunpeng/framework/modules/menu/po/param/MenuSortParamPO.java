package com.kunpeng.framework.modules.menu.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kunpeng.framework.annotation.verify.KPMaxLength;
import com.kunpeng.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "MenuSortParamPO", description = "MenuSortParamPO")
public class MenuSortParamPO {

    @ApiModelProperty(value = "菜单Id", required = true)
    @TableId(value = "menu_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入菜单Id")
    @KPMaxLength(max = 36, errMeg = "菜单Id不能超过36个字符")
    private String menuId;


    @ApiModelProperty(value = "显示顺序", required = true)
    @TableField("sort")
    @KPNotNull(errMeg = "请输入显示顺序")
    private Integer sort;
}
