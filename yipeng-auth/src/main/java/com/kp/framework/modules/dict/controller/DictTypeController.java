package com.kp.framework.modules.dict.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dict.po.DictTypePO;
import com.kp.framework.modules.dict.po.customer.DictTypeDetailsCustomerPO;
import com.kp.framework.modules.dict.po.customer.DictTypeListCustomerPO;
import com.kp.framework.modules.dict.po.param.DictTypeEditParamPO;
import com.kp.framework.modules.dict.po.param.DictTypeListParamPO;
import com.kp.framework.modules.dict.service.DictTypeService;
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
 * 字典类型表相关接口。
 * @author lipeng
 * 2025-07-03
 */
@RestController
@RequestMapping("/auth/dict/type")
@Tag(name = "字典类型相关接口")
@ApiSupport(author = "lipeng", order = 30)
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;

    @PreAuthorize("hasPermission('/auth/dict/type/page/list', 'auth:dict:type:page:list')")
    @Operation(summary = "查询字典类型分页列表", description = "权限 auth:dict:type:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<DictTypeListCustomerPO> queryPageList(@RequestBody DictTypeListParamPO dictTypeListParamPO) {
        return dictTypeService.queryPageList(dictTypeListParamPO);
    }


    @PreAuthorize("hasPermission('/auth/dict/type/details','auth:dict:type:details')")
    @Operation(summary = "根据字典类型ID查询详情", description = "权限 auth:dict:type:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "dictTypeId", description = "字典类型ID", required = true)
    })
    public KPResult<DictTypeDetailsCustomerPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(dictTypeService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/dict/type/save','auth:dict:type:save')")
    @Operation(summary = "新增字典类型", description = "权限 auth:dict:type:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonParamMode(component = DictTypeEditParamPO.class, ignores = "dictTypeId")
    public KPResult<DictTypePO> save(@RequestBody DictTypeEditParamPO dictTypeEditParamPO) {
        dictTypeService.saveDictType(dictTypeEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dict/type/update','auth:dict:type:update')")
    @Operation(summary = "修改字典类型", description = "权限 auth:dict:type:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<DictTypePO> update(@RequestBody DictTypeEditParamPO dictTypeEditParamPO) {
        dictTypeService.updateDictType(dictTypeEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dict/type/batch/remove','auth:dict:type:batch:remove')")
    @Operation(summary = "批量删除字典类型", description = "权限 auth:dict:type:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonParam({
            @KPJsonField(name = "ids", description = "字典类型ID", required = true, dataType = "array<string>")
    })
    public KPResult<String> batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(dictTypeService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/dict/type/do/status','auth:dict:type:do:status')")
    @Operation(summary = "设置字典类型状态", description = "权限 auth:dict:type:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonParam({
            @KPJsonField(name = "dictTypeId", description = "字典类型Id", required = true),
    })
    public KPResult<Void> doStatus(@RequestBody JSONObject parameter) {
        dictTypeService.doStatus(parameter);
        return KPResult.success();
    }
}
