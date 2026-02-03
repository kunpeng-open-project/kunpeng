package com.kp.framework.modules.data;


import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.DictionaryChildrenBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.dept.po.param.DeptListParamPO;
import com.kp.framework.modules.dept.service.DeptService;
import com.kp.framework.modules.dict.service.DictDataService;
import com.kp.framework.modules.dict.service.DictTypeService;
import com.kp.framework.modules.menu.service.MenuService;
import com.kp.framework.modules.post.service.PostService;
import com.kp.framework.modules.project.service.ProjectService;
import com.kp.framework.modules.role.service.RoleProjectRelevanceService;
import com.kp.framework.modules.role.service.RoleService;
import com.kp.framework.utils.kptool.KPServiceUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据相关接口
 * @author lipeng
 * 2025/3/31
 */
@RestController
@Tag(name = "数据相关接口")
@ApiSupport(author = "lipeng", order = 7)
public class DataController {

    @Autowired
    private DictDataService dictDataService;

    @Operation(summary = "查询项目下拉框")
    @PostMapping("/project/select")
    @KPApiJsonParam({
            @KPJsonField(name = "manageType", description = "管理类型 0 只获取不管理的项目 1 只获取内部管理的项目 2 全部", required = true, example = "1", dataType = "int")
    })
    public KPResult<List<DictionaryBO>> queryProjectSelect(@RequestBody JSONObject parameter) {
        return KPResult.success(KPServiceUtil.getBean(ProjectService.class).queryProjectSelect(parameter));
    }

    @Operation(summary = "查询角色关联的项目下拉框")
    @PostMapping("/role/project/select")
    @KPApiJsonParam({
            @KPJsonField(name = "roleId", description = "角色Id", required = true, example = "e3ae1261c42dcb0e195fb9b9d9298bfe"),
    })
    public KPResult<List<DictionaryBO>> queryRoleProjectSelect(@RequestBody JSONObject parameter) {
        return KPResult.success(KPServiceUtil.getBean(RoleProjectRelevanceService.class).queryRoleProjectSelect(parameter));
    }


    @Operation(summary = "查询部门下拉框")
    @PostMapping("/dept/select")
    @KPApiJsonParamMode(component = DeptListParamPO.class, includes = "isTree")
    public KPResult<List<DictionaryChildrenBO>> queryProjectSelect(@RequestBody DeptListParamPO deptListParamPO) {
        return KPResult.success(KPServiceUtil.getBean(DeptService.class).queryDeptSelect(deptListParamPO));
    }


    @Operation(summary = "查询菜单下拉框")
    @PostMapping("/menu/select")
    @KPApiJsonParam({
            @KPJsonField(name = "projectId", description = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407"),
            @KPJsonField(name = "isTree", description = "是否树形结构 1 树形 2 列表", required = true, example = "1", dataType = "int"),
            @KPJsonField(name = "isInterface", description = "是否只显示接口 1 全部 2 只要接口 （默认全部）", example = "1", dataType = "int")
    })
    public KPResult<List<DictionaryChildrenBO>> queryMenuSelect(@RequestBody JSONObject parameter) {
        return KPResult.success(KPServiceUtil.getBean(MenuService.class).queryMenuSelect(parameter));
    }


    @Operation(summary = "查询角色下拉框")
    @PostMapping("/role/select")
    public KPResult<List<DictionaryBO>> queryRoleSelect() {
        return KPResult.success(KPServiceUtil.getBean(RoleService.class).querySelect());
    }

    @Operation(summary = "查询岗位下拉框")
    @PostMapping("/post/select")
    public KPResult<List<DictionaryBO>> queryPostSelect() {
        return KPResult.success(KPServiceUtil.getBean(PostService.class).querySelect());
    }


    @Operation(summary = "查询数据字典类型下拉框")
    @PostMapping("/dict/type/select")
    @KPApiJsonParam({
            @KPJsonField(name = "projectId", description = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    })
    public KPResult<List<DictionaryChildrenBO>> queryDictTypeSelect(@RequestBody JSONObject parameter) {
        return KPResult.success(KPServiceUtil.getBean(DictTypeService.class).queryDictTypeSelect(parameter));
    }

    @Operation(summary = "查询数据字典")
    @PostMapping(value = "/open/dict/data/list")
    @KPApiJsonParam({
            @KPJsonField(name = "dictType", description = "字典类型", example = "sex", required = true),
            @KPJsonField(name = "projectCode", description = "项目编号", example = "authentication", required = true)
    })
    public KPResult<List<DictionaryBO>> queryDictData(@RequestBody JSONObject parameter) {
        return KPResult.success(dictDataService.queryDictData(parameter));
    }


    @Operation(summary = "批量查询数据字典")
    @PostMapping(value = "/open/dict/data/batch/list")
    @KPApiJsonParam({
            @KPJsonField(name = "projectCode", description = "项目编号", example = "authentication", required = true),
            @KPJsonField(name = "dictTypes", description = "字典类型集合", required = true, example = "[\"sex\",\"age\"]", dataType = "array<string>")
    })
    public KPResult<Map<String, List<DictionaryBO>>> queryDictDatas(@RequestBody JSONObject parameter) {
        return KPResult.success(dictDataService.queryDictDatas(parameter));
    }

}
