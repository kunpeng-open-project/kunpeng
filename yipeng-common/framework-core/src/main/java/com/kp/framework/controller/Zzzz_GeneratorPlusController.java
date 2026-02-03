package com.kp.framework.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.entity.po.GeneratorPO;
import com.kp.framework.utils.kptool.KPMyBatisPlusNew;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * mybatis plus代码生成工具。
 * 如果系统最后一个加载的接口加了@KPApiGlobalNote/@KPApiJsonlNote/@ApiSimpleModel，这个接口动态生成的参数在swagger文档里将找不到
 * 出现这种情况就加上这个Controller，保证这个Controller是最后一个Controller => 保证最后一个接口没有使用以上3个注解
 * @author lipeng
 * 2022/5/20
 */
@Controller
@RequestMapping("/entrance/admin")
@Tag(name = "逆向工程-开发人员", description = "逆向工程-开发人员")
@ApiSupport(author = "lipeng", order = 1)
public class Zzzz_GeneratorPlusController {

    @Autowired
    private KPMyBatisPlusNew kpMyBatisPlusNew;


    @Operation(summary = "代码生成")
    @PostMapping("/generator")
    @ApiOperationSupport(author = "lipeng", order = 1)
    @ResponseBody
    public KPResult<String> queryInitiateProject(GeneratorPO generatorPO) {
        kpMyBatisPlusNew.firstGenerate(generatorPO);
        return KPResult.success("操作成功");
    }


    @Operation(summary = "重新生成表结构")
    @PostMapping("/replace/generator")
    @ApiOperationSupport(author = "lipeng", order = 2)
    @ResponseBody
    public KPResult<String> queryInitiateProjectReplace(GeneratorPO generatorPO) {
        kpMyBatisPlusNew.againGenerate(generatorPO);
        return KPResult.success("操作成功");
    }
}
