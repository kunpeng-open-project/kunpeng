package com.kunpeng.framework.modules.role.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kunpeng.framework.constant.ReturnFinishedMessageConstant;
import com.kunpeng.framework.entity.bo.DictionaryBO;
import com.kunpeng.framework.enums.DeleteFalgEnum;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.project.mapper.ProjectMapper;
import com.kunpeng.framework.modules.project.po.ProjectPO;
import com.kunpeng.framework.modules.role.mapper.RoleProjectRelevanceMapper;
import com.kunpeng.framework.modules.role.po.RolePO;
import com.kunpeng.framework.modules.role.po.RoleProjectRelevancePO;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lipeng
 * @Description 角色项目关联表 服务实现类
 * @Date 2025-03-31
 **/
@Service
public class RoleProjectRelevanceService extends ServiceImpl<RoleProjectRelevanceMapper, RoleProjectRelevancePO> {


    @Autowired
    private ProjectMapper projectMapper;


    /**
     * @Author lipeng
     * @Description 新增或者修改角色项目关联关系
     * @Date 2025/3/31
     * @param projectIds
     * @param rolePO
     * @return void
     **/
    public void saveOrUpdate(List<String> projectIds, RolePO rolePO) {
        if (KPStringUtil.isEmpty(projectIds)) return;
        //删除历史
        List<String> arprIds = this.baseMapper.selectList(Wrappers.lambdaQuery(RoleProjectRelevancePO.class).in(RoleProjectRelevancePO::getRoleId, rolePO.getRoleId())).stream().map(RoleProjectRelevancePO::getArprId).collect(Collectors.toList());
        if (arprIds != null && arprIds.size() != 0) this.baseMapper.deleteAllByIds(arprIds);
        List<RoleProjectRelevancePO> list = new ArrayList<>();
        projectIds.forEach(projectId -> {
            RoleProjectRelevancePO rp = new RoleProjectRelevancePO();
            rp.setRoleId(rolePO.getRoleId());
            rp.setProjectId(projectId);
            list.add(rp);
        });

        if (list.size() != 0 && this.baseMapper.insertBatchSomeColumn(list) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 查询角色关联的项目下拉框
     * @Date 2025/5/10
     * @param parameter
     * @return java.util.List<com.kunpeng.framework.entity.bo.DictionaryBO>
     **/
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
