package com.kp.framework.modules.role.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.entity.bo.DictionaryBO;
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
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lipeng
 * @Description 角色信息表 服务实现类
 * @Date 2025-03-31
 **/
@Service
public class RoleService extends ServiceImpl<RoleMapper, RolePO> {

    @Autowired
    private RoleProjectRelevanceService roleProjectReplevanceService;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * @Author lipeng
     * @Description 查询角色信息列表
     * @Date 2025-03-31
     * @param roleListParamPO
     * @return java.util.List<RolePO>
     **/
    public List<RoleListCustomerPO> queryPageList(RoleListParamPO roleListParamPO) {
        MPJLambdaWrapper<RolePO> wrapper = new MPJLambdaWrapper<RolePO>("role")
                .selectAll(RolePO.class, "role")
                .select("GROUP_CONCAT( project.project_name SEPARATOR ', ' ) AS projectName")
                .leftJoin(RoleProjectRelevancePO.class, on -> on
                        .eq(RoleProjectRelevancePO::getRoleId, RolePO::getRoleId)
                        .eq(RoleProjectRelevancePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(ProjectPO.class, "project", on -> on
                        .eq(ProjectPO::getProjectId, RoleProjectRelevancePO::getProjectId)
                        .eq(ProjectPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .groupBy(RolePO::getRoleId)
                .like(KPStringUtil.isNotEmpty(roleListParamPO.getProjectId()), ProjectPO::getProjectId, roleListParamPO.getProjectId())
                .like(KPStringUtil.isNotEmpty(roleListParamPO.getRoleName()), RolePO::getRoleName, roleListParamPO.getRoleName())
                .eq(KPStringUtil.isNotEmpty(roleListParamPO.getStatus()), RolePO::getStatus, roleListParamPO.getStatus());
        Page page = PageHelper.startPage(roleListParamPO.getPageNum(), roleListParamPO.getPageSize(), roleListParamPO.getOrderBy(RolePO.class));
        page.setCountColumn("distinct role_id");
        return this.baseMapper.selectJoinList(RoleListCustomerPO.class, wrapper);
    }


    /**
     * @Author lipeng
     * @Description 根据角色Id查询详情
     * @Date 2025-03-31
     * @param parameter
     * @return RolePO
     **/
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
     * @Author lipeng
     * @Description 新增角色信息
     * @Date 2025-03-31
     * @param roleEditParamPO
     * @return void
     **/
    public void saveRole(RoleEditParamPO roleEditParamPO) {
        RolePO rolePO = KPJsonUtil.toJavaObjectNotEmpty(roleEditParamPO, RolePO.class);

        List<RolePO> list = this.baseMapper.selectList(Wrappers.lambdaQuery(RolePO.class)
                .eq(RolePO::getRoleName, roleEditParamPO.getRoleName()));
        if (list.size() > 0) throw new KPServiceException("角色名称已存在，请勿重复添加");

        if (this.baseMapper.insert(rolePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        //维护项目
        roleProjectReplevanceService.saveOrUpdate(roleEditParamPO.getProjectIds(), rolePO);
    }


    /**
     * @Author lipeng
     * @Description 修改角色信息
     * @Date 2025-03-31
     * @param roleEditParamPO
     * @return void
     **/
    public void updateRole(RoleEditParamPO roleEditParamPO) {
        RolePO rolePO = KPJsonUtil.toJavaObjectNotEmpty(roleEditParamPO, RolePO.class);

        List<RolePO> list = this.baseMapper.selectList(Wrappers.lambdaQuery(RolePO.class)
                .ne(RolePO::getRoleId, rolePO.getRoleId())
                .eq(RolePO::getRoleName, roleEditParamPO.getRoleName()));

        if (list.size() > 0) throw new KPServiceException("角色名称已存在，请勿重复添加");

        if (this.baseMapper.updateById(rolePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        //维护项目
        roleProjectReplevanceService.saveOrUpdate(roleEditParamPO.getProjectIds(), rolePO);
    }


    /**
     * @Author lipeng
     * @Description 批量删除角色信息
     * @Date 2025-03-31
     * @param ids
     * @return String
     **/
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        //查询角色下是否有用户
        List<UserRolePO> userRoleList = userRoleMapper.selectList(Wrappers.lambdaQuery(UserRolePO.class).in(UserRolePO::getRoleId, ids));
        if (userRoleList.size() != 0) throw new KPServiceException(KPStringUtil.format("{0} 下存在用户, 不允许删除",  this.baseMapper.selectById(userRoleList.get(0).getRoleId()).getRoleName()));

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
        return KPStringUtil.format("删除成功{0}条数据", row);
    }


    /**
     * @Author lipeng
     * @Description 修改角色状态
     * @Date 2025/3/31
     * @param parameter
     * @return void
     **/
    public void updateStatus(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("roleId"), "请输入角色id！");

        RolePO rolePO = this.baseMapper.selectById(parameter.getString("roleId"));
        if (rolePO == null) throw new RuntimeException("角色不存在");

        rolePO.setStatus(rolePO.getStatus().equals(YesNoEnum.YES.code()) ? YesNoEnum.NO.code() : YesNoEnum.YES.code());

        if (this.baseMapper.updateById(rolePO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }



    /**
     * @Author lipeng
     * @Description 查询角色下拉框
     * @Date 2025/4/30
     * @param
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryBO>
     **/
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
