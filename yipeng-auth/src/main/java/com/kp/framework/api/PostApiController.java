package com.kp.framework.api;

import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.KPApiJsonParam;
import com.kp.framework.annotation.sub.KPJsonField;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.post.po.PostPO;
import com.kp.framework.modules.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@Tag(name = "API-岗位相关接口")
@ApiSupport(author = "lipeng", order = 10)
public class PostApiController {

    @Autowired
    private PostService postService;

    @Operation(summary = "根据岗位Id查询岗位信息")
    @PostMapping("/query/id")
    @KPApiJsonParam({
            @KPJsonField(name = "postId", description = "岗位Id", required = true)
    })
    public KPResult<PostPO> queryPostId(@RequestBody JSONObject parameter) {
        return KPResult.success(postService.queryDetailsById(parameter));
    }


    @Operation(summary = "根据岗位id集合查询岗位列表")
    @PostMapping("/query/ids/list")
    @KPApiJsonParam({
            @KPJsonField(name = "postId", description = "岗位Id集合", required = true, dataType = "array<String>")
    })
    public KPResult<List<PostPO>> queryPostIdList(@RequestBody List<String> postIds) {
        return KPResult.success(postService.queryPostIdList(postIds));
    }
}
