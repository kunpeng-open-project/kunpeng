package com.kunpeng.framework.modules.user.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kunpeng.framework.common.cache.ProjectCache;
import com.kunpeng.framework.common.enums.LoginUserTypeEnum;
import com.kunpeng.framework.entity.bo.PageBO;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.project.po.ProjectPO;
import com.kunpeng.framework.modules.user.mapper.LoginRecordMapper;
import com.kunpeng.framework.modules.user.po.LoginRecordPO;
import com.kunpeng.framework.modules.user.po.customer.LoginUserBO;
import com.kunpeng.framework.modules.user.po.param.LoginRecordListParamPO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPLocalDateTimeUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lipeng
 * @Description 用户登录记录表 服务实现类
 * @Date 2025-06-10
 **/
@Service
public class LoginRecordService extends ServiceImpl<LoginRecordMapper, LoginRecordPO> {


    /**
     * @Author lipeng
     * @Description 查询用户登录记录列表
     * @Date 2025-06-10
     * @param loginRecordListParamPO
     * @return java.util.List<LoginRecordPO>
     **/
    public List<LoginRecordPO> queryPageList(LoginRecordListParamPO loginRecordListParamPO) {
        QueryWrapper<LoginRecordPO> queryWrapper = new QueryWrapper<>(LoginRecordPO.class)
                .eq(KPStringUtil.isNotEmpty(loginRecordListParamPO.getUserName()), "user_name", loginRecordListParamPO.getUserName())
                .eq(KPStringUtil.isNotEmpty(loginRecordListParamPO.getLoginType()), "login_type", loginRecordListParamPO.getLoginType())
                .eq(KPStringUtil.isNotEmpty(loginRecordListParamPO.getProjectId()), "project_id", loginRecordListParamPO.getProjectId())
                .like(KPStringUtil.isNotEmpty(loginRecordListParamPO.getLoginIp()), "project_id", loginRecordListParamPO.getLoginIp())
                .between(KPStringUtil.isNotEmpty(loginRecordListParamPO.getLoginDate()), "create_date", KPLocalDateTimeUtil.getFirstDateTimeOfDay(loginRecordListParamPO.getLoginDate()), KPLocalDateTimeUtil.getLastDateTimeOfDay(loginRecordListParamPO.getLoginDate()))
                .select(loginRecordListParamPO.getOptions().contains("distinct"), "SUBSTRING_INDEX(GROUP_CONCAT(alr_id ORDER BY create_date DESC),',',1) AS alr_id,user_name, max(create_date) as createDate")
                .groupBy(loginRecordListParamPO.getOptions().contains("distinct"), "user_name");

        List<LoginRecordPO> list = null;
        if (loginRecordListParamPO.getOptions().contains("distinct")){
            PageHelper.startPage(loginRecordListParamPO.getPageNum(), loginRecordListParamPO.getPageSize());
            list = this.baseMapper.selectList(queryWrapper);
            if (list.size() == 0) return list;
            list = this.baseMapper.selectList(Wrappers.lambdaQuery(LoginRecordPO.class).in(LoginRecordPO::getAlrId, list.stream().map(LoginRecordPO::getAlrId).collect(Collectors.toList())));
        }else{
            PageHelper.startPage(loginRecordListParamPO.getPageNum(), loginRecordListParamPO.getPageSize(), loginRecordListParamPO.getOrderBy(LoginRecordPO.class));
            list = this.baseMapper.selectList(queryWrapper);

        }

        if (list.size() == 0) return list;
        list.stream().forEach(loginRecordPO -> {
            if (KPStringUtil.isNotEmpty(loginRecordPO.getLoginType()) && loginRecordPO.getLoginType().equals(LoginUserTypeEnum.AUTHORIZATION.code())) {
                loginRecordPO.setUserName(ProjectCache.getProjectByAppId(loginRecordPO.getUserName()).getProjectName());
            }
            loginRecordPO.setProjectId(ProjectCache.getProjectByProjectId(loginRecordPO.getProjectId()).getProjectName());
        });
        return list;
    }


    /**
     * @Author lipeng
     * @Description 根据登录记录id查询详情
     * @Date 2025-06-10
     * @param parameter
     * @return LoginRecordPO
     **/
    public LoginRecordPO queryDetailsById(JSONObject parameter) {
        LoginRecordPO loginRecordPO = KPJsonUtil.toJavaObject(parameter, LoginRecordPO.class);
        KPVerifyUtil.notNull(loginRecordPO.getAlrId(), "请输入alrId");
        return this.baseMapper.selectById(loginRecordPO.getAlrId());
    }


    /**
     * @Author lipeng
     * @Description 查询本人登录记录列表
     * @Date 2025/7/7
     * @param parameter
     * @return java.util.List<com.kunpeng.framework.modules.user.po.LoginRecordPO>
     **/
    public List<LoginRecordPO> queryOneselfList(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("projectCode"), "请输入项目编号");
        KPVerifyUtil.notNull(parameter.getString("pageNum"), "请输入当前页");
        KPVerifyUtil.notNull(parameter.getString("pageSize"), "请输入每页条数");
        KPVerifyUtil.maxLength(parameter.getInteger("pageSize"), 100, "每页条数不能超过100");

        ProjectPO projectPO = ProjectCache.getProjectByCode(parameter.getString("projectCode"));
        if (projectPO == null) throw new KPServiceException("该项目不存在！");
        if (projectPO.getStatus().equals(YesNoEnum.NO.code())) throw new KPServiceException("该项目已停用！");

        LambdaQueryWrapper<LoginRecordPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoginRecordPO::getUserName, LoginUserBO.getLoginUser().getUser().getUserName())
                .eq(LoginRecordPO::getProjectId, projectPO.getProjectId());

        PageHelper.startPage(parameter.getInteger("pageNum"), parameter.getInteger("pageSize"), new PageBO().getOrderBy(parameter.getString("orderBy"), LoginRecordPO.class));
        return this.baseMapper.selectList(queryWrapper);
    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 新增用户登录记录
//     * @Date 2025-06-10
//     * @param loginRecordEditParamPO
//     * @return void
//     **/
//    public void saveLoginRecord(LoginRecordEditParamPO loginRecordEditParamPO) {
//        LoginRecordPO loginRecordPO = KPJsonUtil.toJavaObjectNotEmpty(loginRecordEditParamPO, LoginRecordPO.class);
//
//        if (this.baseMapper.insert(loginRecordPO) == 0)
//            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 修改用户登录记录
//     * @Date 2025-06-10
//     * @param loginRecordEditParamPO
//     * @return void
//     **/
//    public void updateLoginRecord(LoginRecordEditParamPO loginRecordEditParamPO) {
//        LoginRecordPO loginRecordPO = KPJsonUtil.toJavaObjectNotEmpty(loginRecordEditParamPO, LoginRecordPO.class);
//
//        if (this.baseMapper.updateById(loginRecordPO) == 0)
//            throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 批量删除用户登录记录
//     * @Date 2025-06-10
//     * @param ids
//     * @return String
//     **/
//    public String batchRemove(List<String> ids) {
//        if (KPStringUtil.isEmpty(ids)) throw new KPServiceException("请选择要删除的内容！");
//
//        Integer row = this.baseMapper.deleteBatchIds(ids);
//        if (row == 0) throw new KPServiceException(ReturnFinishedMessageConstant.ERROR);
//
//        return KPStringUtil.format("删除成功{0}条数据", row);
//    }
}
