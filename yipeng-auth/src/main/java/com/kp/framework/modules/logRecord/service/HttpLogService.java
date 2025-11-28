package com.kp.framework.modules.logRecord.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kp.framework.modules.logRecord.enums.JournalStatusEnum;
import com.kp.framework.modules.logRecord.mapper.HttpLogHistoryMapper;
import com.kp.framework.modules.logRecord.mapper.HttpLogMapper;
import com.kp.framework.modules.logRecord.po.HttpLogHistoryPO;
import com.kp.framework.modules.logRecord.po.HttpLogPO;
import com.kp.framework.modules.logRecord.po.param.HttpLogListParamPO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPLocalDateTimeUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lipeng
 * @Description 系统外部接口调用记录 服务实现类
 * @Date 2025-05-21
 **/
@Service
public class HttpLogService extends ServiceImpl<HttpLogMapper, HttpLogPO> {

    @Autowired
    private HttpLogHistoryMapper httpLogHistoryMapper;

    /**
     * @Author lipeng
     * @Description 查询系统外部接口调用记录列表
     * @Date 2025-05-21
     * @param httpLogListParamPO
     * @return java.util.List<HttpLogPO>
     **/
    public List<?> queryPageList(HttpLogListParamPO httpLogListParamPO) {
        PageHelper.startPage(httpLogListParamPO.getPageNum(), httpLogListParamPO.getPageSize(), httpLogListParamPO.getOrderBy(HttpLogPO.class));

        if (httpLogListParamPO.getLevel().equals(JournalStatusEnum.NEWEST_JOURNAL.code())) {
            LambdaQueryWrapper<HttpLogPO> queryWrapper = new LambdaQueryWrapper<>(HttpLogPO.class)
                    .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getProjectName()), HttpLogPO::getProjectName, httpLogListParamPO.getProjectName())
                    .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getUri()), HttpLogPO::getUri, httpLogListParamPO.getUri())
                    .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getName()), HttpLogPO::getName, httpLogListParamPO.getName())
                    .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getParameters()), HttpLogPO::getParameters, httpLogListParamPO.getParameters())
                    .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getResult()), HttpLogPO::getResult, httpLogListParamPO.getResult())
                    .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getClinetIp()), HttpLogPO::getClinetIp, httpLogListParamPO.getClinetIp())
                    .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getIdentification()), HttpLogPO::getIdentification, httpLogListParamPO.getIdentification())
                    .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getIdentificationName()), HttpLogPO::getIdentificationName, httpLogListParamPO.getIdentificationName())
                    .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getPhone()), HttpLogPO::getPhone, httpLogListParamPO.getPhone())
                    .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getSerial()), HttpLogPO::getSerial, httpLogListParamPO.getSerial())
                    .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getStatus()), HttpLogPO::getStatus, httpLogListParamPO.getStatus())
                    .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getMessage()), HttpLogPO::getMessage, httpLogListParamPO.getMessage())
                    .between(KPStringUtil.isNotEmpty(httpLogListParamPO.getCallTime()), HttpLogPO::getCallTime, KPLocalDateTimeUtil.getWeeHours(httpLogListParamPO.getCallTime()), KPLocalDateTimeUtil.getWitchingHour(httpLogListParamPO.getCallTime()));
            return this.baseMapper.selectList(queryWrapper);
        }

        return httpLogHistoryMapper.selectList(new LambdaQueryWrapper<>(HttpLogHistoryPO.class)
                .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getProjectName()), HttpLogHistoryPO::getProjectName, httpLogListParamPO.getProjectName())
                .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getUri()), HttpLogHistoryPO::getUri, httpLogListParamPO.getUri())
                .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getName()), HttpLogHistoryPO::getName, httpLogListParamPO.getName())
                .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getParameters()), HttpLogHistoryPO::getParameters, httpLogListParamPO.getParameters())
                .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getResult()), HttpLogHistoryPO::getResult, httpLogListParamPO.getResult())
                .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getClinetIp()), HttpLogHistoryPO::getClinetIp, httpLogListParamPO.getClinetIp())
                .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getIdentification()), HttpLogHistoryPO::getIdentification, httpLogListParamPO.getIdentification())
                .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getIdentificationName()), HttpLogHistoryPO::getIdentificationName, httpLogListParamPO.getIdentificationName())
                .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getPhone()), HttpLogHistoryPO::getPhone, httpLogListParamPO.getPhone())
                .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getSerial()), HttpLogHistoryPO::getSerial, httpLogListParamPO.getSerial())
                .eq(KPStringUtil.isNotEmpty(httpLogListParamPO.getStatus()), HttpLogHistoryPO::getStatus, httpLogListParamPO.getStatus())
                .like(KPStringUtil.isNotEmpty(httpLogListParamPO.getMessage()), HttpLogHistoryPO::getMessage, httpLogListParamPO.getMessage())
                .between(KPStringUtil.isNotEmpty(httpLogListParamPO.getCallTime()), HttpLogHistoryPO::getCallTime, KPLocalDateTimeUtil.getWeeHours(httpLogListParamPO.getCallTime()), KPLocalDateTimeUtil.getWitchingHour(httpLogListParamPO.getCallTime())));
    }


    /**
     * @Author lipeng
     * @Description 根据查询详情
     * @Date 2025-05-21
     * @param parameter
     * @return HttpLogPO
     **/
    public HttpLogPO queryDetailsById(JSONObject parameter) {
        HttpLogPO httpLogPO = KPJsonUtil.toJavaObject(parameter, HttpLogPO.class);
        KPVerifyUtil.notNull(httpLogPO.getUuid(), "请输入uuid");

        HttpLogPO row = this.baseMapper.selectById(httpLogPO.getUuid());
        if (row != null) return row;

        return KPJsonUtil.toJavaObject(httpLogHistoryMapper.selectById(httpLogPO.getUuid()), HttpLogPO.class);
    }
}
