package com.kp.framework.modules.menu.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.menu.mapper.MenuMapper;
import com.kp.framework.modules.menu.po.MenuPO;
import com.kp.framework.modules.menu.po.customer.MenuCustomerPO;
import com.kp.framework.modules.menu.po.param.MenuEditParamPO;
import com.kp.framework.modules.menu.po.param.MenuListParamPO;
import com.kp.framework.modules.menu.po.param.MenuSortParamPO;
import com.kp.framework.modules.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单信息表相关接口。
 * @author lipeng
 * 2025-04-11
 */
@RestController
@RequestMapping("/auth/menu")
@Tag(name = "菜单信息相关接口")
@ApiSupport(author = "lipeng", order = 29)
public class MenuController {

    @Autowired
    private MenuService menuService;


    @PreAuthorize("hasPermission('/auth/menu/list', 'auth:menu:list')")
    @Operation(summary = "查询菜单信息分页列表", description = "权限 auth:menu:list")
    @PostMapping("/list")
    @KPVerifyNote
    public KPResult<List<MenuCustomerPO>> queryList(@RequestBody MenuListParamPO menuListParamPO) {
        return KPResult.success(menuService.queryList(menuListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/menu/details','auth:menu:details')")
    @Operation(summary = "根据菜单Id查询详情", description = "权限 auth:menu:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "menuId", description = "菜单Id", required = true)
    })
    public KPResult<MenuPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(menuService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/menu/save','auth:menu:save')")
    @Operation(summary = "新增菜单信息", description = "权限 auth:menu:save")
    @PostMapping("/save")
    @KPObjectChangeLogNote(parentMapper = MenuMapper.class, identification = "menuId,menu_id", operateType = ObjectChangeLogOperateType.ADD, businessType = "菜单信息")
    @KPVerifyNote
    @KPApiJsonParamMode(component = MenuEditParamPO.class, ignores = "menuId")
    public KPResult<MenuPO> save(@RequestBody MenuEditParamPO menuEditParamPO) {
        menuService.saveMenu(menuEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/menu/update','auth:menu:update')")
    @Operation(summary = "修改菜单信息", description = "权限 auth:menu:update")
    @PostMapping("/update")
    @KPObjectChangeLogNote(parentMapper = MenuMapper.class, identification = "menuId,menu_id", businessType = "菜单信息")
    @KPVerifyNote
    public KPResult<MenuPO> update(@RequestBody MenuEditParamPO menuEditParamPO) {
        menuService.updateMenu(menuEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/menu/batch/remove','auth:menu:batch:remove')")
    @Operation(summary = "批量删除菜单信息", description = "权限 auth:menu:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonParam({
            @KPJsonField(name = "ids", description = "菜单Id", required = true, dataType = "array<string>")
    })
    @KPObjectChangeLogNote(parentMapper = MenuMapper.class, identification = "menuId,menu_id", operateType = ObjectChangeLogOperateType.DELETE, businessType = "菜单信息")
    public KPResult<String> batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(menuService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/menu/do/set/sort','auth:menu:do:set:sort')")
    @Operation(summary = "设置排序", description = "权限 auth:menu:do:set:sort")
    @PostMapping(value = "/do/set/sort")
    @KPObjectChangeLogNote(parentMapper = MenuMapper.class, identification = "menuId,menu_id", operateType = ObjectChangeLogOperateType.UPDATE_BATCH, businessType = "菜单信息")
    @KPVerifyNote
    public KPResult<Void> doSetOrderNum(@RequestBody List<MenuSortParamPO> menuSortParamPOList) {
        menuService.doSetSort(menuSortParamPOList);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/menu/do/enable','auth:menu:do:enable')")
    @Operation(summary = "设置菜单启用状态", description = "权限 auth:menu:do:enable")
    @PostMapping(value = "/do/enable")
    @KPApiJsonParam({
            @KPJsonField(name = "menuId", description = "菜单Id", required = true)
    })
    @KPObjectChangeLogNote(parentMapper = MenuMapper.class, identification = "menuId,menu_id", businessType = "菜单信息")
    public KPResult<Void> doEnable(@RequestBody JSONObject parameter) {
        menuService.doEnable(parameter);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/menu/do/copy','auth:menu:do:copy')")
    @Operation(summary = "复制菜单", description = "权限 auth:menu:do:copy")
    @PostMapping(value = "/do/copy")
    @KPApiJsonParam({
            @KPJsonField(name = "menuId", description = "菜单Id", required = true)
    })
    @KPObjectChangeLogNote(parentMapper = MenuMapper.class, identification = "menuId,menu_id", operateType = ObjectChangeLogOperateType.ADD, businessType = "菜单信息")
    public KPResult<Void> doStatus(@RequestBody JSONObject parameter) {
        menuService.doCopy(parameter);
        return KPResult.success();
    }
}
