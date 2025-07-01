package com.kunpeng.framework.annotation.impl.util;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kunpeng.framework.annotation.KPObjectChangeLogNote;
import com.kunpeng.framework.configruation.properties.KunPengFrameworkConfig;
import com.kunpeng.framework.constant.ObjectChangeLogOperateType;
import com.kunpeng.framework.entity.bo.ObjectChangeLogBO;
import com.kunpeng.framework.entity.bo.OperationUserMessageBO;
import com.kunpeng.framework.enums.DeleteFalgEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.mapper.ObjectChangeMapper;
import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.utils.kptool.KPIPUtil;
import com.kunpeng.framework.utils.kptool.KPJSONFactoryUtil;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPNumberUtil;
import com.kunpeng.framework.utils.kptool.KPReflectUtil;
import com.kunpeng.framework.utils.kptool.KPRequsetUtil;
import com.kunpeng.framework.utils.kptool.KPServiceUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPUuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ObjectChangeUtil {

    @Autowired
    private KunPengFrameworkConfig kunPengFrameworkConfig;


    /**
     * @Author lipeng
     * @Description 处理请求前执行
     * @Date
     * @param proceedingJoinPoint
     * @param operateLog
     * @return com.alibaba.fastjson.JSONObject
     **/
    public JSONObject boBefore(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog) {
        String type = ""; //类型
        List<Object> oldObject = new ArrayList<>(); //旧值

        switch (operateLog.operateType()){
            case ObjectChangeLogOperateType.ADD:
                type = ObjectChangeLogOperateType.ADD;
                break;
            case ObjectChangeLogOperateType.DELETE:
                type = ObjectChangeLogOperateType.DELETE;
                break;
            case ObjectChangeLogOperateType.UPDATE:
                type = ObjectChangeLogOperateType.UPDATE;
                //记录旧值
                oldObject = this.getResult(proceedingJoinPoint, operateLog);
                break;
            case ObjectChangeLogOperateType.ADD_OR_UPDATE:
                //记录旧值
                oldObject = this.getResult(proceedingJoinPoint, operateLog);
                if (oldObject.size() == 0){
                    type = ObjectChangeLogOperateType.ADD;
                }else{
                    type = ObjectChangeLogOperateType.UPDATE;
                }
                break;
            case ObjectChangeLogOperateType.DELETE_ADD:
                type = ObjectChangeLogOperateType.DELETE_ADD;
                //记录旧值
                oldObject = this.getResult(proceedingJoinPoint, operateLog);
                break;
        }

        return new KPJSONFactoryUtil()
                .put("type", type)
                .put("oldObject", oldObject)
                .build();
    }


    /**
     * @Author lipeng
     * @Description 处理请求后执行
     * @Date 2024/2/22 10:31
     * @param proceedingJoinPoint
     * @param operateLog
     * @param beforeJson
     * @return void
     **/
    public void boAround(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, JSONObject beforeJson) throws Throwable {
        HttpServletRequest request = KPRequsetUtil.getRequest();
        beforeJson.put("url", request.getRequestURL().toString());
        beforeJson.put("IP", KPIPUtil.getClinetIP(request));
        beforeJson.put("userMessage", request.getAttribute("userMessage"));


        new Thread(() -> {
            try {
                // 从切面织入点处通过反射机制获取织入点处的方法
                switch (beforeJson.getString("type")){
                    case ObjectChangeLogOperateType.ADD:
                        this.add(proceedingJoinPoint, operateLog, this.getResult(proceedingJoinPoint, operateLog), beforeJson);
                        break;
                case ObjectChangeLogOperateType.DELETE:
                    break;
                case ObjectChangeLogOperateType.UPDATE:
                    this.update(proceedingJoinPoint, operateLog, this.getResult(proceedingJoinPoint, operateLog), beforeJson);
                    break;
                case ObjectChangeLogOperateType.DELETE_ADD:
                    this.deleteAdd(proceedingJoinPoint, operateLog, this.getResult(proceedingJoinPoint, operateLog), beforeJson);
                    break;
                }
            } catch (Exception e) {
                log.info("自定义变更记录注解出现异常");
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * @Author lipeng
     * @Description 获取数据库内容
     * @Date 2024/2/23 9:46
     * @param proceedingJoinPoint
     * @param objectChangeLog 注解
     * @return java.lang.Object
     **/
    private List<Object> getResult(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote objectChangeLog) {
        String fileName = objectChangeLog.identification(), databaseFileName = objectChangeLog.identification();
        if (objectChangeLog.identification().contains(",")){
            String[] strs =  objectChangeLog.identification().split(",");
            fileName = strs[0];
            databaseFileName = strs[1];
        }

        List<Object> onlys = new ArrayList<>();
        if (onlys.size() == 0) {
            Object[] infos = proceedingJoinPoint.getArgs();
            for (Object info : infos) {
                Object value = null;
                if (info instanceof JSONObject){
                    value = KPJsonUtil.toJson(info).get(fileName);
                }else{
                    value = KPReflectUtil.getField(info, fileName);
                }
                if(value != null) onlys.add(value);
            }
        }

        if (onlys.size() == 0) return new ArrayList<>();

        ParentMapper mapper = (ParentMapper) KPServiceUtil.getBean(objectChangeLog.parentMapper());
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(databaseFileName, onlys)
                .orderByAsc(databaseFileName);
        List<Object> result = mapper.selectList(queryWrapper);
        return result;
    }


    /**
     * @Author lipeng
     * @Description 比较新老数据差异
     * @Date 2024/2/25 14:12
     * @param newObjects 新数据
     * @param oldObjects 老数据
     * @param operateLog 注解
     * @return java.util.List<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     **/
    private List<List<Map<String, Object>>> defaultDealUpdate(List<Object> newObjects, List<Object> oldObjects, KPObjectChangeLogNote operateLog) {
        List<List<Map<String, Object>>> returnList = new ArrayList<>();
        try {
            //兼容批量处理
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < oldObjects.size(); i++) {
                JSONObject oldData = KPJsonUtil.toJson(oldObjects.get(i));
                JSONObject newData = KPJsonUtil.toJson(newObjects.get(i));

                for (String key : oldData.keySet()) {
                    if (Arrays.asList(operateLog.notRecordField().split(",")).contains(key))
                        continue;

                    Field field = null;
                    try {
                        field = oldObjects.get(i).getClass().getDeclaredField(key);
                    }catch (Exception e){
                        //表单当前类没有找到 去父类找
                        field = oldObjects.get(i).getClass().getSuperclass().getDeclaredField(key);
                    }

                    ApiModelProperty dataName = field.getAnnotation(ApiModelProperty.class);
                    Object oldValue = oldData.get(key)==null?"":oldData.get(key);
                    Object newValue = newData.get(key)==null?"":newData.get(key);


                    Map result = new HashMap();
                    result.put("filedName", key);
                    result.put("identification", newData.get(operateLog.identification().contains(",")?operateLog.identification().split(",")[0]:operateLog.identification()));
                    result.put("oldValue", oldValue);
                    result.put("newValue", newValue);

                    if (dataName != null){
                        oldValue = this.translate(oldData.get(key), dataName.value());
                        newValue = this.translate(newData.get(key), dataName.value());
                        result.put("filedName", dataName.value());
                    }
                    if (oldValue == null) oldValue = "";
                    if (newValue == null) newValue = "";
                    if (oldValue.equals(newValue)) continue;


                    list.add(result);
                }
            }
            returnList.add(list);
            return returnList;
        } catch (Exception e) {
            log.error("比较异常", e);
            throw new KPServiceException("比较异常" + e.getMessage());
        }
    }


    /**
     * @Author lipeng
     * @Description 翻译 把 数字转成字符 入 0-女 1 -男
     * @Date 2024/2/23 11:25
     * @param obj 内容
     * @param value 转换的依据
     * @return java.lang.Object
     **/
    private Object translate(Object obj, String value) {
        if (KPStringUtil.isEmpty(value)) return obj;

        try {
            Integer number = Integer.valueOf((String) obj);
            Map<Integer, String> map = KPNumberUtil.queryNumberSplit(value);
            return map.get(number);
        }catch (Exception ex){
            return obj;
        }
    }



    /**
     * @Author lipeng
     * @Description 新增处理逻辑
     * @Date 2024/2/25 14:13
     * @param proceedingJoinPoint
     * @param operateLog
     * @param objects
     * @return void
     **/
    private void add(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, List<Object> objects, JSONObject beforeJson) {
        if (objects.size() ==0) return;

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        for (Object obj : objects){
            JSONObject row = KPJsonUtil.toJson(obj);
            ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
            OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
            if (opUserMessageBO != null){
                objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
                objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
                objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
                objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
            }

            objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
            objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
            objectChangeLogBO.setIdentification(row.getString(operateLog.identification().contains(",")?operateLog.identification().split(",")[0]:operateLog.identification()));
            objectChangeLogBO.setOperateType(beforeJson.getString("type"));
            objectChangeLogBO.setBusinessType(operateLog.businessType());
            objectChangeLogBO.setUrl(beforeJson.getString("url"));
            objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
            objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
            objectChangeLogBO.setProjectName(kunPengFrameworkConfig.getProjectName());

            objectChangeLogBO.setCreateDate(LocalDateTime.now());
            objectChangeLogBO.setUpdateDate(LocalDateTime.now());
            objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

            DynamicDataSourceContextHolder.push(operateLog.saveDS());
            KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(operateLog.saveTableName(), objectChangeLogBO);
            DynamicDataSourceContextHolder.poll();
        }
    }


    /**
     * @Author lipeng
     * @Description 修改处理逻辑
     * @Date 2024/2/25 14:13
     * @param proceedingJoinPoint
     * @param operateLog
     * @param newObject
     * @param beforeJson
     * @return void
     **/
    private void update(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, List<Object> newObject, JSONObject beforeJson) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        //比较新数据与数据库原数据  第一次list 批量操作数据 如果不是批量操作 只有一天  第二个list 是改变的字段
        List<List<Map<String, Object>>> returnList = this.defaultDealUpdate(newObject, beforeJson.getJSONArray("oldObject"), operateLog);
        if (returnList.size() == 0) return;

        for (List<Map<String, Object>> list : returnList) {
            if (list.size() == 0) continue;


            //改内容记录  唯一标识
            String changeBody = "", identification = ""; //唯一标识
            for (Map<String, Object> dataMap : list) {
                changeBody += KPStringUtil.format("{0}由 {1} 修改为 {2} |", dataMap.get("filedName"), KPStringUtil.isEmpty(dataMap.get("oldValue"))?"空值":dataMap.get("oldValue"), KPStringUtil.isEmpty(dataMap.get("newValue"))?"空值":dataMap.get("newValue"));
                identification = String.valueOf(dataMap.get("identification"));
            }
            ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
            OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
            if (opUserMessageBO != null){
                objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
                objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
                objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
                objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
            }

            objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
            objectChangeLogBO.setChangeBody(KPStringUtil.isEmpty(changeBody)?"":changeBody.substring(0, changeBody.length()-1));
            objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
            objectChangeLogBO.setIdentification(identification);
            objectChangeLogBO.setOperateType(beforeJson.getString("type"));
            objectChangeLogBO.setBusinessType(operateLog.businessType());
            objectChangeLogBO.setUrl(beforeJson.getString("url"));
            objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
            objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
            objectChangeLogBO.setProjectName(kunPengFrameworkConfig.getProjectName());

            objectChangeLogBO.setCreateDate(LocalDateTime.now());
            objectChangeLogBO.setUpdateDate(LocalDateTime.now());
            objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

            DynamicDataSourceContextHolder.push(operateLog.saveDS());
            KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(operateLog.saveTableName(), objectChangeLogBO);
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * @Author lipeng
     * @Description 先删除后新增
     * @Date 2024/3/13 10:20
     * @param joinPoint
     * @param operateLog
     * @param result
     * @return void
     **/
    private void deleteAdd(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, List<Object> result, JSONObject beforeJson) {
        List<Object> oldObject = beforeJson.getJSONArray("oldObject");
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        if (oldObject.size() == 0 && result.size() == 0) return;

        List<String> notRecordField = Arrays.asList(operateLog.notRecordField().split(","));

        StringBuffer builder = new StringBuffer();
        if (oldObject.size() != 0){
            List<JSONObject> body = new ArrayList<>();
            oldObject.forEach(obj -> {
                JSONObject json = KPJsonUtil.toJson(obj);
                notRecordField.forEach(field -> {
                    try {
                        json.remove(field);
                    }catch (Exception ex){}
                });
                body.add(json);
            });
            builder.append("删除的数据 ").append(KPJsonUtil.toJsonString(body));
        }

        if (result.size() != 0){
            List<JSONObject> body = new ArrayList<>();
            result.forEach(obj -> {
                JSONObject json = KPJsonUtil.toJson(obj);
                notRecordField.forEach(field -> {
                    try {
                        json.remove(field);
                    }catch (Exception ex){}
                });
                body.add(json);
            });
            builder.append(oldObject.size() != 0?"|新增的数据 ":"新增的数据").append(KPJsonUtil.toJsonString(body));
        }



        String changeBody = builder.toString();
        String identification = "";
        if (result.size()!=0){
            JSONObject newData = KPJsonUtil.toJson(result.get(0));
            identification = newData.getString(operateLog.identification().contains(",")?operateLog.identification().split(",")[0]:operateLog.identification());
        }else if (oldObject.size() !=0){
            JSONObject oldData = KPJsonUtil.toJson(oldObject.get(0));
            identification = oldData.getString(operateLog.identification().contains(",")?operateLog.identification().split(",")[0]:operateLog.identification());
        }


        ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
        OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
        if (opUserMessageBO != null){
            objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
            objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
            objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
            objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
            objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
            objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
        }

        objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
        objectChangeLogBO.setChangeBody(KPStringUtil.isEmpty(changeBody)?"":changeBody);
        objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
        objectChangeLogBO.setIdentification(identification);
        objectChangeLogBO.setOperateType(beforeJson.getString("type"));
        objectChangeLogBO.setBusinessType(operateLog.businessType());
        objectChangeLogBO.setUrl(beforeJson.getString("url"));
        objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
        objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
        objectChangeLogBO.setProjectName(kunPengFrameworkConfig.getProjectName());

        objectChangeLogBO.setCreateDate(LocalDateTime.now());
        objectChangeLogBO.setUpdateDate(LocalDateTime.now());
        objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

        DynamicDataSourceContextHolder.push(operateLog.saveDS());
        KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(operateLog.saveTableName(), objectChangeLogBO);
        DynamicDataSourceContextHolder.poll();
    }
}
