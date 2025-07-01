package com.kunpeng.framework.controller;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.jfzh.framework.annotation.KPApiJsonlNote;
//import com.jfzh.framework.controller.server.ImportServer;
//import com.jfzh.framework.entity.internal.CommonResult;
//import com.jfzh.framework.entity.po.ImportCoursePO;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiModelProperty;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
///**
// * @Author lipeng
// * @Description 导入
// * @Date 2022/6/20 19:48
// * @return
// **/
//@Controller
//@RequestMapping("/data/import")
//@Api(value = "B_导入公用接口", tags = "B_导入公用接口")
//public class ImportController {
//
//    @Autowired
//    private ImportServer importServer;
//
//    /**
//     * @Author lipeng
//     * @Description 导入结果查询
//     * @Date 2022/6/20 19:30
//     * @param identification
//     * @return com.daoben.framework.entity.bo.KPResult
//     **/
//    @GetMapping(value = "/result")
//    @ApiOperation(value = "导入结果查询", response = ImportCoursePO.class)
//    @ApiImplicitParam(name = "identification", value = "唯一标识", required = true)
//    @ResponseBody
//    public CommonResult doImportResult(String identification) {
//        return CommonResult.success(importServer.doImportResult(identification));
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 导入失败内容导出
//     * @Date 2022/6/20 20:28
//     * @param jsonObject
//     * @return void
//     **/
//    @PostMapping(value = "/fail/list")
//    @ApiOperation(value = "导入失败内容导出")
//    @KPApiJsonlNote({
//        @ApiModelProperty(name = "identification", value = "唯一标识", required = true),
//        @ApiModelProperty(name = "errorFilePath", value = "异常文件路径", required = true)
//    })
//    @ResponseBody
//    public void doImportFailList(@RequestBody JSONObject jsonObject) {
//        importServer.doImportFailList(jsonObject);
//    }
//
//}
