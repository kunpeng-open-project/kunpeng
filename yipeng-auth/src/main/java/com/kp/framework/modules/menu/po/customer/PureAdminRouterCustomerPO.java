package com.kp.framework.modules.menu.po.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 路由配置信息。
 * @author lipeng
 * 2025/5/4
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PureAdminRouterCustomerPO", description = "PureAdminRouterCustomerPO")
public class PureAdminRouterCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "路由名字")
    private String name;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件地址")
    private String component;

    @Schema(description = "重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击")
    private String redirect;

    @Schema(description = "其他元素")
    private PureAdminRouterMetaCustomerPO meta;

    @Schema(description = "子路由")
    private List<PureAdminRouterCustomerPO> children;
}
