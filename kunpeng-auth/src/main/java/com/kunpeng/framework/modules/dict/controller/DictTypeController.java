package com.kunpeng.framework.modules.dict.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.KPApiJsonlParamMode;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.dict.po.DictTypePO;
import com.kunpeng.framework.modules.dict.po.param.DictTypeEditParamPO;
import com.kunpeng.framework.modules.dict.po.param.DictTypeListParamPO;
import com.kunpeng.framework.modules.dict.service.DictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @Author lipeng
* @Description  字典类型表相关接口
* @Date 2025-07-03
**/
@RestController
@RequestMapping("/auth/dict/type")
@Api(tags = "字典类型相关接口", value = "字典类型相关接口")
@ApiSupport(order = 12)
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;


    @PreAuthorize("hasPermission('/auth/dict/type/page/list', 'auth:dict:type:page:list')")
    @ApiOperation(value = "查询字典类型分页列表", notes = "权限 auth:dict:type:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<DictTypePO> queryPageList(@RequestBody DictTypeListParamPO dictTypeListParamPO){
        return KPResult.list(dictTypeService.queryPageList(dictTypeListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/dict/type/details','auth:dict:type:details')")
    @ApiOperation(value = "根据字典类型ID查询详情", notes="权限 auth:dict:type:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "dictTypeId", value = "字典类型ID", required = true)
    })
    public KPResult<DictTypePO> queryDetailsById(@RequestBody JSONObject parameter){
        return KPResult.success(dictTypeService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/dict/type/save','auth:dict:type:save')")
    @ApiOperation(value = "新增字典类型", notes="权限 auth:dict:type:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = DictTypeEditParamPO.class, ignores = "dictTypeId")
    public KPResult<DictTypePO> save(@RequestBody DictTypeEditParamPO dictTypeEditParamPO){
        dictTypeService.saveDictType(dictTypeEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dict/type/update','auth:dict:type:update')")
    @ApiOperation(value = "修改字典类型", notes="权限 auth:dict:type:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<DictTypePO> update(@RequestBody DictTypeEditParamPO dictTypeEditParamPO){
        dictTypeService.updateDictType(dictTypeEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/dict/type/batch/remove','auth:dict:type:batch:remove')")
    @ApiOperation(value = "批量删除字典类型", notes="权限 auth:dict:type:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "ids", value = "字典类型ID", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids){
        return KPResult.success(dictTypeService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/dict/type/do/status','auth:dict:type:do:status')")
    @ApiOperation(value = "设置字典类型状态", notes="权限 auth:dict:type:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "dictTypeId", value = "字典类型Id", required = true),
    })
    public KPResult doStatus(@RequestBody JSONObject parameter) {
        dictTypeService.doStatus(parameter);
        return KPResult.success();
    }
}
