package com.kunpeng.framework.modules.post.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kunpeng.framework.constant.ReturnFinishedMessageConstant;
import com.kunpeng.framework.entity.bo.DictionaryBO;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.post.mapper.PostMapper;
import com.kunpeng.framework.modules.post.po.PostPO;
import com.kunpeng.framework.modules.post.po.param.PostEditParamPO;
import com.kunpeng.framework.modules.post.po.param.PostListParamPO;
import com.kunpeng.framework.modules.user.mapper.UserPostMapper;
import com.kunpeng.framework.modules.user.po.UserPostPO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lipeng
 * @Description 岗位信息表 服务实现类
 * @Date 2025-03-31
 **/
@Service
public class PostService extends ServiceImpl<PostMapper, PostPO> {

    @Autowired
    private UserPostMapper userPostMapper;

    /**
     * @Author lipeng
     * @Description 查询岗位信息列表
     * @Date 2025-03-31
     * @param postListParamPO
     * @return java.util.List<PostPO>
     **/
    public List<PostPO> queryPageList(PostListParamPO postListParamPO) {
        //搜索条件
        LambdaQueryWrapper<PostPO> queryWrapper = Wrappers.lambdaQuery(PostPO.class)
                .like(KPStringUtil.isNotEmpty(postListParamPO.getPostCode()), PostPO::getPostCode, postListParamPO.getPostCode())
                .like(KPStringUtil.isNotEmpty(postListParamPO.getPostName()), PostPO::getPostName, postListParamPO.getPostName())
                .eq(KPStringUtil.isNotEmpty(postListParamPO.getStatus()), PostPO::getStatus, postListParamPO.getStatus());

        //分页和排序
        PageHelper.startPage(postListParamPO.getPageNum(), postListParamPO.getPageSize(), postListParamPO.getOrderBy(PostPO.class));
        return this.baseMapper.selectList(queryWrapper);
    }


    /**
     * @Author lipeng
     * @Description 根据岗位Id查询详情
     * @Date 2025-03-31
     * @param parameter
     * @return PostPO
     **/
    public PostPO queryDetailsById(JSONObject parameter) {
        PostPO postPO = KPJsonUtil.toJavaObject(parameter, PostPO.class);
        KPVerifyUtil.notNull(postPO.getPostId(), "请输入postId");
        return this.baseMapper.selectById(postPO.getPostId());
    }


    /**
     * @Author lipeng
     * @Description 新增岗位信息
     * @Date 2025-03-31
     * @param postEditParamPO
     * @return void
     **/
    public void savePost(PostEditParamPO postEditParamPO) {
        PostPO postPO = KPJsonUtil.toJavaObjectNotEmpty(postEditParamPO, PostPO.class);


        List<PostPO> postPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(PostPO.class)
                .eq(PostPO::getPostCode, postPO.getPostCode())
                .or()
                .eq(PostPO::getPostName, postPO.getPostName()));

        if (postPOList.size() > 0) {
            postPOList.forEach(postPO1 -> {
                if (postPO1.getPostCode().equals(postPO.getPostCode()))
                    throw new KPServiceException("岗位编码已存在，请勿重复添加");
                if (postPO1.getPostName().equals(postPO.getPostName()))
                    throw new KPServiceException("岗位名称已存在，请勿重复添加");
            });
        }

        if (this.baseMapper.insert(postPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 修改岗位信息
     * @Date 2025-03-31
     * @param postEditParamPO
     * @return void
     **/
    public void updatePost(PostEditParamPO postEditParamPO) {
        PostPO postPO = KPJsonUtil.toJavaObjectNotEmpty(postEditParamPO, PostPO.class);


        List<PostPO> postPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(PostPO.class)
                .ne(PostPO::getPostId, postPO.getPostId())
                .and(e -> e.eq(PostPO::getPostCode, postPO.getPostCode())
                        .or()
                        .eq(PostPO::getPostName, postPO.getPostName())
                ));
        if (postPOList.size() > 0) {
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
     * @Author lipeng
     * @Description 批量删除岗位信息
     * @Date 2025-03-31
     * @param ids
     * @return String
     **/
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        //查询角色下是否有用户
        List<UserPostPO> userPostList = userPostMapper.selectList(Wrappers.lambdaQuery(UserPostPO.class).in(UserPostPO::getPostId, ids));
        if (userPostList.size() != 0) throw new KPServiceException(KPStringUtil.format("{0} 下存在用户, 不允许删除",  this.baseMapper.selectById(userPostList.get(0).getPostId()).getPostName()));

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
        return KPStringUtil.format("删除成功{0}条数据", row);
    }



    /**
     * @Author lipeng
     * @Description 设置岗位状态
     * @Date 2025/3/31
     * @param parameter
     * @return void
     **/
    public void doStatus(JSONObject parameter) {
        PostPO postParameter = KPJsonUtil.toJavaObject(parameter, PostPO.class);
        KPVerifyUtil.notNull(postParameter.getPostId(), "请输入岗位id");

        PostPO postPO = this.baseMapper.selectById(postParameter.getPostId());
        if (postPO == null) throw new KPServiceException("岗位不存在");

        postPO.setStatus(postPO.getStatus().equals(YesNoEnum.YES.code())? YesNoEnum.NO.code():YesNoEnum.YES.code());

        if (this.baseMapper.updateById(postPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
    }


    /**
     * @Author lipeng
     * @Description 查询岗位下拉框
     * @Date 2025/4/30
     * @param
     * @return java.util.List<com.kunpeng.framework.entity.bo.DictionaryBO>
     **/
    public List<DictionaryBO> querySelect() {
        List<PostPO> postPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(PostPO.class)
                .eq(PostPO::getStatus, YesNoEnum.YES.code()));

        List<DictionaryBO> body = new ArrayList<>();
        postPOList.forEach(postPO -> {
            body.add(new DictionaryBO().setLabel(postPO.getPostName()).setValue(postPO.getPostId()));
        });

        return body;
    }
}
