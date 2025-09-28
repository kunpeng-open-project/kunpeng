package com.kp.framework.modules.menu.po.customer;

import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.modules.menu.enums.MenuTypeEnum;
import com.kp.framework.modules.menu.po.MenuPO;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lipeng
 * @Description
 * @Date 2025/5/1
 * @return
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="PureAdminRouterMetaCustomerPO", description="PureAdminRouterMetaCustomerPO")
public class PureAdminRouterMetaCustomerPO {

    @ApiModelProperty(value = "菜单名称（兼容国际化、非国际化，如果用国际化的写法就必须在根目录的locales文件夹下对应添加）")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "是否在菜单中显示")
    private Boolean showLink;

    @ApiModelProperty(value = "菜单排序，值越高排的越后（只针对顶级路由）")
    private Integer rank;

    @ApiModelProperty(value = "是否显示父级菜单")
    private Boolean showParent;

    @ApiModelProperty(value = "是否缓存该路由页面（开启后，会保存该页面的整体状态，刷新后会清空状态）")
    private Boolean keepAlive;

    @ApiModelProperty(value = "权限")
    private List<String> auths;

    @ApiModelProperty(value = "外链内嵌页面的地址")
    private String frameSrc;

    public PureAdminRouterMetaCustomerPO(MenuCustomerPO menu, Map<String, List<MenuPO>> auths) {
        this.title = menu.getMenuName();
        this.icon = menu.getIcon();
        this.rank = menu.getSort();
        if (KPStringUtil.isNotEmpty(menu.getVisible()))
            this.showLink = YesNoEnum.YES.code().equals(menu.getVisible());
        if (KPStringUtil.isNotEmpty(menu.getIsCache()))
            this.keepAlive = YesNoEnum.YES.code().equals(menu.getIsCache());
        if (MenuTypeEnum.MENU.code().equals(menu.getMenuType()) && !menu.getParentId().equals("0"))
            this.showParent = true;

        //设置自带权限
        if (KPStringUtil.isNotEmpty(menu.getPerms())){
            this.auths = new ArrayList<>();
            this.auths.add(menu.getPerms());
        }

        //设置按钮权限
        List<MenuPO> list = auths.get(menu.getMenuId());
        if (KPStringUtil.isEmpty(list)) return;

        if (KPStringUtil.isEmpty(this.auths)){
            this.auths = list.stream().map(MenuPO::getPerms).collect(Collectors.toList());
        }else{
            this.auths.addAll(list.stream().map(MenuPO::getPerms).collect(Collectors.toList()));
        }
    }
}
