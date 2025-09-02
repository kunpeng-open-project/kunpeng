package com.kp.framework.annotation.impl;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.annotation.KPProhibitCrawlerNote;
import com.kp.framework.entity.internal.ResultCode;
import com.kp.framework.utils.kptool.KPIPUtil;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPRequsetUtil;
import com.kp.framework.utils.kptool.KPResponseUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Author lipeng
 * @Description
 * @Date 2023/12/20 9:14
 * @return
 **/
@Component
public class ProhibitCrawlerBuilder {

    private String redis_key = "request:crawler:";

    private List<String> whitelist = Arrays.asList("119.3.223.72");

    public Boolean dispose(KPProhibitCrawlerNote prohibitCrawler)  {
        HttpServletRequest request = KPRequsetUtil.getRequest();
        //放行白名单
        if (whitelist.contains(KPIPUtil.getClinetIP())) return true;

        String redisKey = redis_key + request.getRequestURI() + ":" + KPIPUtil.getClinetIP();
        String redisKey_forbid = redisKey + ":forbid"; //是否禁用
        String redisKey_forbidNumber = redisKey + ":forbidNumber"; //禁黑名单 统计次数
        String redisKey_blacklist = redis_key + request.getRequestURI() + ":blacklist"; //黑名单

        Integer number = KPRedisUtil.getInteger(redisKey); //访问次数
        String forbid = KPRedisUtil.getString(redisKey_forbid); // 是否禁用
        List<Object> blacklist = KPRedisUtil.getList(redisKey_blacklist); // 黑名单

        if (blacklist != null && blacklist.size()>0 && blacklist.contains(KPIPUtil.getClinetIP())){
            JSONObject error = new KPJSONFactoryUtil()
                    .put("code", ResultCode.PERPETUAL_FORBID_VISIT.code())
                    .put("message", ResultCode.PERPETUAL_FORBID_VISIT.message())
                    .put("data", "")
                    .build();
            KPResponseUtil.writeJson(error);
            return false;
        }

        //禁止访问
        if (KPStringUtil.isNotEmpty(forbid)){
            JSONObject error = new KPJSONFactoryUtil()
                    .put("code", ResultCode.TEMPORARY_FORBID_VISIT.code())
                    .put("message", ResultCode.TEMPORARY_FORBID_VISIT.message())
                    .put("data", "")
                    .build();
            KPResponseUtil.writeJson(error);
            return false;
        }

        if (KPStringUtil.isEmpty(number)){
            KPRedisUtil.set(redisKey, 1, prohibitCrawler.minute() * 60);
            return true;
        }


        KPRedisUtil.set(redisKey, number + 1, KPRedisUtil.ttl(redisKey));

        if (number >= prohibitCrawler.minuteCount()){
            //设置禁用
            KPRedisUtil.set(redisKey_forbid, KPStringUtil.format("禁止访问{0}小时", prohibitCrawler.forbidHouse()), prohibitCrawler.forbidHouse(), TimeUnit.HOURS);

            //判断是否加入黑名单
            Integer forbidNumber = KPRedisUtil.getInteger(redisKey_forbidNumber);
            if (KPStringUtil.isEmpty(forbidNumber)){
                KPRedisUtil.set(redisKey_forbidNumber, 1, 30, TimeUnit.DAYS);
            }else{
                Integer forbidCount = forbidNumber + 1;
                KPRedisUtil.set(redisKey_forbidNumber, forbidCount, 30, TimeUnit.DAYS);
                if (forbidCount >= prohibitCrawler.blacklist()){
                    if (!blacklist.contains(KPIPUtil.getClinetIP())){
                        KPRedisUtil.setListByLeftPush(redisKey_blacklist, KPIPUtil.getClinetIP());
                    }
                }
            }

        }
        return true;
    }
}
