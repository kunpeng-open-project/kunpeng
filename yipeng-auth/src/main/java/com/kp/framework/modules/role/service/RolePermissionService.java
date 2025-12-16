package com.kp.framework.modules.role.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.common.enums.PermissionTypeEnum;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.dept.mapper.DeptMapper;
import com.kp.framework.modules.dept.po.DeptPO;
import com.kp.framework.modules.dept.po.customer.DeptCustomerPO;
import com.kp.framework.modules.role.mapper.RoleMapper;
import com.kp.framework.modules.role.mapper.RolePermissionMapper;
import com.kp.framework.modules.role.mapper.RoleProjectRelevanceMapper;
import com.kp.framework.modules.role.po.RolePO;
import com.kp.framework.modules.role.po.RolePermissionPO;
import com.kp.framework.modules.role.po.RoleProjectRelevancePO;
import com.kp.framework.modules.role.po.customer.QueryPermissionCustomerPO;
import com.kp.framework.modules.role.po.param.RolePermissionInstallParamPO;
import com.kp.framework.modules.user.mapper.UserMapper;
import com.kp.framework.modules.user.po.UserDeptPO;
import com.kp.framework.modules.user.po.UserPO;
import com.kp.framework.modules.user.po.customer.QueryUserCustomerPO;
import com.kp.framework.utils.kptool.KPCollectionUtil;
import com.kp.framework.utils.kptool.KPDatabaseUtil;
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
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermissionPO> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleProjectRelevanceMapper roleProjectRelevanceMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeptMapper deptMapper;


    /**
     * @param rolePermissionInstallParamPO
     * @return void
     * @Author lipeng
     * @Description 设置数据权限
     * @Date 2024/5/6
     **/
    public void doPermissionInstall(RolePermissionInstallParamPO rolePermissionInstallParamPO) {
        RolePO rolePO = roleMapper.selectById(rolePermissionInstallParamPO.getRoleId());
        if (rolePO == null) throw new RuntimeException("角色不存在");

        List<RoleProjectRelevancePO> authRoleProjectRelevanceList = roleProjectRelevanceMapper.selectList(Wrappers.lambdaQuery(RoleProjectRelevancePO.class).eq(RoleProjectRelevancePO::getRoleId, rolePermissionInstallParamPO.getRoleId()));
        if (authRoleProjectRelevanceList != null
                && authRoleProjectRelevanceList.size() != 0
                && !authRoleProjectRelevanceList.stream().map(RoleProjectRelevancePO::getRoleId).collect(Collectors.toList()).contains(rolePermissionInstallParamPO.getRoleId())
        ) {
            throw new RuntimeException("该角色没有分配该项目，请在角色里面设置所属项目");
        }

        //删除历史数据
        List<String> arpIds = this.baseMapper.selectList(new LambdaQueryWrapper<>(RolePermissionPO.class)
                .eq(RolePermissionPO::getRoleId, rolePermissionInstallParamPO.getRoleId())
                .eq(RolePermissionPO::getProjectId, rolePermissionInstallParamPO.getProjectId())
        ).stream().map(RolePermissionPO::getArpId).collect(Collectors.toList());
        if (arpIds != null && arpIds.size() != 0) this.baseMapper.deleteAllByIds(arpIds);

        switch (PermissionTypeEnum.getCode(rolePermissionInstallParamPO.getPermissionType())) {
            case CUSTOM_USER:
                if (KPStringUtil.isEmpty(rolePermissionInstallParamPO.getChoiceValue()))
                    throw new RuntimeException("自定义用户数据时用戶Id不能为空");

                List<RolePermissionPO> list = new ArrayList<>();
                rolePermissionInstallParamPO.getChoiceValue().forEach(userId -> {
                    list.add(new RolePermissionPO()
                            .setUserId(userId)
                            .setPermissionType(rolePermissionInstallParamPO.getPermissionType())
                            .setRoleId(rolePermissionInstallParamPO.getRoleId())
                            .setProjectId(rolePermissionInstallParamPO.getProjectId())
                    );
                });

                if (!KPCollectionUtil.insertBatch(this.baseMapper, list, 100))
                    throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
                break;
            case CUSTOM:
                if (KPStringUtil.isEmpty(rolePermissionInstallParamPO.getChoiceValue()))
                    throw new RuntimeException("自定义部门权限时部门Id不能为空");

                List<RolePermissionPO> rolePermissionList = new ArrayList<>();
                rolePermissionInstallParamPO.getChoiceValue().forEach(deptId -> {
                    rolePermissionList.add(new RolePermissionPO()
                            .setDeptId(deptId)
                            .setPermissionType(rolePermissionInstallParamPO.getPermissionType())
                            .setRoleId(rolePermissionInstallParamPO.getRoleId())
                            .setProjectId(rolePermissionInstallParamPO.getProjectId()));
                });

                if (!KPCollectionUtil.insertBatch(this.baseMapper, rolePermissionList, 100))
                    throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
                break;
            default:
                if (this.baseMapper.insert(new RolePermissionPO()
                        .setRoleId(rolePermissionInstallParamPO.getRoleId())
                        .setProjectId(rolePermissionInstallParamPO.getProjectId())
                        .setPermissionType(rolePermissionInstallParamPO.getPermissionType())) == 0)
                    throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
                break;
        }
    }


    /**
     * @param parameter
     * @return java.util.List<java.lang.String>
     * @Author lipeng
     * @Description 查询选中的数据权限
     * @Date 2024/5/6
     **/
    public QueryPermissionCustomerPO queryPermissionInstall(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("roleId"), "请输入角色Id");
        KPVerifyUtil.notNull(parameter.getString("projectId"), "请输入项目Id");

        List<RolePermissionPO> permissionPOList = this.baseMapper.selectList(new LambdaQueryWrapper<>(RolePermissionPO.class)
                .eq(RolePermissionPO::getRoleId, parameter.getString("roleId"))
                .eq(RolePermissionPO::getProjectId, parameter.getString("projectId")));
        if (permissionPOList.size() == 0) return null;

        QueryPermissionCustomerPO row = new QueryPermissionCustomerPO();
        row.setPermissionType(permissionPOList.get(0).getPermissionType());

        if (row.getPermissionType().equals(PermissionTypeEnum.CUSTOM_USER.code())) {
            row.setChoiceValue(permissionPOList.stream().map(RolePermissionPO::getUserId).collect(Collectors.toList()));
        } else if (row.getPermissionType().equals(PermissionTypeEnum.CUSTOM.code())) {
            List<DeptCustomerPO> deptList = KPJsonUtil.toJavaObjectList(deptMapper.selectList(Wrappers.lambdaQuery(DeptPO.class).eq(DeptPO::getStatus, YesNoEnum.YES.code())), DeptCustomerPO.class);
            // 构建树形结构
            Map<String, List<DeptCustomerPO>> map = deptList.stream().collect(Collectors.groupingBy(DeptCustomerPO::getParentId));
            List<String> choiceValue = new ArrayList<>();
            permissionPOList.stream().map(RolePermissionPO::getDeptId).collect(Collectors.toList()).forEach(deptId -> {
                if (map.get(deptId) == null)  choiceValue.add(deptId);
            });
            row.setChoiceValue(choiceValue);
        }

        return row;
    }

    public List<QueryUserCustomerPO> queryPermissionUser(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("roleId"), "请输入角色Id");
        KPVerifyUtil.notNull(parameter.getString("projectId"), "请输入项目Id");

        LambdaQueryWrapper<RolePermissionPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermissionPO::getRoleId, parameter.getString("roleId"))
                .eq(RolePermissionPO::getProjectId, parameter.getString("projectId"));
        List<String> userIds = this.baseMapper.selectList(wrapper).stream().filter(po -> KPStringUtil.isNotEmpty(po.getUserId())).map(RolePermissionPO::getUserId).collect(Collectors.toList());
        if (KPStringUtil.isEmpty(userIds)) return new ArrayList<>();


        MPJLambdaWrapper<UserPO> mpjWrapper = new MPJLambdaWrapper<UserPO>("u")
                .selectAll(UserPO.class,"u")
                .select(KPDatabaseUtil.groupConcat("dept.dept_name", false, "deptName"))
//                .select("GROUP_CONCAT( dept.dept_name SEPARATOR ', ' ) AS deptName")
                .leftJoin(UserDeptPO.class, "userDept", on -> on
                        .eq(UserDeptPO::getUserId, UserPO::getUserId)
                        .eq(UserDeptPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .leftJoin(DeptPO.class, "dept", on -> on
                        .eq(DeptPO::getDeptId, UserDeptPO::getDeptId)
                        .eq(DeptPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
//                .groupBy(UserPO::getUserId)
                .in(UserPO::getUserId, userIds);
        KPDatabaseUtil.groupFieldsBy(mpjWrapper, "u", UserPO.class);

        return userMapper.selectJoinList(QueryUserCustomerPO.class, mpjWrapper);
    }
}
