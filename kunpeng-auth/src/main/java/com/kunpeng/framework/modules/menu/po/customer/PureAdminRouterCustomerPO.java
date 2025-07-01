package com.kunpeng.framework.modules.menu.po.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 路由配置信息
 *
 * @author Chen Haidong
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="PureAdminRouterCustomerPO", description="PureAdminRouterCustomerPO")
public class PureAdminRouterCustomerPO {

    @ApiModelProperty(value = "路由名字")
    private String name;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件地址")
    private String component;

    @ApiModelProperty(value = "重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击")
    private String redirect;

    @ApiModelProperty(value = "其他元素")
    private PureAdminRouterMetaCustomerPO meta;

    @ApiModelProperty(value = "子路由")
    private List<PureAdminRouterCustomerPO> children;
}
