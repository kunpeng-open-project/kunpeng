package com.kunpeng.framework.modules.post.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kunpeng.framework.annotation.KPApiJsonlParam;
import com.kunpeng.framework.annotation.KPApiJsonlParamMode;
import com.kunpeng.framework.annotation.verify.KPVerifyNote;
import com.kunpeng.framework.entity.bo.KPResult;
import com.kunpeng.framework.modules.post.po.PostPO;
import com.kunpeng.framework.modules.post.po.param.PostEditParamPO;
import com.kunpeng.framework.modules.post.po.param.PostListParamPO;
import com.kunpeng.framework.modules.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @Author lipeng
* @Description  岗位信息表相关接口
* @Date 2025-03-31
**/
@RestController
@RequestMapping("/auth/post")
@Api(tags = "岗位信息相关接口", value = "岗位信息相关接口")
@ApiSupport(order = 4)
public class PostController {

    @Autowired
    private PostService postService;


    @PreAuthorize("hasPermission('/auth/post/page/list', 'auth:post:page:list')")
    @ApiOperation(value = "查询岗位信息分页列表", notes = "权限 auth:post:page:list")
    @PostMapping("/page/list")
    @KPVerifyNote
    public KPResult<PostPO> queryList(@RequestBody PostListParamPO postListParamPO){
        return KPResult.list(postService.queryPageList(postListParamPO));
    }


    @PreAuthorize("hasPermission('/auth/post/details','auth:post:details')")
    @ApiOperation(value = "根据岗位Id查询详情", notes="权限 auth:post:details")
    @PostMapping("/details")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "postId", value = "岗位Id", required = true)
    })
    public KPResult<PostPO> queryDetailsById(@RequestBody JSONObject parameter){
        return KPResult.success(postService.queryDetailsById(parameter));
    }


    @PreAuthorize("hasPermission('/auth/post/save','auth:post:save')")
    @ApiOperation(value = "新增岗位信息", notes="权限 auth:post:save")
    @PostMapping("/save")
    @KPVerifyNote
    @KPApiJsonlParamMode(component = PostEditParamPO.class, ignores = "postId")
    public KPResult<PostPO> save(@RequestBody PostEditParamPO postEditParamPO){
        postService.savePost(postEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/post/update','auth:post:update')")
    @ApiOperation(value = "修改岗位信息", notes="权限 auth:post:update")
    @PostMapping("/update")
    @KPVerifyNote
    public KPResult<PostPO> update(@RequestBody PostEditParamPO postEditParamPO){
        postService.updatePost(postEditParamPO);
        return KPResult.success();
    }


    @PreAuthorize("hasPermission('/auth/post/batch/remove','auth:post:batch:remove')")
    @ApiOperation(value = "批量删除岗位信息", notes="权限 auth:post:batch:remove")
    @PostMapping("/batch/remove")
    @KPApiJsonlParam({
        @ApiModelProperty(name = "ids", value = "岗位Id", required = true, dataType = "list")
    })
    public KPResult batchRemove(@RequestBody List<String> ids){
        return KPResult.success(postService.batchRemove(ids));
    }



    @PreAuthorize("hasPermission('/auth/post/do/status','auth:post:do:status')")
    @ApiOperation(value = "设置岗位状态", notes="权限 auth:post:do:status")
    @PostMapping(value = "/do/status")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "postId", value = "岗位Id", required = true),
    })
    @ResponseBody
    public KPResult doStatus(@RequestBody JSONObject parameter) {
        postService.doStatus(parameter);
        return KPResult.success();
    }
}
