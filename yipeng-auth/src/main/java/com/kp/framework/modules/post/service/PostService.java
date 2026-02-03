package com.kp.framework.modules.post.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kp.framework.constant.ReturnFinishedMessageConstant;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.post.mapper.PostMapper;
import com.kp.framework.modules.post.po.PostPO;
import com.kp.framework.modules.post.po.param.PostEditParamPO;
import com.kp.framework.modules.post.po.param.PostListParamPO;
import com.kp.framework.modules.user.mapper.UserPostMapper;
import com.kp.framework.modules.user.po.UserPostPO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位信息表 服务实现类。
 * @author lipeng
 * 2025-03-31
 */
@Service
public class PostService extends ServiceImpl<PostMapper, PostPO> {

    @Autowired
    private UserPostMapper userPostMapper;

    /**
     * 查询岗位信息列表。
     * @author lipeng
     * 2025-03-31
     * @param postListParamPO 查询参数
     * @return com.kp.framework.entity.bo.KPResult<com.kp.framework.modules.post.po.PostPO>
     */
    @Cacheable(value = "postCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public KPResult<PostPO> queryPageList(PostListParamPO postListParamPO) {
        //搜索条件
        LambdaQueryWrapper<PostPO> queryWrapper = Wrappers.lambdaQuery(PostPO.class)
                .like(KPStringUtil.isNotEmpty(postListParamPO.getPostCode()), PostPO::getPostCode, postListParamPO.getPostCode())
                .like(KPStringUtil.isNotEmpty(postListParamPO.getPostName()), PostPO::getPostName, postListParamPO.getPostName())
                .eq(KPStringUtil.isNotEmpty(postListParamPO.getStatus()), PostPO::getStatus, postListParamPO.getStatus());

        //分页和排序
        PageHelper.startPage(postListParamPO.getPageNum(), postListParamPO.getPageSize(), postListParamPO.getOrderBy(PostPO.class));
        return KPResult.list(this.baseMapper.selectList(queryWrapper));

    }

    /**
     * 根据岗位Id查询详情。
     * @author lipeng
     * 2025-03-31
     * @param parameter 查询参数
     * @return com.kp.framework.modules.post.po.PostPO
     */
    @Cacheable(value = "postCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public PostPO queryDetailsById(JSONObject parameter) {
        PostPO postPO = KPJsonUtil.toJavaObject(parameter, PostPO.class);
        KPVerifyUtil.notNull(postPO.getPostId(), "请输入postId");
        return this.baseMapper.selectById(postPO.getPostId());
    }

    /**
     * 新增岗位信息。
     * @author lipeng
     * 2025-03-31
     * @param postEditParamPO 新增参数
     */
    @CacheEvict(value = "postCache", allEntries = true)
    public void savePost(PostEditParamPO postEditParamPO) {
        PostPO postPO = KPJsonUtil.toJavaObjectNotEmpty(postEditParamPO, PostPO.class);

        List<PostPO> postPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(PostPO.class)
                .eq(PostPO::getPostCode, postPO.getPostCode())
                .or()
                .eq(PostPO::getPostName, postPO.getPostName()));

        if (KPStringUtil.isNotEmpty(postPOList)) {
            postPOList.forEach(postPO1 -> {
                if (postPO1.getPostCode().equals(postPO.getPostCode()))
                    throw new KPServiceException("岗位编码已存在，请勿重复添加");
                if (postPO1.getPostName().equals(postPO.getPostName()))
                    throw new KPServiceException("岗位名称已存在，请勿重复添加");
            });
        }

        if (this.baseMapper.insert(postPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
        postEditParamPO.setPostId(postPO.getPostId());
    }

    /**
     * 修改岗位信息。
     * @author lipeng
     * 2025-03-31
     * @param postEditParamPO 修改参数
     */
    @CacheEvict(value = "postCache", allEntries = true)
    public void updatePost(PostEditParamPO postEditParamPO) {
        PostPO postPO = KPJsonUtil.toJavaObjectNotEmpty(postEditParamPO, PostPO.class);

        List<PostPO> postPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(PostPO.class)
                .ne(PostPO::getPostId, postPO.getPostId())
                .and(e -> e.eq(PostPO::getPostCode, postPO.getPostCode())
                        .or()
                        .eq(PostPO::getPostName, postPO.getPostName())
                ));
        if (KPStringUtil.isNotEmpty(postPOList)) {
            postPOList.forEach(postPO1 -> {
                if (postPO1.getPostCode().equals(postPO.getPostCode()))
                    throw new KPServiceException("岗位编码已存在，请勿重复添加");
                if (postPO1.getPostName().equals(postPO.getPostName()))
                    throw new KPServiceException("岗位名称已存在，请勿重复添加");
            });
        }

        if (this.baseMapper.updateById(postPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }

    /**
     * 批量删除岗位信息。
     * @author lipeng
     * 2025-03-31
     * @param ids 岗位Id列表
     * @return java.lang.String
     */
    @CacheEvict(value = "postCache", allEntries = true)
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        //查询角色下是否有用户
        List<UserPostPO> userPostList = userPostMapper.selectList(Wrappers.lambdaQuery(UserPostPO.class).in(UserPostPO::getPostId, ids));
        if (KPStringUtil.isNotEmpty(userPostList)) throw new KPServiceException(KPStringUtil.format("{0} 下存在用户, 不允许删除", this.baseMapper.selectById(userPostList.get(0).getPostId()).getPostName()));

        int row = this.baseMapper.deleteByIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
        return KPStringUtil.format("删除成功{0}条数据", row);
    }

    /**
     * 设置岗位状态。
     * @author lipeng
     * 2026/1/16
     * @param parameter 修改参数
     */
    @CacheEvict(value = "postCache", allEntries = true)
    public void doStatus(JSONObject parameter) {
        PostPO postParameter = KPJsonUtil.toJavaObject(parameter, PostPO.class);
        KPVerifyUtil.notNull(postParameter.getPostId(), "请输入岗位id");

        PostPO postPO = this.baseMapper.selectById(postParameter.getPostId());
        if (postPO == null) throw new KPServiceException("岗位不存在");

        postPO.setStatus(postPO.getStatus().equals(YesNoEnum.YES.code()) ? YesNoEnum.NO.code() : YesNoEnum.YES.code());

        if (this.baseMapper.updateById(postPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }

    /**
     * 查询岗位下拉框。
     * @author lipeng
     * 2025/4/30
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryBO>
     */
    @Cacheable(value = "postCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<DictionaryBO> querySelect() {
        List<PostPO> postPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(PostPO.class)
                .eq(PostPO::getStatus, YesNoEnum.YES.code()));

        List<DictionaryBO> body = new ArrayList<>();
        postPOList.forEach(postPO -> {
            body.add(new DictionaryBO().setLabel(postPO.getPostName()).setValue(postPO.getPostId()));
        });

        return body;
    }

    /**
     * 根据岗位id集合查询岗位列表。
     * @author lipeng
     * 2025/8/28
     * @param postIds 岗位id集合
     * @return java.util.List<com.kp.framework.modules.post.po.PostPO>
     */
    @Cacheable(value = "postCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<PostPO> queryPostIdList(List<String> postIds) {
        KPVerifyUtil.notNull(postIds, "岗位id集合不能为空");

        return this.baseMapper.selectList(Wrappers.lambdaQuery(PostPO.class)
                .in(PostPO::getPostId, postIds)
                .orderByDesc(PostPO::getCreateDate));
    }
}
