package com.kp.framework.api;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dict.po.DictTypePO;
import com.kp.framework.modules.dict.service.DictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dict")
@Tag(name = "API-数据字典相关接口")
@ApiSupport(author = "lipeng", order = 10)
public class DictController {

    @Autowired
    private DictTypeService dictTypeService;

    @Operation(summary = "根据字典类型Id集合查询字典类型列表")
    @PostMapping("/query/ids/list")
    @KPApiJsonParam({
            @KPJsonField(name = "dictTypeId", description = "字典类型Id集合", required = true, dataType = "array<String>")
    })
    public KPResult<List<DictTypePO>> queryDictTypeIdList(@RequestBody List<String> dictTypeId) {
        return KPResult.success(dictTypeService.queryDictTypeIdList(dictTypeId));
    }
}
