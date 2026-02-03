package com.kp.framework.modules.post.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.KPApiJsonParamMode;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.post.mapper.PostMapper;
import com.kp.framework.modules.post.po.PostPO;
import com.kp.framework.modules.post.po.param.PostEditParamPO;
import com.kp.framework.modules.post.po.param.PostListParamPO;
import com.kp.framework.modules.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 岗位信息表相关接口。
 * @author lipeng
 * 2025-03-31
 */
@RestController
@RequestMapping("/auth/post")
@Tag(name = "岗位信息相关接口")
@ApiSupport(author = "lipeng", order = 27)
public class PostController {

    @Autowired
    private PostService postService;


    @PreAuthorize("hasPermission('/auth/post/page/list', 'auth:post:page:list')")
    @Operation(summary = "查询岗位信息分页列表", description = "权限 auth:post:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<PostPO> queryList(@RequestBody PostListParamPO postListParamPO) {
        return postService.queryPageList(postListParamPO);
    }


    @PreAuthorize("hasPermission('/auth/post/details','auth:post:details')")
    @Operation(summary = "根据岗位Id查询详情", description = "权限 auth:post:details")
    @PostMapping("/details")
    @KPApiJsonParam({
            @KPJsonField(name = "postId", description = "岗位Id", required = true)
    })
    public KPResult<PostPO> queryDetailsById(@RequestBody JSONObject parameter) {
        return KPResult.success(postService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/post/save','auth:post:save')")
    @Operation(summary = "新增岗位信息", description = "权限 auth:post:save")
    @PostMapping("/save")
    @KPObjectChangeLogNote(parentMapper = PostMapper.class, identification = "postId,post_id", operateType = ObjectChangeLogOperateType.ADD, businessType = "岗位信息")
    @KPVerifyNote
    @KPApiJsonParamMode(component = PostEditParamPO.class, ignores = "postId")
    public KPResult<PostPO> save(@RequestBody PostEditParamPO postEditParamPO) {
        postService.savePost(postEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/post/update','auth:post:update')")
    @Operation(summary = "修改岗位信息", description = "权限 auth:post:update")
    @PostMapping("/update")
    @KPObjectChangeLogNote(parentMapper = PostMapper.class, identification = "postId,post_id", businessType = "岗位信息")
    @KPVerifyNote
    public KPResult<PostPO> update(@RequestBody PostEditParamPO postEditParamPO) {
        postService.updatePost(postEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/post/batch/remove','auth:post:batch:remove')")
    @Operation(summary = "批量删除岗位信息", description = "权限 auth:post:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonParam({
            @KPJsonField(name = "ids", description = "岗位Id", required = true, dataType = "array<string>")
    })
    @KPObjectChangeLogNote(parentMapper = PostMapper.class, identification = "postId,post_id", operateType = ObjectChangeLogOperateType.DELETE, businessType = "岗位信息")
    public KPResult<String> batchRemove(@RequestBody List<String> ids) {
        return KPResult.success(postService.batchRemove(ids));
    }


    @PreAuthorize("hasPermission('/auth/post/do/status','auth:post:do:status')")
    @Operation(summary = "设置岗位状态", description = "权限 auth:post:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonParam({
            @KPJsonField(name = "postId", description = "岗位Id", required = true),
    })
    @KPObjectChangeLogNote(parentMapper = PostMapper.class, identification = "postId,post_id", businessType = "岗位信息")
    public KPResult<Void> doStatus(@RequestBody JSONObject parameter) {
        postService.doStatus(parameter);
        return KPResult.success();
    }
}
