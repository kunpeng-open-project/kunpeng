package com.kp.framework.modules.role.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.modules.role.mapper.RoleMapper;
import com.kp.framework.modules.role.po.RolePO;
import com.kp.framework.modules.role.po.RoleProjectRelevancePO;
import com.kp.framework.modules.role.po.customer.RoleDetailsCustomerPO;
import com.kp.framework.modules.role.po.customer.RoleListCustomerPO;
import com.kp.framework.modules.role.po.param.RoleEditParamPO;
import com.kp.framework.modules.role.po.param.RoleListParamPO;
import com.kp.framework.modules.user.mapper.UserRoleMapper;
import com.kp.framework.modules.user.po.UserRolePO;
import com.kp.framework.utils.kptool.KPDatabaseUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表 服务实现类。
 * @author lipeng
 * 2025-03-31
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, RolePO> {

    @Autowired
    private RoleProjectRelevanceService roleProjectReplevanceService;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 查询角色信息列表。
     * @author lipeng
     * 2025-03-31
     * @param roleListParamPO 查询参数
     * @return com.kp.framework.entity.bo.KPResult<com.kp.framework.modules.role.po.customer.RoleListCustomerPO>
     */
    public KPResult<RoleListCustomerPO> queryPageList(RoleListParamPO roleListParamPO) {
        MPJLambdaWrapper<RolePO> wrapper = new MPJLambdaWrapper<RolePO>("role")
                .selectAll(RolePO.class, "role")
                .select(KPDatabaseUtil.groupConcat("project.project_name", false, "projectName"))
//                .select("GROUP_CONCAT( project.project_name SEPARATOR ', ' ) AS projectName")
                .leftJoin(RoleProjectRelevancePO.class, on -> on
                        .eq(RoleProjectRelevancePO::getRoleId, RolePO::getRoleId)
                        .eq(RoleProjectRelevancePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(ProjectPO.class, "project", on -> on
                        .eq(ProjectPO::getProjectId, RoleProjectRelevancePO::getProjectId)
                        .eq(ProjectPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
//                .groupBy(RolePO::getRoleId)
                .like(KPStringUtil.isNotEmpty(roleListParamPO.getProjectId()), ProjectPO::getProjectId, roleListParamPO.getProjectId())
                .like(KPStringUtil.isNotEmpty(roleListParamPO.getRoleName()), RolePO::getRoleName, roleListParamPO.getRoleName())
                .like(KPStringUtil.isNotEmpty(roleListParamPO.getRoleCode()), RolePO::getRoleCode, roleListParamPO.getRoleCode())
                .eq(KPStringUtil.isNotEmpty(roleListParamPO.getStatus()), RolePO::getStatus, roleListParamPO.getStatus());
        KPDatabaseUtil.groupFieldsBy(wrapper, "role", RolePO.class);

        Page page = PageHelper.startPage(roleListParamPO.getPageNum(), roleListParamPO.getPageSize(), roleListParamPO.getOrderBy(RolePO.class));
        page.setCountColumn("distinct role_id");
        return KPResult.list(this.baseMapper.selectJoinList(RoleListCustomerPO.class, wrapper));
    }

    /**
     * 根据角色Id查询详情。
     * @author lipeng
     * 2025-03-31
     * @param parameter 查询参数
     * @return com.kp.framework.modules.role.po.customer.RoleDetailsCustomerPO
     */
    public RoleDetailsCustomerPO queryDetailsById(JSONObject parameter) {
        RolePO rolePO = KPJsonUtil.toJavaObject(parameter, RolePO.class);
        KPVerifyUtil.notNull(rolePO.getRoleId(), "请输入roleId");

        MPJLambdaWrapper<RolePO> wrapper = new MPJLambdaWrapper<RolePO>("role")
                .selectAll(RolePO.class, "role")
                .selectCollection(ProjectPO.class, RoleDetailsCustomerPO::getProjectIds, map -> map
                        .result(ProjectPO::getProjectId)
                )
                .selectCollection(ProjectPO.class, RoleDetailsCustomerPO::getProjectNameList, map -> map
                        .result(ProjectPO::getProjectName)
                )
                .leftJoin(RoleProjectRelevancePO.class, on -> on
                        .eq(RoleProjectRelevancePO::getRoleId, RolePO::getRoleId)
                        .eq(RoleProjectRelevancePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(ProjectPO.class, "project", on -> on
                        .eq(ProjectPO::getProjectId, RoleProjectRelevancePO::getProjectId)
                        .eq(ProjectPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .eq(RolePO::getRoleId, rolePO.getRoleId());
        return this.baseMapper.selectJoinOne(RoleDetailsCustomerPO.class, wrapper);
    }

    /**
     * 新增角色信息。
     * @author lipeng
     * 2025-03-31
     * @param roleEditParamPO 新增参数
     */
    public void saveRole(RoleEditParamPO roleEditParamPO) {
        RolePO rolePO = KPJsonUtil.toJavaObjectNotEmpty(roleEditParamPO, RolePO.class);

        List<RolePO> list = this.baseMapper.selectList(Wrappers.lambdaQuery(RolePO.class)
                .eq(RolePO::getRoleName, roleEditParamPO.getRoleName()));
        if (KPStringUtil.isNotEmpty(list)) throw new KPServiceException("角色名称已存在，请勿重复添加");

        if (this.baseMapper.insert(rolePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        roleEditParamPO.setRoleId(rolePO.getRoleId());

        //维护项目
        roleProjectReplevanceService.saveOrUpdate(roleEditParamPO.getProjectIds(), rolePO);
    }

    /**
     * 修改角色信息。
     * @author lipeng
     * 2025-03-31
     * @param roleEditParamPO 修改参数
     */
    public void updateRole(RoleEditParamPO roleEditParamPO) {
        RolePO rolePO = KPJsonUtil.toJavaObjectNotEmpty(roleEditParamPO, RolePO.class);

        List<RolePO> list = this.baseMapper.selectList(Wrappers.lambdaQuery(RolePO.class)
                .ne(RolePO::getRoleId, rolePO.getRoleId())
                .eq(RolePO::getRoleName, roleEditParamPO.getRoleName()));

        if (KPStringUtil.isNotEmpty(list)) throw new KPServiceException("角色名称已存在，请勿重复添加");

        if (this.baseMapper.updateById(rolePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        //维护项目
        roleProjectReplevanceService.saveOrUpdate(roleEditParamPO.getProjectIds(), rolePO);
    }

    /**
     * 批量删除角色信息。
     * @author lipeng
     * 2025-03-31
     * @param ids 角色Id列表
     * @return java.lang.String
     */
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        //查询角色下是否有用户
        List<UserRolePO> userRoleList = userRoleMapper.selectList(Wrappers.lambdaQuery(UserRolePO.class).in(UserRolePO::getRoleId, ids));
        if (KPStringUtil.isNotEmpty(userRoleList)) throw new KPServiceException(KPStringUtil.format("{0} 下存在用户, 不允许删除", this.baseMapper.selectById(userRoleList.get(0).getRoleId()).getRoleName()));

        int row = this.baseMapper.deleteByIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
        return KPStringUtil.format("删除成功{0}条数据", row);
    }

    /**
     * 修改角色状态。
     * @author lipeng
     * 2025/3/31
     * @param parameter 修改参数
     */
    public void updateStatus(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("roleId"), "请输入角色id！");

        RolePO rolePO = this.baseMapper.selectById(parameter.getString("roleId"));
        if (rolePO == null) throw new RuntimeException("角色不存在");

        rolePO.setStatus(rolePO.getStatus().equals(YesNoEnum.YES.code()) ? YesNoEnum.NO.code() : YesNoEnum.YES.code());

        if (this.baseMapper.updateById(rolePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }

    /**
     * 查询角色下拉框。
     * @author lipeng
     * 2025/4/30
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryBO>
     */
    public List<DictionaryBO> querySelect() {
        List<RolePO> roleList = this.baseMapper.selectList(Wrappers.lambdaQuery(RolePO.class)
                .eq(RolePO::getStatus, YesNoEnum.YES.code()));

        List<DictionaryBO> body = new ArrayList<>();
        roleList.forEach(rolePO -> {
            body.add(new DictionaryBO().setValue(rolePO.getRoleId()).setLabel(rolePO.getRoleName()));
        });

        return body;
    }
}
