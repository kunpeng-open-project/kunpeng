package com.kp.framework.annotation.impl.verify;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.annotation.KPApiJsonlParamMode;
import com.kp.framework.annotation.verify.KPLength;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import com.kp.framework.configruation.config.MyRequestWrapper;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPReflectUtil;
import com.kp.framework.utils.kptool.KPRequsetUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Author lipeng
 * @Description 校验器处理入口
 * @Date 2024/3/4 8:59
 * @return
 **/
@Component
@Slf4j
public class KPVerifyBuilder {

    @Autowired
    private KPLengthBuilder kPLengthBuilder;

    @Autowired
    private KPNotNullBuilder kpNotNullBuilder;

    @Autowired
    private KPMaxLengthBuilder kpMaxLengthBuilder;



    public boolean verify(HttpServletRequest request, HandlerMethod handlerMethod) {
        // 获取所有方法参数
        JSONObject json = KPRequsetUtil.getJSONParam(request);

        KPApiJsonlParamMode kpApiJsonlParamMode = handlerMethod.getMethodAnnotation(KPApiJsonlParamMode.class);
        //屏蔽的字段
        List<String> excludeList = new ArrayList();
        //包含的字段
        List<String> globalList = new ArrayList();
        if (kpApiJsonlParamMode != null){
            if (KPStringUtil.isNotEmpty(kpApiJsonlParamMode.includes())){
                globalList = Arrays.asList(kpApiJsonlParamMode.includes().split(kpApiJsonlParamMode.separator()));
            }else if(KPStringUtil.isNotEmpty(kpApiJsonlParamMode.ignores())){
                excludeList = Arrays.asList(kpApiJsonlParamMode.ignores().split(kpApiJsonlParamMode.separator()));
            }
        }

        for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
            Field[] fields = null;
            //入参最外层是list
            if (parameter.getParameterType().getName().equalsIgnoreCase("java.util.List")){
                try {
                    fields = KPReflectUtil.getAllDeclaredFields((Class<?>) (((ParameterizedType) parameter.getGenericParameterType()).getActualTypeArguments()[0]), excludeList, globalList).stream().toArray(Field[]::new);
                } catch (Exception e) {
                    log.info("[list参数校验异常]： {}", e.getMessage());
                }
                return this.verifyDispose(fields, KPJsonUtil.toJavaObjectList(((MyRequestWrapper) request).getBody(), JSONObject.class));
            }else{
                try {
                    fields = KPReflectUtil.getAllDeclaredFields(parameter.getParameterType(), excludeList, globalList).stream().toArray(Field[]::new);
                }catch (Exception e){
                    fields = parameter.getParameterType().getDeclaredFields();
                }
                return this.verifyDispose(fields, json);
            }
        }
        return true;
    }



    /**
     * @Author lipeng
     * @Description 处理校验器
     * @Date 2024/3/4 8:58
     * @param fields
     * @param json
     * @return java.lang.Boolean
     **/
    private Boolean verifyDispose(Field[] fields, JSONObject json){
        for (Field field : fields) {
            //表示是list 对象
            if (field.getType() == List.class){

                //先判断是否必传
                KPNotNull kpNotNull = field.getAnnotation(KPNotNull.class);
                if (kpNotNull != null) {
                    try {
                        if (!kpNotNullBuilder.dispose(field, kpNotNull, json))
                            return false;
                    }catch (NullPointerException ex){
                        throw new KPServiceException(field.getName() + "为空校验异常");
                    }

                }

                //在校验子类
                Type genericFieldType = field.getGenericType();
                if (genericFieldType instanceof ParameterizedType && !((ParameterizedTypeImpl) genericFieldType).getActualTypeArguments()[0].getTypeName().contains("java")) {
                    // 将其转换为ParameterizedType以访问实际的类型参数
                    ParameterizedType parameterizedType = (ParameterizedType) genericFieldType;
                    // 获取第一个类型参数（对于List，它就是元素类型）
                    Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                    JSONArray jsonArray = json.getJSONArray(field.getName());
                    if (jsonArray == null) continue;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        if (!this.verifyDispose(((Class) actualTypeArgument).getDeclaredFields(), jsonArray.getJSONObject(i)))
                            return false;
                    }
                }
            }else{
                if (!field.getType().isPrimitive() && !field.getType().getName().contains("java.")){
                    //这是对象
                    if (!this.verifyDispose(field.getType().getDeclaredFields(), json.getJSONObject(field.getName())))
                        return false;
                }else{
                    KPNotNull kpNotNull = field.getAnnotation(KPNotNull.class);
                    if (kpNotNull != null) {
                        if (!kpNotNullBuilder.dispose(field, kpNotNull, json))
                            return false;
                    }

                    KPLength kPLength = field.getAnnotation(KPLength.class);
                    if (kPLength != null) {
                        if (!kPLengthBuilder.dispose(field, kPLength, json))
                            return false;
                    }

                    KPMaxLength kpMaxLength = field.getAnnotation(KPMaxLength.class);
                    if (kpMaxLength != null) {
                        if (!kpMaxLengthBuilder.dispose(field, kpMaxLength, json))
                            return false;
                    }
                }
            }
        }
        return true;
    }


    private Boolean verifyDispose(Field[] fields, List<JSONObject> jsons){
        for (JSONObject json : jsons){
            if (!this.verifyDispose(fields, json))
                return false;
        }
        return true;
    }
}
