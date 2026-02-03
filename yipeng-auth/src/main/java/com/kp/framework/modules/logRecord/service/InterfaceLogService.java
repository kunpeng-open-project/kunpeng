package com.kp.framework.modules.logRecord.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kp.framework.common.cache.ProjectCache;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.modules.logRecord.enums.JournalStatusEnum;
import com.kp.framework.modules.logRecord.mapper.InterfaceLogHistoryMapper;
import com.kp.framework.modules.logRecord.mapper.InterfaceLogMapper;
import com.kp.framework.modules.logRecord.po.InterfaceLogHistoryPO;
import com.kp.framework.modules.logRecord.po.InterfaceLogPO;
import com.kp.framework.modules.logRecord.po.customer.InterfaceCallListCustomerPO;
import com.kp.framework.modules.logRecord.po.param.InterfaceLogListParamPO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPLocalDateTimeUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统内部接口调用记录 服务实现类。
 * @author lipeng
 * 2025-05-21
 */
@Service
public class InterfaceLogService extends ServiceImpl<InterfaceLogMapper, InterfaceLogPO> {

    @Autowired
    private InterfaceLogHistoryMapper interfaceLogHistoryMapper;

    private final String redisKeyByProject = RedisSecurityConstant.AUTHENTICATION + "interface:log:project";

    private final String redisKeyByInterfaceName = RedisSecurityConstant.AUTHENTICATION + "interface:log:interface:name:";

    /**
     * 查询系统内部接口调用记录列表。
     * @author lipeng
     * 2025-05-21
     * @param interfaceLogListParamPO 查询参数
     * @return com.kp.framework.entity.bo.KPResult<?>
     */
    public KPResult<?> queryPageList(InterfaceLogListParamPO interfaceLogListParamPO) {
        PageHelper.startPage(interfaceLogListParamPO.getPageNum(), interfaceLogListParamPO.getPageSize(), interfaceLogListParamPO.getOrderBy(InterfaceLogPO.class));

        if (interfaceLogListParamPO.getLevel().equals(JournalStatusEnum.NEWEST_JOURNAL.code())) {
            LambdaQueryWrapper<InterfaceLogPO> queryWrapper = new LambdaQueryWrapper<>(InterfaceLogPO.class)
                    .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getProjectName()), InterfaceLogPO::getProjectName, interfaceLogListParamPO.getProjectName())
                    .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getUri()), InterfaceLogPO::getUri, interfaceLogListParamPO.getUri())
                    .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getName()), InterfaceLogPO::getName, interfaceLogListParamPO.getName())
                    .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getParameters()), InterfaceLogPO::getParameters, interfaceLogListParamPO.getParameters())
                    .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getResult()), InterfaceLogPO::getResult, interfaceLogListParamPO.getResult())
                    .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getClinetIp()), InterfaceLogPO::getClinetIp, interfaceLogListParamPO.getClinetIp())
                    .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getIdentification()), InterfaceLogPO::getIdentification, interfaceLogListParamPO.getIdentification())
                    .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getIdentificationName()), InterfaceLogPO::getIdentificationName, interfaceLogListParamPO.getIdentificationName())
                    .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getPhone()), InterfaceLogPO::getPhone, interfaceLogListParamPO.getPhone())
                    .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getSerial()), InterfaceLogPO::getSerial, interfaceLogListParamPO.getSerial())
                    .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getStatus()), InterfaceLogPO::getStatus, interfaceLogListParamPO.getStatus())
                    .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getMessage()), InterfaceLogPO::getMessage, interfaceLogListParamPO.getMessage())
                    .between(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getCallTime()), InterfaceLogPO::getCallTime, KPLocalDateTimeUtil.getWeeHours(interfaceLogListParamPO.getCallTime()), KPLocalDateTimeUtil.getWitchingHour(interfaceLogListParamPO.getCallTime()));
            return KPResult.list(this.baseMapper.selectList(queryWrapper));
        }

        return KPResult.list(
                interfaceLogHistoryMapper.selectList(new LambdaQueryWrapper<>(InterfaceLogHistoryPO.class)
                        .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getProjectName()), InterfaceLogHistoryPO::getProjectName, interfaceLogListParamPO.getProjectName())
                        .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getUri()), InterfaceLogHistoryPO::getUri, interfaceLogListParamPO.getUri())
                        .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getName()), InterfaceLogHistoryPO::getName, interfaceLogListParamPO.getName())
                        .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getParameters()), InterfaceLogHistoryPO::getParameters, interfaceLogListParamPO.getParameters())
                        .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getResult()), InterfaceLogHistoryPO::getResult, interfaceLogListParamPO.getResult())
                        .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getClinetIp()), InterfaceLogHistoryPO::getClinetIp, interfaceLogListParamPO.getClinetIp())
                        .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getIdentification()), InterfaceLogHistoryPO::getIdentification, interfaceLogListParamPO.getIdentification())
                        .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getIdentificationName()), InterfaceLogHistoryPO::getIdentificationName, interfaceLogListParamPO.getIdentificationName())
                        .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getPhone()), InterfaceLogHistoryPO::getPhone, interfaceLogListParamPO.getPhone())
                        .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getSerial()), InterfaceLogHistoryPO::getSerial, interfaceLogListParamPO.getSerial())
                        .eq(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getStatus()), InterfaceLogHistoryPO::getStatus, interfaceLogListParamPO.getStatus())
                        .like(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getMessage()), InterfaceLogHistoryPO::getMessage, interfaceLogListParamPO.getMessage())
                        .between(KPStringUtil.isNotEmpty(interfaceLogListParamPO.getCallTime()), InterfaceLogHistoryPO::getCallTime, KPLocalDateTimeUtil.getWeeHours(interfaceLogListParamPO.getCallTime()), KPLocalDateTimeUtil.getWitchingHour(interfaceLogListParamPO.getCallTime())))
        );
    }

    /**
     * 查询接口所属项目。
     * @author lipeng
     * 2025/5/21
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryBO>
     */
    public List<DictionaryBO> queryProject() {
        if (KPRedisUtil.hasKey(redisKeyByProject))
            return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKeyByProject), DictionaryBO.class);

        List<DictionaryBO> body = new ArrayList<>();

        List<InterfaceLogPO> interfaceLogPOList = this.baseMapper.selectList(new LambdaQueryWrapper<>(InterfaceLogPO.class)
                .select(InterfaceLogPO::getProjectName)
                .groupBy(InterfaceLogPO::getProjectName));

        interfaceLogPOList.stream().filter(interfaceLogPO -> KPStringUtil.isNotEmpty(interfaceLogPO.getProjectName())).toList().forEach(interfaceLogPO -> {
            body.add(new DictionaryBO()
                    .setLabel(interfaceLogPO.getProjectName())
                    .setValue(interfaceLogPO.getProjectName()));
        });

        Map<String, DictionaryBO> map = body.stream().collect(Collectors.toMap(DictionaryBO::getLabel, Function.identity()));
        List<InterfaceLogHistoryPO> interfaceLogHistoryPOList = interfaceLogHistoryMapper.selectList(new LambdaQueryWrapper<>(InterfaceLogHistoryPO.class)
                .select(InterfaceLogHistoryPO::getProjectName)
                .groupBy(InterfaceLogHistoryPO::getProjectName));
        interfaceLogHistoryPOList.stream().filter(interfaceLogPO -> KPStringUtil.isNotEmpty(interfaceLogPO.getProjectName())).toList().forEach(interfaceLogPO -> {
            if (map.get(interfaceLogPO.getProjectName()) == null) {
                body.add(new DictionaryBO()
                        .setLabel(interfaceLogPO.getProjectName())
                        .setValue(interfaceLogPO.getProjectName()));
            }
        });

        if (KPStringUtil.isNotEmpty(body)) KPRedisUtil.set(redisKeyByProject, body, 2, TimeUnit.HOURS);

        //如果没有任何记录 就查询项目 避免前端报错
        if (KPStringUtil.isEmpty(body)) {
            ProjectCache.getProjectList().forEach(projectPO -> {
                body.add(new DictionaryBO()
                        .setLabel(projectPO.getProjectName())
                        .setValue(projectPO.getProjectName()));
            });
        }
        return body;
    }

    /**
     * 根据查询详情。
     * @author lipeng
     * 2025-05-21
     * @param parameter 查询参数
     * @return com.kp.framework.modules.logRecord.po.InterfaceLogPO
     */
    public InterfaceLogPO queryDetailsById(JSONObject parameter) {
        InterfaceLogPO interfaceLogPO = KPJsonUtil.toJavaObject(parameter, InterfaceLogPO.class);
        KPVerifyUtil.notNull(interfaceLogPO.getUuid(), "请输入uuid");
        InterfaceLogPO row = this.baseMapper.selectById(interfaceLogPO.getUuid());
        if (row != null) return row;

        return KPJsonUtil.toJavaObject(interfaceLogHistoryMapper.selectById(interfaceLogPO.getUuid()), InterfaceLogPO.class);
    }

    /**
     * 查询内部接口名称。
     * @author lipeng
     * 2025/5/21
     * @param parameter 查询参数
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryBO>
     */
    public List<DictionaryBO> queryInterfaceName(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("projectName"), "请输入项目名称！");

        String redisKey = redisKeyByInterfaceName + parameter.getString("projectName");
        if (KPRedisUtil.hasKey(redisKey)) return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKey), DictionaryBO.class);

        List<DictionaryBO> body = new ArrayList<>();

        List<InterfaceLogPO> interfaceLogPOList = this.baseMapper.selectList(new LambdaQueryWrapper<>(InterfaceLogPO.class)
                .select(InterfaceLogPO::getName)
                .eq(InterfaceLogPO::getProjectName, parameter.getString("projectName"))
                .groupBy(InterfaceLogPO::getName));

        interfaceLogPOList.stream().filter(interfaceLogPO -> KPStringUtil.isNotEmpty(interfaceLogPO.getName())).toList().forEach(interfaceLogPO -> {
            if (KPStringUtil.isNotEmpty(interfaceLogPO.getName())) {
                body.add(new DictionaryBO()
                        .setLabel(interfaceLogPO.getName())
                        .setValue(interfaceLogPO.getName()));
            }
        });


        Map<String, DictionaryBO> map = body.stream().collect(Collectors.toMap(DictionaryBO::getLabel, Function.identity()));
        List<InterfaceLogHistoryPO> interfaceLogHistoryPOList = interfaceLogHistoryMapper.selectList(new LambdaQueryWrapper<>(InterfaceLogHistoryPO.class)
                .select(InterfaceLogHistoryPO::getName)
                .eq(InterfaceLogHistoryPO::getProjectName, parameter.getString("projectName"))
                .groupBy(InterfaceLogHistoryPO::getName));
        interfaceLogHistoryPOList.stream().filter(interfaceLogPO -> KPStringUtil.isNotEmpty(interfaceLogPO.getProjectName())).toList().forEach(interfaceLogPO -> {
            if (map.get(interfaceLogPO.getName()) == null) {
                body.add(new DictionaryBO()
                        .setLabel(interfaceLogPO.getName())
                        .setValue(interfaceLogPO.getName()));
            }
        });

        if (KPStringUtil.isNotEmpty(body)) KPRedisUtil.set(redisKey, body, 2, TimeUnit.HOURS);
        return body;
    }

    /**
     * 清空接口缓存。
     * @author lipeng
     * 2026/1/16 2025/5/21
     */
    public void clearCache() {
        KPRedisUtil.remove(redisKeyByProject);
        KPRedisUtil.removeBacth(redisKeyByInterfaceName);
    }

    /**
     * 查询接口日志的项目名称。
     * @author lipeng
     * 2025/6/12
     * @return java.util.List<com.kp.framework.entity.bo.DictionaryBO>
     */
    public List<DictionaryBO> queryProjectName() {
        if (KPRedisUtil.hasKey(redisKeyByProject))
            return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKeyByProject), DictionaryBO.class);

        LambdaQueryWrapper<InterfaceLogPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(InterfaceLogPO::getProjectName)
                .groupBy(InterfaceLogPO::getProjectName);

        List<DictionaryBO> row = new ArrayList<>();
        this.baseMapper.selectList(queryWrapper).stream().filter(interfaceLogPO -> KPStringUtil.isNotEmpty(interfaceLogPO.getProjectName())).toList().forEach(interfaceLogPO -> {
            row.add(new DictionaryBO()
                    .setLabel(interfaceLogPO.getProjectName())
                    .setValue(interfaceLogPO.getProjectName()));
        });

        if (KPStringUtil.isNotEmpty(row)) KPRedisUtil.set(redisKeyByProject, KPJsonUtil.toJsonString(row), 2, TimeUnit.HOURS);
        return row;
    }

    /**
     * 查询接口调用次数列表。
     * @author lipeng
     * 2025/6/12
     * @param parameter 查询参数
     * @return java.util.List<com.kp.framework.modules.logRecord.po.customer.InterfaceCallListCustomerPO>
     */
    public List<InterfaceCallListCustomerPO> queryInterfaceCallList(JSONObject parameter) {
        String redisKey = RedisSecurityConstant.AUTHENTICATION + "interface:interfaceCallList:" + parameter.toJSONString();
        if (KPRedisUtil.hasKey(redisKey))
            return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKey), InterfaceCallListCustomerPO.class);

        QueryWrapper<InterfaceLogPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("uri, count( 1 ) num, ANY_VALUE(project_name) AS projectName, ANY_VALUE(name) AS name, ANY_VALUE(method) AS method")
                .groupBy("uri")
                .in(KPStringUtil.isNotEmpty(parameter.getString("projectName")), "project_name", parameter.getString("projectName"))
                .orderByDesc("num");
//                .last("LIMIT 100");

        List<Map<String, Object>> list = this.baseMapper.selectMaps(queryWrapper);
        List<InterfaceCallListCustomerPO> result = KPJsonUtil.toJavaObjectList(list, InterfaceCallListCustomerPO.class);
        KPRedisUtil.set(redisKey, KPJsonUtil.toJsonString(result), 2, TimeUnit.HOURS);
        return result;
    }
}
