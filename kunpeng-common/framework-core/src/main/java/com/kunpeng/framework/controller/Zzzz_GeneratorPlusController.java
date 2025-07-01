package com.kunpeng.framework.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.entity.po.GeneratorPO;
import com.kunpeng.framework.utils.kptool.KPMyBatisPlusNew;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 如果系统最后一个加载的接口加了@KPApiGlobalNote/@KPApiJsonlNote/@ApiSimpleModel，这个接口动态生成的参数在swagger文档里将找不到
 * 出现这种情况就加上这个Controller，保证这个Controller是最后一个Controller => 保证最后一个接口没有使用以上3个注解
 */

/**
 * @Author lipeng
 * @Description mybatis plus代码生成工具
 * @Date 2022/5/20 18:45
 * @return
 **/
@Controller
@RequestMapping("/open/entrance/admin")
@Api(value = "逆向工程-开发人员", tags = "逆向工程-开发人员")
@ApiSupport(author = "李鹏",order = -9999)
public class Zzzz_GeneratorPlusController {

    @Autowired
    private KPMyBatisPlusNew kpMyBatisPlusNew;



    @ApiOperation(value = "代码生成")
    @PostMapping("/generator")
    @ResponseBody
    public KPResult queryInitiateProject(GeneratorPO generatorPO){
        try {
            kpMyBatisPlusNew.firstGenerate(generatorPO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return KPResult.success();
    }



    @ApiOperation(value = "重新生成表结构")
    @PostMapping("/replace/generator")
    @ResponseBody
    public KPResult queryInitiateProjectReplace(GeneratorPO generatorPO){
        try {
            kpMyBatisPlusNew.againGenerate(generatorPO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return KPResult.success();
    }
}
