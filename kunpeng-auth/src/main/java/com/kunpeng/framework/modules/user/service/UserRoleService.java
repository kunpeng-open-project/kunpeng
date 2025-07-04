package com.kunpeng.framework.modules.user.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.role.mapper.RoleMapper;
import com.kunpeng.framework.modules.role.po.RolePO;
import com.kunpeng.framework.modules.user.mapper.UserRoleMapper;
import com.kunpeng.framework.modules.user.po.UserRolePO;
import com.kunpeng.framework.utils.kptool.KPCollectionUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author lipeng
 * @Description 用户角色
 * @Date 2025/5/15
 * @return
 **/
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRolePO> {

    @Autowired
    private RoleMapper roleMapper;


    /**
     * @Author lipeng
     * @Description 设置用户
     * @Date 2025/5/15
     * @param parameter
     * @return void
     **/
    public void userRoleService(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("roleId"), "请输入角色id！");
        KPVerifyUtil.notNull(parameter.getJSONArray("userIds"), "请选择用户！");

        RolePO rolePO = roleMapper.selectById(parameter.getString("roleId"));
        if (rolePO == null) throw new KPServiceException("角色不存在");

        List<UserRolePO> userRoleList = new ArrayList<>();

        //删除角色
        List<String> aurIds = this.baseMapper.selectList(Wrappers.lambdaQuery(UserRolePO.class)
                        .eq(UserRolePO::getRoleId, rolePO.getRoleId()))
                .stream().map(UserRolePO::getAurId).collect(Collectors.toList());

        if (KPStringUtil.isNotEmpty(aurIds)) this.baseMapper.deleteAllByIds(aurIds);

        parameter.getJSONArray("userIds").forEach(userId -> {
            userRoleList.add(new UserRolePO()
                    .setUserId(userId.toString())
                    .setRoleId(rolePO.getRoleId()));
        });

        KPCollectionUtil.insertBatch(this.baseMapper, userRoleList, 100);
    }
}
