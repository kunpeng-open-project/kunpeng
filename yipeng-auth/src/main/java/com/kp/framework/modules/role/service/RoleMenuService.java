package com.kp.framework.modules.role.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.menu.mapper.MenuMapper;
import com.kp.framework.modules.menu.po.MenuPO;
import com.kp.framework.modules.menu.po.customer.MenuCustomerPO;
import com.kp.framework.modules.role.mapper.RoleMapper;
import com.kp.framework.modules.role.mapper.RoleMenuMapper;
import com.kp.framework.modules.role.mapper.RoleProjectRelevanceMapper;
import com.kp.framework.modules.role.po.RoleMenuPO;
import com.kp.framework.modules.role.po.RolePO;
import com.kp.framework.modules.role.po.RoleProjectRelevancePO;
import com.kp.framework.modules.role.po.param.RoleMenuInstallParamPO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenuPO> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleProjectRelevanceMapper roleProjectRelevanceMapper;

    @Autowired
    private MenuMapper menuMapper;


    /**
     * @Author lipeng
     * @Description 设置菜单权限
     * @Date 2024/4/26
     * @param roleMenuInstallParamPO
     * @return void
     **/
    public void doMenuInstall(RoleMenuInstallParamPO roleMenuInstallParamPO) {
        RolePO rolePO = roleMapper.selectById(roleMenuInstallParamPO.getRoleId());
        if (rolePO == null) throw new RuntimeException("角色不存在");

        List<RoleProjectRelevancePO> authRoleProjectRelevanceList = roleProjectRelevanceMapper.selectList(Wrappers.lambdaQuery(RoleProjectRelevancePO.class).eq(RoleProjectRelevancePO::getRoleId, roleMenuInstallParamPO.getRoleId()));
        if (authRoleProjectRelevanceList != null
                && authRoleProjectRelevanceList.size() != 0
                && !authRoleProjectRelevanceList.stream().map(RoleProjectRelevancePO::getRoleId).collect(Collectors.toList()).contains(roleMenuInstallParamPO.getRoleId())
        ) {
            throw new RuntimeException("该角色没有分配该项目，请在角色里面设置所属项目");
        }


        //删除历史菜单
        List<String> armIds = this.baseMapper.selectList(new LambdaQueryWrapper<>(RoleMenuPO.class)
                .eq(RoleMenuPO::getRoleId, rolePO.getRoleId())
                .eq(RoleMenuPO::getProjectId, roleMenuInstallParamPO.getProjectId())
        ).stream().map(RoleMenuPO::getArmId).collect(Collectors.toList());
        if (armIds != null && armIds.size() != 0) this.baseMapper.deleteAllByIds(armIds);

        if (KPStringUtil.isEmpty(roleMenuInstallParamPO.getMenuIds())) return;

        List<RoleMenuPO> roleMenuPOList = new ArrayList<>();
        roleMenuInstallParamPO.getMenuIds().forEach(menuId -> {
            roleMenuPOList.add(new RoleMenuPO()
                    .setRoleId(roleMenuInstallParamPO.getRoleId())
                    .setProjectId(roleMenuInstallParamPO.getProjectId())
                    .setMenuId(menuId));
        });

        if (this.baseMapper.insertBatchSomeColumn(roleMenuPOList) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 查询选中的菜单权限
     * @Date 2024/4/26
     * @param parameter
     * @return java.util.List<java.lang.String>
     **/
    public List<String> queryMenuInstall(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("roleId"), "请输入角色Id");
        KPVerifyUtil.notNull(parameter.getString("projectId"), "请输入项目Id");

        MPJLambdaWrapper<RoleMenuPO> wrapper = new MPJLambdaWrapper<RoleMenuPO>()
                .selectAll(RoleMenuPO.class)
                .leftJoin(MenuPO.class, "menu", on -> on
                        .eq(MenuPO::getMenuId, RoleMenuPO::getMenuId)
                        .eq(MenuPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                        .eq(MenuPO::getIsEnable, 1)
                )
                .disableSubLogicDel()
                .eq(RoleMenuPO::getRoleId, parameter.getString("roleId"))
                .eq(RoleMenuPO::getProjectId, parameter.getString("projectId"));

        List<MenuCustomerPO> menuPOList = KPJsonUtil.toJavaObjectList(menuMapper.selectList(Wrappers.lambdaQuery(MenuPO.class).eq(MenuPO::getProjectId, parameter.getString("projectId")).eq(MenuPO::getIsEnable, 1)), MenuCustomerPO.class);
        // 构建树形结构
        Map<String, List<MenuCustomerPO>> map = menuPOList.stream().collect(Collectors.groupingBy(MenuCustomerPO::getParentId));
        List<String> row = new ArrayList<>();
        this.baseMapper.selectJoinList(RoleMenuPO.class, wrapper).forEach(roleMenuPO -> {
            if (map.get(roleMenuPO.getMenuId()) == null)  row.add(roleMenuPO.getMenuId());
        });
        return row;
    }
}
