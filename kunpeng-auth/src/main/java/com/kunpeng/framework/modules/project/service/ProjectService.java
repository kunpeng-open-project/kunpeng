package com.kunpeng.framework.modules.project.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kunpeng.framework.common.cache.ProjectCache;
import com.kunpeng.framework.constant.ReturnFinishedMessageConstant;
import com.kunpeng.framework.entity.bo.DictionaryBO;
import com.kunpeng.framework.enums.DeleteFalgEnum;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.project.mapper.ProjectMapper;
import com.kunpeng.framework.modules.project.po.ProjectPO;
import com.kunpeng.framework.modules.project.po.param.ProjectEditParamPO;
import com.kunpeng.framework.modules.project.po.param.ProjectListParamPO;
import com.kunpeng.framework.modules.user.po.UserProjectPO;
import com.kunpeng.framework.modules.user.po.customer.LoginUserBO;
import com.kunpeng.framework.utils.kptool.KPAppUtil;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lipeng
 * @Description 项目表 服务实现类
 * @Date 2025-03-14 17:19:50
 **/
@Service
public class ProjectService extends ServiceImpl<ProjectMapper, ProjectPO> {

    /**
     * @Author lipeng
     * @Description 查询项目列表
     * @Date 2025-03-14 17:19:50
     * @param projectListParamPO
     * @return java.util.List<ProjectPO>
     **/
    public List<ProjectPO> queryPageList(ProjectListParamPO projectListParamPO) {
        //搜索条件
        LambdaQueryWrapper<ProjectPO> queryWrapper = Wrappers.lambdaQuery(ProjectPO.class)
                .like(KPStringUtil.isNotEmpty(projectListParamPO.getProjectName()), ProjectPO::getProjectName, projectListParamPO.getProjectName())
                .like(KPStringUtil.isNotEmpty(projectListParamPO.getProjectCode()), ProjectPO::getProjectCode, projectListParamPO.getProjectCode())
                .eq(KPStringUtil.isNotEmpty(projectListParamPO.getStatus()), ProjectPO::getStatus, projectListParamPO.getStatus())
                .eq(KPStringUtil.isNotEmpty(projectListParamPO.getManage()), ProjectPO::getManage, projectListParamPO.getManage())
                .like(KPStringUtil.isNotEmpty(projectListParamPO.getAppId()), ProjectPO::getAppId, projectListParamPO.getAppId());

        //分页和排序
        PageHelper.startPage(projectListParamPO.getPageNum(), projectListParamPO.getPageSize(), projectListParamPO.getOrderBy(ProjectPO.class));
        return this.baseMapper.selectList(queryWrapper);
    }


    /**
     * @Author lipeng
     * @Description 根据项目Id查询详情
     * @Date 2025-03-14 17:19:50
     * @param parameter
     * @return ProjectPO
     **/
    public ProjectPO queryDetailsById(JSONObject parameter) {
        ProjectPO projectPO = KPJsonUtil.toJavaObject(parameter, ProjectPO.class);
        KPVerifyUtil.notNull(projectPO.getProjectId(), "请输入projectId");
        return this.baseMapper.selectById(projectPO.getProjectId());
    }


    /**
     * @Author lipeng
     * @Description 新增项目
     * @Date 2025-03-14 17:19:50
     * @param projectEditParamPO
     * @return void
     **/
    public void saveProject(ProjectEditParamPO projectEditParamPO) {
        ProjectPO projectPO = KPJsonUtil.toJavaObjectNotEmpty(projectEditParamPO, ProjectPO.class);

        List<ProjectPO> projectPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(ProjectPO.class)
                .eq(ProjectPO::getProjectName, projectPO.getProjectName())
                .or()
                .eq(ProjectPO::getProjectCode, projectPO.getProjectCode()));

        if (projectPOList.size() > 0) {
            projectPOList.forEach(projectPO1 -> {
                if (projectPO1.getProjectName().equals(projectPO.getProjectName()))
                    throw new KPServiceException("项目名称已存在，请勿重复添加");
                if (projectPO1.getProjectCode().equals(projectPO.getProjectCode()))
                    throw new KPServiceException("项目编号已存在，请勿重复添加");
            });
        }

        projectPO.setAppId(KPAppUtil.getAppId());
        projectPO.setAppSecret(KPAppUtil.getAppSecret(60));
        projectPO.setTokenFailure(KPAppUtil.TOKEN_FAILURE);
        projectPO.setTokenGainMaxNum(KPAppUtil.TOKEN_GAIN_MAX_NUM);
        projectPO.setVoucher(new BCryptPasswordEncoder().encode(projectPO.getAppSecret()));
        if (this.baseMapper.insert(projectPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        ProjectCache.clear();
    }


    /**
     * @Author lipeng
     * @Description 修改项目
     * @Date 2025-03-14 17:19:50
     * @param projectEditParamPO
     * @return void
     **/
    public void updateProject(ProjectEditParamPO projectEditParamPO) {
        ProjectPO projectPO = KPJsonUtil.toJavaObjectNotEmpty(projectEditParamPO, ProjectPO.class);

        List<ProjectPO> projectPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(ProjectPO.class)
                .ne(ProjectPO::getProjectId, projectPO.getProjectId())
                .and(e -> e.eq(ProjectPO::getProjectName, projectPO.getProjectName())
                        .or()
                        .eq(ProjectPO::getProjectCode, projectPO.getProjectCode())
                ));

        if (projectPOList.size() > 0) {
            projectPOList.forEach(projectPO1 -> {
                if (projectPO1.getProjectName().equals(projectPO.getProjectName()))
                    throw new KPServiceException("项目名称已存在，请勿重复添加");
                if (projectPO1.getProjectCode().equals(projectPO.getProjectCode()))
                    throw new KPServiceException("项目编号已存在，请勿重复添加");
            });
        }


        if (this.baseMapper.updateById(projectPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        ProjectCache.clear();
    }


    /**
     * @Author lipeng
     * @Description 批量删除项目
     * @Date 2025-03-14 17:19:50
     * @param ids
     * @return String
     **/
    public String batchRemove(List<String> ids) {
        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");

        Integer row = this.baseMapper.deleteBatchIds(ids);
        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        ProjectCache.clear();
        return KPStringUtil.format("删除成功{0}条数据", row);
    }


    /**
     * @Author lipeng
     * @Description 设置项目状态
     * @Date 2025/3/27 16:16
     * @param parameter
     * @return void
     **/
    public void doStatus(JSONObject parameter) {
        ProjectPO projectParameter = KPJsonUtil.toJavaObject(parameter, ProjectPO.class);
        KPVerifyUtil.notNull(projectParameter.getProjectId(), "请输入项目id");

        ProjectPO projectPO = this.baseMapper.selectById(projectParameter.getProjectId());
        if (projectPO == null) throw new KPServiceException("项目不存在");

        projectPO.setStatus(projectPO.getStatus().equals(YesNoEnum.YES.code()) ? YesNoEnum.NO.code() : YesNoEnum.YES.code());

        if (this.baseMapper.updateById(projectPO) == 0)
            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);

        ProjectCache.clear();
    }


    /**
     * @Author lipeng
     * @Description 查询下拉框
     * @Date 2025/3/31 15:20
     * @param parameter
     * @return java.util.List<com.kunpeng.framework.entity.bo.DictionaryBO>
     **/
    public List<DictionaryBO> queryProjectSelect(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getInteger("manageType"), "请输入管理类型！");

        LoginUserBO loginUserBO = LoginUserBO.getLoginUser();
        List<ProjectPO> projectPOList = null;

        if (Arrays.asList("admin", "admin1").contains(loginUserBO.getUsername())) {
            projectPOList = this.baseMapper.selectList(Wrappers.lambdaQuery(ProjectPO.class)
                    .eq(ProjectPO::getStatus, YesNoEnum.YES.code())
                    .eq(!parameter.getInteger("manageType").equals(2), ProjectPO::getManage, parameter.getInteger("manageType"))
                    .orderByAsc(ProjectPO::getCreateDate));
        } else {
            MPJLambdaWrapper<ProjectPO> wrapper = new MPJLambdaWrapper<ProjectPO>()
                    .selectAll(ProjectPO.class)
                    .leftJoin(UserProjectPO.class, "userProjectPO", on->on
                            .eq(UserProjectPO::getProjectId, ProjectPO::getProjectId)
                            .eq(UserProjectPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                    ).disableSubLogicDel()
                    .eq(UserProjectPO::getUserId, loginUserBO.getUser().getUserId())
                    .eq(ProjectPO::getStatus, YesNoEnum.YES.code())
                    .eq(!parameter.getInteger("manageType").equals(2), ProjectPO::getManage, parameter.getInteger("manageType"))
                    .orderByAsc(ProjectPO::getCreateDate);
            projectPOList = this.baseMapper.selectJoinList(ProjectPO.class, wrapper);
        }


        List<DictionaryBO> body = new ArrayList<>();
        projectPOList.forEach(projectPO -> {
            body.add(new DictionaryBO()
                    .setValue(projectPO.getProjectId())
                    .setLabel(projectPO.getProjectName()));
        });

        return body;
    }
}
