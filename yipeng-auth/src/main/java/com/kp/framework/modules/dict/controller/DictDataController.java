package com.kp.framework.modules.dict.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dict.mapper.DictDataMapper;
import com.kp.framework.modules.dict.po.DictDataPO;
import com.kp.framework.modules.dict.po.customer.DictDataDetailsCustomerPO;
import com.kp.framework.modules.dict.po.param.DictDataEditParamPO;
import com.kp.framework.modules.dict.po.param.DictDataListParamPO;
import com.kp.framework.modules.dict.service.DictDataService;
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
 * 字典数据表相关接口。
 * @author lipeng
 * 2025-07-03
 */
@RestController
@RequestMapping("/auth/dict/data")
@Tag(name = "字典数据相关接口")
@ApiSupport(author = "lipeng", order = 31)
public class DictDataController {

    @Autowired
    private DictDataService dictDataService;

    @PreAuthorize("hasPermission('/auth/dict/data/page/list', 'auth:dict:data:page:list')")
    @Operation(summary = "查询字典数据分页列表", description = "权限 auth:dict:data:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<DictDataPO> queryPageList(@RequestBody DictDataListParamPO dictDataListParamPO) {
        return dictDataService.queryPageList(dictDataListParamPO);
    }


    @PreAuthorize("hasPermission('/auth/dict/data/details','auth:dict:data:details')")
    @Operation(summary = "根据字典编码ID查询详情", description = "权限 auth:dict:data:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "dictDataId", description = "字典编码ID", required = true)
    })
    public KPResult<DictDataDetailsCustomerPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(dictDataService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/dict/data/save','auth:dict:data:save')")
    @Operation(summary = "新增字典数据", description = "权限 auth:dict:data:save")
    @PostMapping("/save")
    @KPObjectChangeLogNote(parentMapper = DictDataMapper.class, identification = "dictDataId,dict_data_id", operateType = ObjectChangeLogOperateType.ADD, businessType = "字典数据")
    @KPVerifyNote
    @KPApiJsonParamMode(component = DictDataEditParamPO.class, ignores = "dictDataId")
    public KPResult<DictDataPO> save(@RequestBody DictDataEditParamPO dictDataEditParamPO) {
        dictDataService.saveDictData(dictDataEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dict/data/update','auth:dict:data:update')")
    @Operation(summary = "修改字典数据", description = "权限 auth:dict:data:update")
    @PostMapping("/update")
    @KPObjectChangeLogNote(parentMapper = DictDataMapper.class, identification = "dictDataId,dict_data_id", businessType = "字典数据")
    @KPVerifyNote
    public KPResult<DictDataPO> update(@RequestBody DictDataEditParamPO dictDataEditParamPO) {
        dictDataService.updateDictData(dictDataEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dict/data/batch/remove','auth:dict:data:batch:remove')")
    @Operation(summary = "批量删除字典数据", description = "权限 auth:dict:data:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonParam({
            @KPJsonField(name = "ids", description = "字典编码ID", required = true, dataType = "array<string>")
    })
    @KPObjectChangeLogNote(parentMapper = DictDataMapper.class, identification = "dictDataId,dict_data_id", operateType = ObjectChangeLogOperateType.DELETE, businessType = "字典数据")
    public KPResult<String> batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(dictDataService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/dict/data/do/status','auth:dict:data:do:status')")
    @Operation(summary = "设置字典数据状态", description = "权限 auth:dict:data:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonParam({
            @KPJsonField(name = "dictDataId", description = "字典数据Id", required = true),
    })
    @KPObjectChangeLogNote(parentMapper = DictDataMapper.class, identification = "dictDataId,dict_data_id", businessType = "字典数据")
    public KPResult<Void> doStatus(@RequestBody JSONObject parameter) {
        dictDataService.doStatus(parameter);
        return KPResult.success();
    }
}
