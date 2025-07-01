package com.kunpeng.framework.modules.user.util;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kunpeng.framework.modules.user.mapper.UserDeptMapper;
import com.kunpeng.framework.modules.user.mapper.UserPostMapper;
import com.kunpeng.framework.modules.user.mapper.UserProjectMapper;
import com.kunpeng.framework.modules.user.mapper.UserRoleMapper;
import com.kunpeng.framework.modules.user.po.UserDeptPO;
import com.kunpeng.framework.modules.user.po.UserPostPO;
import com.kunpeng.framework.modules.user.po.UserProjectPO;
import com.kunpeng.framework.modules.user.po.UserRolePO;
import com.kunpeng.framework.modules.user.po.param.UserEditParamPO;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserUtil {

    @Autowired
    private UserDeptMapper userDeptMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserPostMapper userPostMapper;

    @Autowired
    private UserProjectMapper userProjectMapper;


    /**
     * @Author lipeng
     * @Description 维护用户关联信息
     * @Date 2025/4/21
     * @param userEditParamPO
     * @param userId
     * @return void
     **/
    public void editRest(UserEditParamPO userEditParamPO, String userId) {
        /**
         * 保存部门
         **/
        List<String> audIds = userDeptMapper.selectList(Wrappers.lambdaQuery(UserDeptPO.class).eq(UserDeptPO::getUserId, userId)).stream().map(UserDeptPO::getAudId).collect(Collectors.toList());
        if (KPStringUtil.isNotEmpty(audIds)) userDeptMapper.deleteAllByIds(audIds);

        List<UserDeptPO> userDeptPOList = new ArrayList<>();
        userEditParamPO.getUserDepts().forEach(userDept -> {
            userDeptPOList.add(new UserDeptPO().setUserId(userId).setDeptId(userDept.getDeptId()).setPrincipal(userDept.getPrincipal()));
        });
        if (KPStringUtil.isNotEmpty(userDeptPOList)) userDeptMapper.insertBatchSomeColumn(userDeptPOList);


        /**
         * 保存角色
         **/
        List<String> aurIds = userRoleMapper.selectList(Wrappers.lambdaQuery(UserRolePO.class).eq(UserRolePO::getUserId, userId)).stream().map(UserRolePO::getAurId).collect(Collectors.toList());
        if (KPStringUtil.isNotEmpty(aurIds)) userRoleMapper.deleteAllByIds(aurIds);

        List<UserRolePO> userRolePOList = new ArrayList<>();
        userEditParamPO.getRoleIds().forEach(roleId -> {
            userRolePOList.add(new UserRolePO().setUserId(userId).setRoleId(roleId));
        });
        if (KPStringUtil.isNotEmpty(userRolePOList)) userRoleMapper.insertBatchSomeColumn(userRolePOList);


        /**
         * 保存岗位
         **/
        List<String> aupIds = userPostMapper.selectList(Wrappers.lambdaQuery(UserPostPO.class).eq(UserPostPO::getUserId, userId)).stream().map(UserPostPO::getAupId).collect(Collectors.toList());
        if (KPStringUtil.isNotEmpty(aupIds)) userPostMapper.deleteAllByIds(aupIds);

        List<UserPostPO> userPostList = new ArrayList<>();
        userEditParamPO.getPostIds().forEach(postId -> {
            userPostList.add(new UserPostPO().setUserId(userId).setPostId(postId));
        });
        if (KPStringUtil.isNotEmpty(userPostList)) userPostMapper.insertBatchSomeColumn(userPostList);


        /**
         * 保存项目
         **/
        if (KPStringUtil.isNotEmpty(userEditParamPO.getProjectIds())){
            //删除
            List<String> aupIdList = userProjectMapper.selectList(Wrappers.lambdaQuery(UserProjectPO.class).eq(UserProjectPO::getUserId, userId)).stream().map(UserProjectPO::getAupId).collect(Collectors.toList());
            if (KPStringUtil.isNotEmpty(aupIdList)) userProjectMapper.deleteAllByIds(aupIdList);

            List<UserProjectPO> userProjectPOList = new ArrayList<>();
            userEditParamPO.getProjectIds().forEach(projectId -> {
                userProjectPOList.add(new UserProjectPO().setUserId(userId).setProjectId(projectId));
            });
            if (KPStringUtil.isNotEmpty(userProjectPOList)) userProjectMapper.insertBatchSomeColumn(userProjectPOList);
        }
    }
}
