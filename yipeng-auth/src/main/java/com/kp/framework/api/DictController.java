package com.kp.framework.api;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dict.po.DictTypePO;
import com.kp.framework.modules.dict.service.DictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dict")
@Api(tags = "API-数据字典相关接口", value = "API-数据字典相关接口")
@ApiSupport(order = 0)
public class DictController {
    @Autowired
    private DictTypeService dictTypeService;

    @ApiOperation(value = "根据字典类型Id集合查询字典类型列表")
    @PostMapping("/query/ids/list")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "dictTypeId", value = "字典类型Id集合", required = true, dataType = "list")
    })
    public KPResult<List<DictTypePO>> queryDictTypeIdList(@RequestBody List<String> dictTypeId) {
        return KPResult.success(dictTypeService.queryDictTypeIdList(dictTypeId));
    }
}
