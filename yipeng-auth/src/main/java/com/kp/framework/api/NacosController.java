package com.kp.framework.api;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.entity.bo.DictionaryBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPThreadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/api/nacos")
@Tag(name = "API-nacos相关接口")
@ApiSupport(author = "lipeng", order = 10)
@Slf4j
public class NacosController {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;


    @Operation(summary = "获取Nacos注册服务下拉框")
    @PostMapping("/register/select")
    public KPResult<List<DictionaryBO>> queryNacosRegisterServiceSelect() {
        String redisKey = RedisSecurityConstant.AUTHENTICATION + "nacos:service";
        if (KPRedisUtil.hasKey(redisKey)) {
            return KPResult.success(KPJsonUtil.toJavaObjectList(KPRedisUtil.getString(redisKey), DictionaryBO.class));
        }

        List<DictionaryBO> body = new ArrayList<>();
        KPResult<List<JSONObject>> result = this.queryNacosRegisterServiceList();
        if (result.getData().isEmpty()) return KPResult.success(body);

        result.getData().forEach(json -> {
            String serviceName = json.getString("serviceName");
            if (KPStringUtil.isNotEmpty(serviceName) && !serviceName.equals("gateway"))
                body.add(new DictionaryBO().setLabel(serviceName).setValue(serviceName));
        });

        if (!body.isEmpty()) KPRedisUtil.set(redisKey, KPJsonUtil.toJsonString(body), 7200);

        return KPResult.success(body);
    }

    @Operation(summary = "获取Nacos注册服务列表")
    @PostMapping("/register/list")
    public KPResult<List<JSONObject>> queryNacosRegisterServiceList() {
        List<JSONObject> body = new ArrayList<>();

        int maxRetries = 3, retryCount = 0; // 最大重试次数 实际次数

        while (retryCount < maxRetries) {
            NamingService namingService = null;
            try {
                // 1. 获取Nacos配置
                Properties properties = nacosDiscoveryProperties.getNacosProperties();
                // 2. 初始化NamingService（每次重试重建连接）
                namingService = NacosFactory.createNamingService(properties);
                // 3. 获取所有服务名称（核心步骤，若失败会抛出异常）
                List<String> serviceNames = namingService.getServicesOfServer(1, 1000).getData();

                // 4. 处理查询结果（空结果不重试，直接返回）
                if (serviceNames == null || serviceNames.isEmpty()) {
                    return KPResult.success();
                }

                // 5. 遍历服务获取实例
                for (String serviceName : serviceNames) {
                    List<Instance> instances = namingService.getAllInstances(serviceName);
                    body.add(new KPJSONFactoryUtil()
                            .put("serviceName", serviceName) // 服务名称
                            .put("instanceCount", instances.size()) // 实例数量
                            .put("instances", instances)  // 实例详情列表（IP、端口等）
                            .build()
                    );
                }

                return KPResult.success(body);
            } catch (Exception e) {
                retryCount++;
                // 重试前短暂休眠，避免频繁重试
                if (retryCount < maxRetries) {
                    KPThreadUtil.sleep(2000);
                }
            } finally {
                // 关闭连接释放资源
                if (namingService != null) {
                    try {
                        namingService.shutDown();
                    } catch (Exception e) {
                        log.error(KPStringUtil.format("关闭NamingService时发生异常, 异常信息：{0}", e.getMessage()));
                    }
                }
            }
        }

        return KPResult.success(body);
    }

}
