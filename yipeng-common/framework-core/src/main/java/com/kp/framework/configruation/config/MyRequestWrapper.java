package com.kp.framework.configruation.config;

import com.kp.framework.utils.kptool.KPStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Author lipeng
 * @Description 解决 HttpServletRequest的getInputStream()和getReader()只能调用一次，【getReader()底层调用了getInputStream()】。@RequestBody也是流的形式读取，流读取一次就没了。
 * @Date 2023/11/20 11:40
 * @return
 **/
@Slf4j
public class MyRequestWrapper extends HttpServletRequestWrapper {
    private String body;

    private Map<String, String[]> parameters;

    private String queryString;


    @Override
    public String getQueryString(){
        return queryString;
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public Map<String, String[]> getParameterMap(){
        return parameters;
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayIns = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletIns = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return byteArrayIns.read();
            }
        };
        return  servletIns;
    }

    public String getBody() {
        return body;
    }

    public MyRequestWrapper(HttpServletRequest request) {
        super(request);
        if (KPStringUtil.isNotEmpty(request.getContentType()) && request.getContentType().contains(MediaType.MULTIPART_FORM_DATA.toString())) return;
        try {
            parameters = request.getParameterMap();
        }catch (Exception e){}
        try {
            queryString = request.getQueryString();
        }catch (Exception e){}

        try(BufferedReader reader = request.getReader()){
            body = reader.lines().collect(Collectors.joining());
            if (body.equals("\"\"") && request.getContentType().contains(MediaType.APPLICATION_JSON.toString())){
                body = "{}";
            }
        }catch (Exception e){
            log.error("read request from requestbody error",e);
        }
    }

    public void setBody(String body) {
        this.body = body;
    }


}
