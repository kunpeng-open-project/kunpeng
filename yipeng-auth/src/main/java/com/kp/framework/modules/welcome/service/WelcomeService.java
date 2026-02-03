package com.kp.framework.modules.welcome.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kp.framework.common.cache.ProjectCache;
import com.kp.framework.common.enums.LoginUserTypeEnum;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.logRecord.mapper.InterfaceLogMapper;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.modules.user.mapper.LoginRecordMapper;
import com.kp.framework.modules.user.po.LoginRecordPO;
import com.kp.framework.modules.welcome.po.customer.InterfaceCallStatisticsCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginNumberCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginRecordCustomerPO;
import com.kp.framework.modules.welcome.po.customer.LoginRecordStatisticsCustomerPO;
import com.kp.framework.utils.kptool.KPDatabaseUtil;
import com.kp.framework.utils.kptool.KPDateUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPLocalDateTimeUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户信息demo服务实现类。
 * @author lipeng
 * 2024-08-01
 */
@Service
public class WelcomeService {

    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    /**
     * 查询首页用户登录数。
     * @author lipeng
     * 2024/8/6
     * @param projectCodes 项目编码
     * @return java.util.List<com.kp.framework.modules.welcome.po.customer.LoginNumberCustomerPO>
     */
    @Cacheable(value = "welcomeCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<LoginNumberCustomerPO> queryLoginNumber(List<String> projectCodes) {
        KPVerifyUtil.notNull(projectCodes, "请输入项目编码");
        List<ProjectPO> projectPOList = new ArrayList<>();
        projectCodes.forEach(projectCode -> {
            ProjectPO projectPO = ProjectCache.getProjectByCode(projectCode);
            if (KPStringUtil.isNotEmpty(projectPO))
                projectPOList.add(projectPO);
        });


        LambdaQueryWrapper<LoginRecordPO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(LoginRecordPO::getProjectId, projectPOList.stream().map(ProjectPO::getProjectId).collect(Collectors.toList()))
                .between(LoginRecordPO::getCreateDate, KPLocalDateTimeUtil.getWeeHours(), KPLocalDateTimeUtil.getWitchingHour())
                .groupBy(LoginRecordPO::getUserName, LoginRecordPO::getProjectId)
                .select(LoginRecordPO::getUserName, LoginRecordPO::getProjectId);
        Map<String, List<LoginRecordPO>> map = loginRecordMapper.selectList(queryWrapper).stream().collect(Collectors.groupingBy(LoginRecordPO::getProjectId));
        List<LoginNumberCustomerPO> loginNumberCustomerPOS = new ArrayList<>();
        projectPOList.forEach(projectPO -> {
            List<LoginRecordPO> loginRecordPOS = map.get(projectPO.getProjectId());
            loginNumberCustomerPOS.add(new LoginNumberCustomerPO()
                    .setProjectName(projectPO.getProjectName())
                    .setTodayLoginNumber(loginRecordPOS == null ? 0 : loginRecordPOS.size())
                    .setActiveLoginNumber(KPRedisUtil.keys(RedisSecurityConstant.REDIS_AUTHENTICATION_TOKEN + projectPO.getProjectCode()).size()));
            projectCodes.remove(projectPO.getProjectCode());
        });

        projectCodes.forEach(projectCode -> {
            loginNumberCustomerPOS.add(new LoginNumberCustomerPO()
                    .setProjectName(projectCode + "[待上线]")
                    .setTodayLoginNumber(0)
                    .setActiveLoginNumber(0));
        });
        return loginNumberCustomerPOS;
    }

    /**
     * 查询首页登录记录。
     * @author lipeng
     * 2024/8/6
     * @param parameter 查询参数
     * @return java.util.List<com.kp.framework.modules.welcome.po.customer.LoginRecordCustomerPO>
     */
    @Cacheable(value = "welcomeCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<LoginRecordCustomerPO> queryLoginRecord(JSONObject parameter) {
        ProjectPO projectPO = KPJsonUtil.toJavaObject(parameter, ProjectPO.class);
        LambdaQueryWrapper<LoginRecordPO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(LoginRecordPO::getCreateDate)
                .last("LIMIT 30");
        if (KPStringUtil.isNotEmpty(projectPO.getProjectCode()))
            queryWrapper.eq(LoginRecordPO::getProjectId, ProjectCache.getProjectByCode(projectPO.getProjectCode()).getProjectId());
        List<LoginRecordCustomerPO> loginRecordCustomerPOS = new ArrayList<>();

        List<LoginRecordPO> loginRecordPOList = loginRecordMapper.selectList(queryWrapper);

        List<ProjectPO> projectPOList = ProjectCache.getProjectsByProjectIds(loginRecordPOList.stream().map(LoginRecordPO::getProjectId).toList());
        if (KPStringUtil.isEmpty(projectPOList)) throw new KPServiceException("未查询到有效项目！");
        Map<String, ProjectPO> map = projectPOList.stream().collect(Collectors.toMap(ProjectPO::getProjectId, e -> e));

        loginRecordPOList.forEach(loginRecordPO -> {
            ProjectPO project = map.get(loginRecordPO.getProjectId());
            String projectName = project.getProjectName() == null ? " " : project.getProjectName();
            LoginRecordCustomerPO loginRecordCustomerPO = new LoginRecordCustomerPO();
            loginRecordCustomerPO.setLoginDate(loginRecordPO.getCreateDate());
            if (loginRecordPO.getLoginType().equals(LoginUserTypeEnum.COMMON.code()))
                loginRecordCustomerPO.setBody(loginRecordPO.getUserName() + " 登录" + projectName + "成功");
            if (loginRecordPO.getLoginType().equals(LoginUserTypeEnum.AUTHORIZATION.code()))
                loginRecordCustomerPO.setBody(projectName + " 授权登录成功");
            if (loginRecordPO.getLoginType().equals(LoginUserTypeEnum.NOT_PASWORD.code()))
                loginRecordCustomerPO.setBody(loginRecordPO.getUserName() + " 免密登录" + projectName + "成功");
            if (loginRecordPO.getLoginType().equals(LoginUserTypeEnum.SSO_LOGIN.code()))
                loginRecordCustomerPO.setBody(loginRecordPO.getUserName() + " 单点登录" + projectName + "成功");
            loginRecordCustomerPOS.add(loginRecordCustomerPO);
        });

        return loginRecordCustomerPOS;
    }

    /**
     * 查询首页用户登录次数统计。
     * @author lipeng
     * 2024/8/6
     * @param parameter 查询参数
     * @return java.util.List<com.kp.framework.modules.welcome.po.customer.LoginRecordStatisticsCustomerPO>
     */
    @Cacheable(value = "welcomeCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<LoginRecordStatisticsCustomerPO> queryLoginRecordStatistics(JSONObject parameter) {
        ProjectPO projectPO = KPJsonUtil.toJavaObject(parameter, ProjectPO.class);

        String redisKey = RedisSecurityConstant.AUTHENTICATION + "welcome:loginRecordStatistics:" + parameter.toJSONString();
        if (KPRedisUtil.hasKey(redisKey))
            return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKey), LoginRecordStatisticsCustomerPO.class);


        QueryWrapper<LoginRecordPO> queryWrapper = new QueryWrapper<>();
        if (KPStringUtil.isNotEmpty(projectPO.getProjectCode()))
            queryWrapper.eq("project_id", ProjectCache.getProjectByCode(projectPO.getProjectCode()).getProjectId());
        queryWrapper.select("DATE(create_date) as createDate, count(create_date) as number")
                .groupBy("createDate")
                .orderByDesc("createDate")
                .last("LIMIT 30");

        List<LoginRecordStatisticsCustomerPO> row = new ArrayList<>();
        List<Map<String, Object>> maps = loginRecordMapper.selectMaps(queryWrapper);
        Collections.reverse(maps);
        maps.forEach(map -> {
            LoginRecordStatisticsCustomerPO loginRecordStatisticsCustomerPO = new LoginRecordStatisticsCustomerPO();

            loginRecordStatisticsCustomerPO.setCreateDate(KPDateUtil.format(map.get(KPDatabaseUtil.getDatabaseId().equals("postgresql") ? "createdate" : "createDate").toString(), KPDateUtil.DATE_PATTERN, "MM-dd"));
            loginRecordStatisticsCustomerPO.setNumber(Integer.valueOf(map.get("number").toString()));
            row.add(loginRecordStatisticsCustomerPO);
        });

        KPRedisUtil.set(redisKey, KPJsonUtil.toJsonString(row), 2, TimeUnit.HOURS);
        return row;
    }

    /**
     * 查询首页接口调用次数统计。
     * @author lipeng
     * 2024/8/7
     * @param parameter 查询参数
     * @return java.util.List<com.kp.framework.modules.welcome.po.customer.InterfaceCallStatisticsCustomerPO>
     */
    @Cacheable(value = "welcomeCache", keyGenerator = "pageKeyGenerator", unless = "T(com.kp.framework.utils.kptool.KPStringUtil).isEmpty(#result)")
    public List<InterfaceCallStatisticsCustomerPO> queryInterfaceCallStatistics(JSONObject parameter) {
        InterfaceLogPO interfaceLogPO = KPJsonUtil.toJavaObject(parameter, InterfaceLogPO.class);

        String redisKey = RedisSecurityConstant.AUTHENTICATION + "welcome:interfaceCallStatistics:" + parameter.toJSONString();
        if (KPRedisUtil.hasKey(redisKey))
            return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKey), InterfaceCallStatisticsCustomerPO.class);

        int day = 20;
        QueryWrapper<InterfaceLogPO> queryWrapper = new QueryWrapper<>();
        if (KPDatabaseUtil.getDatabaseId().equals("postgresql")) {
            queryWrapper.select("DATE(call_time) AS \"callTime\", project_name as \"projectName\", count(1) AS \"number\"")
                    // 1. 项目名称筛选（不变）
                    .eq(KPStringUtil.isNotEmpty(interfaceLogPO.getProjectName()), "project_name", interfaceLogPO.getProjectName())
                    // 2. 核心修复：用apply写SQL片段，强制参数转date类型
                    .apply("DATE(call_time) >= {0}::date", KPDateUtil.format(KPDateUtil.addDays(new Date(), -day, false), KPDateUtil.DATE_PATTERN))
                    // 3. 分组：放弃用别名，直接用原字段/表达式（彻底避免大小写坑）
                    .groupBy("project_name, DATE(call_time)")
                    // 4. 排序：用原表达式，不用别名
                    .orderByDesc("DATE(call_time)");
        }
        if (KPDatabaseUtil.getDatabaseId().equals("mysql")) {
            queryWrapper.select("DATE( call_time ) AS callTime, project_name as projectName, count( 1 ) AS number ")
                    .eq(KPStringUtil.isNotEmpty(interfaceLogPO.getProjectName()), "project_name", interfaceLogPO.getProjectName())
                    .ge("DATE( call_time )", KPDateUtil.format(KPDateUtil.addDays(new Date(), -day, false), KPDateUtil.DATE_PATTERN))
                    .groupBy("projectName, callTime")
                    .orderByDesc("callTime");
        }


        List<Map<String, Object>> maps = interfaceLogMapper.selectMaps(queryWrapper);
//        Collections.reverse(maps);

        Map<String, List<Map<String, Object>>> groupedByProjectName = maps.stream().collect(Collectors.groupingBy(map -> map.get("projectName").toString(), Collectors.toList()));

        List<InterfaceCallStatisticsCustomerPO> result = new ArrayList<>();
        for (String projectName : groupedByProjectName.keySet()) {
            Map<String, Map<String, Object>> body = groupedByProjectName.get(projectName).stream().collect(Collectors.toMap(map -> map.get("callTime").toString(), e -> e));

            InterfaceCallStatisticsCustomerPO po = new InterfaceCallStatisticsCustomerPO();
            po.setProjectName(projectName);
            for (int i = day; i >= 0; i--) {
                String date = KPDateUtil.format(KPDateUtil.addDays(new Date(), -i, false), KPDateUtil.DATE_PATTERN);
                if (po.getCallTime() == null)
                    po.setCallTime(new ArrayList<>());
                if (po.getNumber() == null)
                    po.setNumber(new ArrayList<>());
                po.getCallTime().add(date);
                Integer number = body.get(date) == null ? 0 : Integer.valueOf(body.get(date).get("number").toString());
                po.getNumber().add(number);
            }
            result.add(po);
        }

        KPRedisUtil.set(redisKey, KPJsonUtil.toJsonString(result), 2, TimeUnit.HOURS);
        return result;
    }

}
