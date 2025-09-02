package com.kp.framework.utils.kptool;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.configruation.mq.HttpRabbitMqConfig;
import com.kp.framework.configruation.properties.KPLogRecordProperties;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.utils.LogUtil;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author lipeng
 * @Description http请求工具类
 * @Date 2020/9/24 14:12
 * @Param
 * @return
 **/
public final class KPOkHttpHelperUtil {
    private static final Logger log = LoggerFactory.getLogger(KPOkHttpHelperUtil.class);
//    private static final MediaType XML_TYPE = MediaType.parse("application/xml;charset=utf-8");
    private static final MediaType XML_TYPE = MediaType.parse("text/xml;charset=utf-8");
    private static final MediaType JSON_TYPE = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType FORM_DATA = MediaType.parse("multipart/form-data;charset=utf-8");
    private static final MediaType APPLICATION = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    //是否打印日志
    private static Boolean pringLog = true;

    private static final OkHttpClient getInstance() {
        return OkHttpClientHolder.defaultClient;
    }
    //设置不打印日志
    public KPOkHttpHelperUtil(Boolean isPringLog) {
        pringLog = isPringLog;
    }

    private KPOkHttpHelperUtil() {}

    private static class OkHttpClientHolder {
        private static final OkHttpClient defaultClient;

        private OkHttpClientHolder() {
        }

        static {
            defaultClient = (new OkHttpClient.Builder())
                    //                    .proxy(Proxy.NO_PROXY) //来屏蔽系统代理
//                    .sslSocketFactory(getSSLSocketFactory(),  getX509TrustManager())
//                    .hostnameVerifier(getHostnameVerifier())
                    .retryOnConnectionFailure(true)
                    .connectionPool(new ConnectionPool())
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
    }




    /**
     * description 忽略https证书验证
     */
    private static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }

    /**
     * description 忽略https证书验证
     */
    private static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static X509TrustManager getX509TrustManager() {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            trustManager = (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trustManager;
    }

    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }














    public static String get(String url) throws KPServiceException {
        return getTool(url, new HashMap<>());
    }

    public static String get(String url, HashMap headerMap) throws KPServiceException {
        return getTool(url, headerMap);
    }

    public static JSONObject getByJson(String url, HashMap headerMap) throws KPServiceException {
        return KPJsonUtil.toJson(getTool(url, headerMap));
    }

    public static String get(String url, String tokenName, String token) throws KPServiceException {
        HashMap<String, String> map = new HashMap<>();
        map.put(tokenName, token);
        return getTool(url, map);
    }


    public static String postXml(String url, String xml) throws KPServiceException {
        return postTool(url, XML_TYPE, xml, new HashMap<>());
    }

    public static String postJson(String url, String json) throws KPServiceException {
        return postTool(url, JSON_TYPE, json, new HashMap<>());
    }

    public static JSONObject postJsonByJson(String url, String json) throws KPServiceException {
        return KPJsonUtil.toJson(postTool(url, JSON_TYPE, json, new HashMap<>()));
    }

    public static String postJson(String url, String json, String tokenName, String token) throws KPServiceException {
        HashMap<String, String> map = new HashMap<>();
        map.put(tokenName, token);
        return postTool(url, JSON_TYPE, json, map);
    }

    public static String postJson(String url, String json, HashMap headerMap) throws KPServiceException {
        return postTool(url, JSON_TYPE, json, headerMap);
    }

    public static JSONObject postJsonByJson(String url, String json, HashMap headerMap) throws KPServiceException {
        return KPJsonUtil.toJson(postTool(url, JSON_TYPE, json, headerMap));
    }

    public static ResponseBody postJsonAll(String url, String json, HashMap headMap) throws KPServiceException {;
        try {
            Instant start = Instant.now();
            Response response = postToolAll(url, JSON_TYPE, json, headMap);
            if (!response.isSuccessful()) {
                log.info("请求失败，HTTP状态码为：{}", response.code());
            }
            String responseStr = response.body().string();
            long disposeDate = Duration.between(start, Instant.now()).toMillis();
            if (pringLog){
                log.info("请求域名: {} ", url);
                if (headMap != null  && headMap.size() != 0)
                    log.info("请求Header: {} ", KPJsonUtil.toJsonString(headMap));
                log.info("请求参数: {} ", json.startsWith("<?xml")?"\n" + json : json);
                log.info("返回内容: {} ", responseStr.startsWith("<?xml")?"\n" + responseStr : responseStr);
                log.info("接口用时: {} ", KPLocalDateTimeUtil.formatDuration(disposeDate));
                log.info("");
            }
            notPringLog(response);
            record(url, "post", json, responseStr, disposeDate);

            ResponseBody responseBody = new ResponseBody();
            responseBody.setResponseStr(responseStr);
            responseBody.setHeaders(response.headers());
            return responseBody;
//            return ResponseBody.builder().headers(response.headers()).responseStr(responseStr).build();
        } catch (IOException var5) {
            log.info(var5.getMessage());
            throw new KPServiceException(var5.getMessage());
        }
    }

    public static ResponseBody postJsonAll(String url, String json) throws KPServiceException {
        return postJsonAll(url, json, null);
    }

    public static String postForm(String url, String request) throws KPServiceException {
        return postTool(url, APPLICATION, request, new HashMap<>());
    }

    public static String postForm(String url, String request, HashMap headerMap) throws KPServiceException {
        return postTool(url, APPLICATION, request, headerMap);
    }









    private static String getTool(String url, Map<String, String> headMap) throws KPServiceException {
        Instant start = Instant.now();

        if (headMap == null) headMap = new HashMap<>();

        try {
            //组装header
            Builder builder = new Builder();
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.addHeader(key, value);
            }

//            HttpUrl httpUrl = HttpUrl.get(url);
            Request request = builder.url(url).get().build();
            Response response = getInstance().newCall(request).execute();
            String responseStr = response.body().string();

            long disposeDate = Duration.between(start, Instant.now()).toMillis();

            if (pringLog){
                log.info("请求域名: {} ", url);
                log.info("返回内容: {} ", responseStr);
                log.info("接口用时: {} ", KPLocalDateTimeUtil.formatDuration(disposeDate));
            }
            notPringLog(response);
            record(url, "get", KPJsonUtil.toJsonString(KPUrlUtil.getQueryParams(url)), responseStr, disposeDate);
            return responseStr;
        } catch (IOException var5) {
            log.info(var5.getMessage());
            throw new KPServiceException(var5.getMessage());
        }
    }


    private static String postTool(String url, MediaType mediaType, String parameter, Map<String, String> headMap) throws KPServiceException {
        try {
            Instant start = Instant.now();
            Response response = postToolAll(url, mediaType, parameter, headMap);
            if (!response.isSuccessful()) {
                log.info("请求失败，HTTP状态码为：{}", response.code());
            }
            String responseStr = response.body().string();
            long disposeDate = Duration.between(start, Instant.now()).toMillis();
            if (pringLog){
                log.info("请求域名: {} ", url);
                if (headMap != null  && headMap.size() != 0)
                    log.info("请求Header: {} ", KPJsonUtil.toJsonString(headMap));
                log.info("请求参数: {} ", parameter.startsWith("<?xml")?"\n" + parameter : parameter);
                log.info("返回内容: {} ", responseStr.startsWith("<?xml")?"\n" + responseStr : responseStr);
                log.info("接口用时: {} ", KPLocalDateTimeUtil.formatDuration(disposeDate));
                log.info("");
            }
            notPringLog(response);
            record(url, "post", parameter, responseStr, disposeDate);
            return responseStr;
        } catch (IOException var5) {
            log.info(var5.getMessage());
            throw new KPServiceException(var5.getMessage());
        }
    }



    private static Response postToolAll(String url, MediaType mediaType, String parameter, Map<String, String> headMap) throws KPServiceException {
        try {
            if (headMap == null) headMap = new HashMap<>();

            //组装header
            Builder builder = new Builder();
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }

            //组装参数
            RequestBody requestBody = null;
            if (mediaType.toString().equals(XML_TYPE.toString())){
                requestBody = RequestBody.create(XML_TYPE, parameter);
            } else if (mediaType.toString().equals(JSON_TYPE.toString())){
                requestBody = RequestBody.create(JSON_TYPE, parameter);
            } else if (mediaType.toString().equals(APPLICATION.toString())) {
                requestBody = RequestBody.create(APPLICATION, parameter);
            }

            Request request = builder.url(url).post(requestBody).build();
            return getInstance().newCall(request).execute();
        } catch (IOException var5) {
            log.info(var5.getMessage());
            throw new KPServiceException(var5.getMessage());
        }
    }



//    @Deprecated
//    public static Response postJsonAll(String url, String json) throws KPServiceException {
//        Instant start = Instant.now();
//         RequestBody requestBody =null;
//        if (null != json) {
//            requestBody = RequestBody.create(JSON_TYPE, json);
//        }
//
//        Builder builder = new Builder();
//        try {
//            Request request = null;
//            if (null !=requestBody) {
//                request = builder
//                        .url(url)
//                        .post(requestBody).build();
//            }else {
//                request = builder.url(url).get().build();
//            }
//            Response response = getInstance().newCall(request).execute();
//
//            if (!response.isSuccessful()) {
//                log.info("请求失败，HTTP状态码为：{}", response.code());
//            };
//
//            long disposeDate = Duration.between(start, Instant.now()).toMillis();
////            if (pringLog){
////                log.info("请求域名: {}", url);
////                log.info("请求参数: {}", json);
////                log.info("返回结果: {}", response.body().string());
////                log.info("接口用时: {} ", KPLocalDateTimeUtil.formatDuration(disposeDate));
////                log.info("");
////            }
//            notPringLog(response);
//            record(url, "post", json, null, disposeDate);
//            return response;
//        } catch (IOException var5) {
//            log.info(var5.getMessage());
//            throw new KPServiceException(var5.getMessage());
//        }
//    }









    private static void notPringLog(Response response){
        pringLog = true;
        response.close();
    }


    private static void record(String url, String method, String parameters, String result, long disposeDate) {
        try {
            KPLogRecordProperties kpLogRecordProperties = KPServiceUtil.getBean(KPLogRecordProperties.class);
            if (!kpLogRecordProperties.getHttpLog()) return;

            LogUtil logUtil = KPServiceUtil.getBean(LogUtil.class);
            String body = logUtil.interfaceRecordLog(url, method, KPRequsetUtil.getRequest(), parameters, result, disposeDate);
            KPRabbitMqUtil.sendDeadMessage(HttpRabbitMqConfig.NORMAL_EXCHANGE, HttpRabbitMqConfig.NORMAL_ROUTING_KEY, body);
        }catch (Exception e){}
    }


    @Data
    public static class ResponseBody {

        private String responseStr;

        private Headers headers;
    }
}
