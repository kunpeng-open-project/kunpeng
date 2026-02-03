package com.kp.framework.modules.user.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kp.framework.common.cache.ProjectCache;
import com.kp.framework.common.enums.LoginUserTypeEnum;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.entity.bo.PageBO;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.modules.user.mapper.LoginRecordMapper;
import com.kp.framework.modules.user.po.LoginRecordPO;
import com.kp.framework.modules.user.po.customer.LoginUserBO;
import com.kp.framework.modules.user.po.param.LoginRecordListParamPO;
import com.kp.framework.utils.kptool.KPDatabaseUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPLocalDateTimeUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户登录记录表 服务实现类。
 * @author lipeng
 * 2025-06-10
 */
@Service
public class LoginRecordService extends ServiceImpl<LoginRecordMapper, LoginRecordPO> {

    /**
     * 查询用户登录记录列表。
     * @author lipeng
     * 2025-06-10
     * @param loginRecordListParamPO 查询参数
     * @return com.kp.framework.entity.bo.KPResult<com.kp.framework.modules.user.po.LoginRecordPO>
     */
    @Cacheable(value = "userLoginRecordCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public KPResult<LoginRecordPO> queryPageList(LoginRecordListParamPO loginRecordListParamPO) {
        QueryWrapper<LoginRecordPO> queryWrapper = new QueryWrapper<>(LoginRecordPO.class)
                .eq(KPStringUtil.isNotEmpty(loginRecordListParamPO.getUserName()), "user_name", loginRecordListParamPO.getUserName())
                .eq(KPStringUtil.isNotEmpty(loginRecordListParamPO.getLoginType()), "login_type", loginRecordListParamPO.getLoginType())
                .eq(KPStringUtil.isNotEmpty(loginRecordListParamPO.getProjectId()), "project_id", loginRecordListParamPO.getProjectId())
                .like(KPStringUtil.isNotEmpty(loginRecordListParamPO.getLoginIp()), "login_ip", loginRecordListParamPO.getLoginIp())
                .between(KPStringUtil.isNotEmpty(loginRecordListParamPO.getLoginDate()), "create_date", KPLocalDateTimeUtil.getWeeHours(loginRecordListParamPO.getLoginDate()), KPLocalDateTimeUtil.getWitchingHour(loginRecordListParamPO.getLoginDate()))
                .select(KPDatabaseUtil.getDatabaseId().equals("mysql") && loginRecordListParamPO.getOptions().contains("distinct"), "SUBSTRING_INDEX(GROUP_CONCAT(alr_id ORDER BY create_date DESC),',',1) AS alr_id,user_name, max(create_date) as createDate")
                .select(KPDatabaseUtil.getDatabaseId().equals("postgresql") && loginRecordListParamPO.getOptions().contains("distinct"), "SPLIT_PART(STRING_AGG(alr_id::text, ',' ORDER BY create_date DESC), ',', 1) AS alr_id,user_name, max(create_date) as createDate")
                .groupBy(Arrays.asList("mysql", "postgresql").contains(KPDatabaseUtil.getDatabaseId()) && loginRecordListParamPO.getOptions().contains("distinct"), "user_name");

        List<LoginRecordPO> list = null;
        if (loginRecordListParamPO.getOptions().contains("distinct")) {
            PageHelper.startPage(loginRecordListParamPO.getPageNum(), loginRecordListParamPO.getPageSize());
            list = this.baseMapper.selectList(queryWrapper);
            if (KPStringUtil.isEmpty(list)) return KPResult.list(list);
            list = this.baseMapper.selectList(Wrappers.lambdaQuery(LoginRecordPO.class).in(LoginRecordPO::getAlrId, list.stream().map(LoginRecordPO::getAlrId).collect(Collectors.toList())));
        } else {
            PageHelper.startPage(loginRecordListParamPO.getPageNum(), loginRecordListParamPO.getPageSize(), loginRecordListParamPO.getOrderBy(LoginRecordPO.class));
            list = this.baseMapper.selectList(queryWrapper);

        }

        if (KPStringUtil.isEmpty(list)) return KPResult.list(list);
        list.forEach(loginRecordPO -> {
            if (KPStringUtil.isNotEmpty(loginRecordPO.getLoginType()) && loginRecordPO.getLoginType().equals(LoginUserTypeEnum.AUTHORIZATION.code())) {
                loginRecordPO.setUserName(ProjectCache.getProjectByAppId(loginRecordPO.getUserName()).getProjectName());
            }
            loginRecordPO.setProjectId(ProjectCache.getProjectByProjectId(loginRecordPO.getProjectId()).getProjectName());
        });
        return KPResult.list(list);
    }

    /**
     * 根据登录记录id查询详情。
     * @author lipeng
     * 2025-06-10
     * @param parameter 查询参数
     * @return com.kp.framework.modules.user.po.LoginRecordPO
     */
    @Cacheable(value = "userLoginRecordCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public LoginRecordPO queryDetailsById(JSONObject parameter) {
        LoginRecordPO loginRecordPO = KPJsonUtil.toJavaObject(parameter, LoginRecordPO.class);
        KPVerifyUtil.notNull(loginRecordPO.getAlrId(), "请输入alrId");
        return this.baseMapper.selectById(loginRecordPO.getAlrId());
    }

    /**
     * 查询本人登录记录列表。
     * @author lipeng
     * 2025/7/7
     * @param parameter 查询参数
     * @return com.kp.framework.entity.bo.KPResult<com.kp.framework.modules.user.po.LoginRecordPO>
     */
    public KPResult<LoginRecordPO> queryOneselfList(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("projectCode"), "请输入项目编号");
        KPVerifyUtil.notNull(parameter.getString("pageNum"), "请输入当前页");
        KPVerifyUtil.notNull(parameter.getString("pageSize"), "请输入每页条数");
        KPVerifyUtil.maxLength(parameter.getInteger("pageSize"), 100, "每页条数不能超过100");

        ProjectPO projectPO = ProjectCache.getProjectByCode(parameter.getString("projectCode"));
        if (projectPO == null) throw new KPServiceException("该项目不存在！");
        if (projectPO.getStatus().equals(YesNoEnum.NO.code())) throw new KPServiceException("该项目已停用！");

        LambdaQueryWrapper<LoginRecordPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoginRecordPO::getUserName, LoginUserBO.getLoginUserNotEmpty().getUser().getUserName())
                .eq(LoginRecordPO::getProjectId, projectPO.getProjectId());

        PageHelper.startPage(parameter.getInteger("pageNum"), parameter.getInteger("pageSize"), new PageBO().getOrderBy(parameter.getString("orderBy"), LoginRecordPO.class));
        return KPResult.list(this.baseMapper.selectList(queryWrapper));
    }
}
