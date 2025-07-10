package com.kunpeng.framework.modules.menu.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.KPApiJsonlParamMode;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.menu.po.MenuPO;
import com.kunpeng.framework.modules.menu.po.customer.MenuCustomerPO;
import com.kunpeng.framework.modules.menu.po.param.MenuEditParamPO;
import com.kunpeng.framework.modules.menu.po.param.MenuListParamPO;
import com.kunpeng.framework.modules.menu.po.param.MenuSortParamPO;
import com.kunpeng.framework.modules.menu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lipeng
 * @Description 菜单信息表相关接口
 * @Date 2025-04-11
 **/
@RestController
@RequestMapping("/auth/menu")
@Api(tags = "菜单信息相关接口", value = "菜单信息相关接口")
@ApiSupport(order = 5)
public class MenuController {

    @Autowired
    private MenuService menuService;


    @PreAuthorize("hasPermission('/auth/menu/list', 'auth:menu:list')")
    @ApiOperation(value = "查询菜单信息分页列表", notes = "权限 auth:menu:list")
    @PostMapping("/list")
    @KPVerifyNote
    public KPResult<MenuCustomerPO> queryList(@RequestBody MenuListParamPO menuListParamPO) {
        return KPResult.list(menuService.queryList(menuListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/menu/details','auth:menu:details')")
    @ApiOperation(value = "根据菜单Id查询详情", notes = "权限 auth:menu:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "menuId", value = "菜单Id", required = true)
    })
    public KPResult<MenuPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(menuService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/menu/save','auth:menu:save')")
    @ApiOperation(value = "新增菜单信息", notes = "权限 auth:menu:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = MenuEditParamPO.class, ignores = "menuId")
    public KPResult<MenuPO> save(@RequestBody MenuEditParamPO menuEditParamPO) {
        menuService.saveMenu(menuEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/menu/update','auth:menu:update')")
    @ApiOperation(value = "修改菜单信息", notes = "权限 auth:menu:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<MenuPO> update(@RequestBody MenuEditParamPO menuEditParamPO) {
        menuService.updateMenu(menuEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/menu/batch/remove','auth:menu:batch:remove')")
    @ApiOperation(value = "批量删除菜单信息", notes = "权限 auth:menu:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "ids", value = "菜单Id", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(menuService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/menu/do/set/sort','auth:menu:do:set:sort')")
    @ApiOperation(value = "设置排序", notes = "权限 auth:menu:do:set:sort")
    @PostMapping(value = "/do/set/sort")
    @KPVerifyNote
    public KPResult doSetOrderNum(@RequestBody List<MenuSortParamPO> menuSortParamPOList) {
        menuService.doSetSort(menuSortParamPOList);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/menu/do/enable','auth:menu:do:enable')")
    @ApiOperation(value = "设置菜单启用状态", notes = "权限 auth:menu:do:enable")
    @PostMapping(value = "/do/enable")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "menuId", value = "菜单Id", required = true)
    })
    public KPResult doEnable(@RequestBody JSONObject parameter) {
        menuService.doEnable(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/menu/do/copy','auth:menu:do:copy')")
    @ApiOperation(value = "复制菜单", notes = "权限 auth:menu:do:copy")
    @PostMapping(value = "/do/copy")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "menuId", value = "菜单Id", required = true)
    })
    public KPResult doStatus(@RequestBody JSONObject parameter) {
        menuService.doCopy(parameter);
        return KPResult.success();
    }
}
