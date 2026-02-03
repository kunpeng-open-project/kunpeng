package com.kp.framework.microservices.auth.util;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.constant.ServerApplicationNameConConstant;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.microservices.auth.interfaces.IDept;
import com.kp.framework.microservices.auth.interfaces.IPost;
import com.kp.framework.microservices.auth.interfaces.IUser;
import com.kp.framework.microservices.auth.po.DeptFeignPO;
import com.kp.framework.microservices.auth.po.PostFeignPO;
import com.kp.framework.microservices.auth.po.UserFeignPO;
import com.kp.framework.util.FeignVerifyUtil;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FeignAuthUtil {

    @Autowired
    private IPost post;
    @Autowired
    private IDept dept;
    @Autowired
    private IUser user;

    /**
     * 根据岗位id查询岗位信息。
     * @author lipeng
     * 2025/8/1
     * @param postId 岗位id
     * @return com.kp.framework.microservices.auth.po.PostFeignPO
     */
    public final PostFeignPO queryPostById(String postId) {
        KPResult<JSONObject> row = post.queryPostById(new KPJSONFactoryUtil().put("postId", postId).build());
        return FeignVerifyUtil.verifyBySingle(ServerApplicationNameConConstant.AUTH_NAME, row, "查询岗位信息", PostFeignPO.class);
    }

    /**
     * 根据岗位id集合查询岗位列表。
     * @author lipeng
     * 2025/9/16
     * @param postIds 岗位id集合
     * @return java.util.List<com.kp.framework.microservices.auth.po.PostFeignPO>
     */
    public final List<PostFeignPO> queryPostIdList(List<String> postIds) {
        KPResult<List<JSONObject>> row = post.queryPostIdList(postIds);
        return FeignVerifyUtil.verifyList(ServerApplicationNameConConstant.AUTH_NAME, row, "根据岗位id集合查询岗位列表", PostFeignPO.class);
    }

    /**
     * 根据部门id查询部门信息。
     * @author lipeng
     * 2025/8/1
     * @param deptId 部门id
     * @return com.kp.framework.microservices.auth.po.DeptFeignPO
     */
    public final DeptFeignPO queryDeptById(String deptId) {
        KPResult<JSONObject> row = dept.queryDeptById(new KPJSONFactoryUtil().put("deptId", deptId).build());
        return FeignVerifyUtil.verifyBySingle(ServerApplicationNameConConstant.AUTH_NAME, row, "查询部门信息", DeptFeignPO.class);
    }

    /**
     * 根据部门id集合查询部门列表。
     * @author lipeng
     * 2025/9/16
     * @param deptIds 部门id集合
     * @return java.util.List<com.kp.framework.microservices.auth.po.DeptFeignPO>
     */
    public final List<DeptFeignPO> queryDeptIdList(List<String> deptIds) {
        KPResult<List<JSONObject>> row = dept.queryDeptIdList(deptIds);
        return FeignVerifyUtil.verifyList(ServerApplicationNameConConstant.AUTH_NAME, row, "根据部门id集合查询部门列表", DeptFeignPO.class);
    }

    /**
     * 根据用户id集合查询用户列表。
     * @author lipeng
     * 2025/8/26
     * @param userIds 用户id集合
     * @return java.util.List<com.kp.framework.microservices.auth.po.UserFeignPO>
     */
    public final List<UserFeignPO> queryUserListByIds(List<String> userIds) {
        KPResult<List<JSONObject>> row = user.queryUserListByIds(userIds);
        return FeignVerifyUtil.verifyList(ServerApplicationNameConConstant.AUTH_NAME, row, "根据用户id集合查询用户列表", UserFeignPO.class);
    }
}
