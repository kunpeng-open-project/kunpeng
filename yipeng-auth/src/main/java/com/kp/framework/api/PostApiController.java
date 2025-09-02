package com.kp.framework.api;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonlParam;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.post.po.PostPO;
import com.kp.framework.modules.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@Api(tags = "API-岗位相关接口", value = "API-岗位相关接口")
@ApiSupport(order = 0)
public class PostApiController {

    @Autowired
    private PostService postService;


    @ApiOperation(value = "根据岗位Id查询岗位信息")
    @PostMapping("/query/post/id")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "postId", value = "岗位Id", required = true)
    })
    public KPResult<PostPO> queryPostId(@RequestBody JSONObject parameter) {
        return KPResult.success(postService.queryDetailsById(parameter));
    }


    @ApiOperation(value = "根据岗位id集合查询岗位列表")
    @PostMapping("/post/ids/list")
    @KPApiJsonlParam({
            @ApiModelProperty(name = "postId", value = "岗位Id集合", required = true, dataType = "list")
    })
    public KPResult<PostPO> queryUserIdList(@RequestBody List<String> postIds) {
        return KPResult.list(postService.queryPostIdList(postIds));
    }
}
