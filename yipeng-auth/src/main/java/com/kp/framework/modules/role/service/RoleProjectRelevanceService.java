package com.kp.framework.modules.role.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.project.mapper.ProjectMapper;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.modules.role.mapper.RoleProjectRelevanceMapper;
import com.kp.framework.modules.role.po.RolePO;
import com.kp.framework.modules.role.po.RoleProjectRelevancePO;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色项目关联表 服务实现类。
 * @author lipeng
 * 2025-03-31
 */
@Service
public class RoleProjectRelevanceService extends ServiceImpl<RoleProjectRelevanceMapper, RoleProjectRelevancePO> {


    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 新增或者修改角色项目关联关系。
     * @author lipeng
     * 2025/3/31
     * @param projectIds 项目id
     * @param rolePO 角色
     */
    public void saveOrUpdate(List<String> projectIds, RolePO rolePO) {
        if (KPStringUtil.isEmpty(projectIds)) return;
        //删除历史
        List<String> arprIds = this.baseMapper.selectList(Wrappers.lambdaQuery(RoleProjectRelevancePO.class).in(RoleProjectRelevancePO::getRoleId, rolePO.getRoleId())).stream().map(RoleProjectRelevancePO::getArprId).collect(Collectors.toList());
        if (KPStringUtil.isNotEmpty(arprIds)) this.baseMapper.kpDeleteAllByIds(arprIds);
        List<RoleProjectRelevancePO> list = new ArrayList<>();
        projectIds.forEach(projectId -> {
            RoleProjectRelevancePO rp = new RoleProjectRelevancePO();
            rp.setRoleId(rolePO.getRoleId());
            rp.setProjectId(projectId);
            list.add(rp);
        });

        if (KPStringUtil.isNotEmpty(list) && this.baseMapper.kpInsertBatchSomeColumn(list) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }

    /**
     * 查询角色关联的项目下拉框。
     * @author lipeng
     * 2025/5/10
     * @param parameter 查询参数
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryBO>
     */
    public List<DictionaryBO> queryRoleProjectSelect(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("roleId"), "请输入角色id！");

        MPJLambdaWrapper<ProjectPO> wrapper = new MPJLambdaWrapper<ProjectPO>()
                .selectAll(ProjectPO.class)
                .leftJoin(RoleProjectRelevancePO.class, "roleProjectRelevancePO", on -> on
                        .eq(RoleProjectRelevancePO::getProjectId, ProjectPO::getProjectId)
                        .eq(RoleProjectRelevancePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                ).disableSubLogicDel()
                .eq(RoleProjectRelevancePO::getRoleId, parameter.getString("roleId"))
                .eq(ProjectPO::getStatus, YesNoEnum.YES.code())
                .orderByAsc(ProjectPO::getCreateDate);

        List<ProjectPO> projectPOList = projectMapper.selectJoinList(ProjectPO.class, wrapper);
        List<DictionaryBO> body = new ArrayList<>();
        projectPOList.forEach(projectPO -> {
            body.add(new DictionaryBO()
                    .setLabel(projectPO.getProjectName())
                    .setValue(projectPO.getProjectId()));
        });
        return body;
    }
}
