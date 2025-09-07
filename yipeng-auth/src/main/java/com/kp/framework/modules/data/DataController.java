package com.kp.framework.modules.data;


import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.DictionaryChildrenBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dept.po.param.DeptListParamPO;
import com.kp.framework.modules.dept.service.DeptService;
import com.kp.framework.modules.dict.service.DictDataService;
import com.kp.framework.modules.menu.service.MenuService;
import com.kp.framework.modules.post.service.PostService;
import com.kp.framework.modules.project.service.ProjectService;
import com.kp.framework.modules.role.service.RoleProjectRelevanceService;
import com.kp.framework.modules.role.service.RoleService;
import com.kp.framework.utils.kptool.KPServiceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @Author lipeng
 * @Description
 * @Date 2025/3/31
 * @return
 **/
@RestController
@Api(tags = "数据相关接口", value = "数据相关接口")
@ApiSupport(order = 0)
public class DataController {

    @Autowired
    private DictDataService dictDataService;

    @ApiOperation(value = "查询项目下拉框")
    @PostMapping("/project/select")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "manageType", value = "管理类型 0 只获取不管理的项目 1 只获取内部管理的项目 2 全部", required = true, example = "1", dataType = "int")
    })
    public KPResult<List<DictionaryBO>> queryProjectSelect(@RequestBody JSONObject parameter) {
        return KPResult.success(KPServiceUtil.getBean(ProjectService.class).queryProjectSelect(parameter));
    }

    @ApiOperation(value = "查询角色关联的项目下拉框")
    @PostMapping("/role/project/select")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "roleId", value = "角色Id", required = true, example = "e3ae1261c42dcb0e195fb9b9d9298bfe"),
    })
    public KPResult<List<DictionaryBO>> queryRoleProjectSelect(@RequestBody JSONObject parameter) {
        return KPResult.success(KPServiceUtil.getBean(RoleProjectRelevanceService.class).queryRoleProjectSelect(parameter));
    }


    @ApiOperation(value = "查询部门下拉框")
    @PostMapping("/dept/select")
    @KPApiJsonlParamMode(component = DeptListParamPO.class, includes = "isTree")
    public KPResult<List<DictionaryChildrenBO>> queryProjectSelect(@RequestBody DeptListParamPO deptListParamPO) {
        return KPResult.success(KPServiceUtil.getBean(DeptService.class).queryDeptSelect(deptListParamPO));
    }


    @ApiOperation(value = "查询菜单下拉框")
    @PostMapping("/menu/select")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "projectId", value = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407"),
            @ApiModelProperty(name = "isTree", value = "是否树形结构 1 树形 2 列表", required = true, example = "1", dataType = "int"),
            @ApiModelProperty(name = "isInterface", value = "是否只显示接口 1 全部 2 只要接口 （默认全部）", example = "1", dataType = "int")
    })
    public KPResult<List<DictionaryChildrenBO>> queryMenuSelect(@RequestBody JSONObject parameter) {
        return KPResult.success(KPServiceUtil.getBean(MenuService.class).queryMenuSelect(parameter));
    }


    @ApiOperation(value = "查询角色下拉框")
    @PostMapping("/role/select")
    public KPResult<List<DictionaryBO>> queryRoleSelect() {
        return KPResult.success(KPServiceUtil.getBean(RoleService.class).querySelect());
    }

    @ApiOperation(value = "查询岗位下拉框")
    @PostMapping("/post/select")
    public KPResult<List<DictionaryBO>> queryPostSelect() {
        return KPResult.success(KPServiceUtil.getBean(PostService.class).querySelect());
    }


    @ApiOperation(value = "查询数据字典")
    @PostMapping(value = "/open/dict/data/list")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "dictType", value = "字典类型", example = "sex", required = true),
            @ApiModelProperty(name = "projectCode", value = "项目编号", example = "authentication", required = true)
    })
    public KPResult<List<DictionaryBO>> queryDictData(@RequestBody JSONObject parameter) {
        return KPResult.success(dictDataService.queryDictData(parameter));
    }


    @ApiOperation(value = "批量查询数据字典")
    @PostMapping(value = "/open/dict/data/batch/list")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "projectCode", value = "项目编号", example = "authentication", required = true),
            @ApiModelProperty(name = "dictTypes", value = "字典类型集合", required = true, example = "[\"sex\",\"age\"]", dataType = "list")
    })
    public KPResult<Map<String, List<DictionaryBO>>> queryDictDatas(@RequestBody JSONObject parameter) {
        return KPResult.success(dictDataService.queryDictDatas(parameter));
    }

}
