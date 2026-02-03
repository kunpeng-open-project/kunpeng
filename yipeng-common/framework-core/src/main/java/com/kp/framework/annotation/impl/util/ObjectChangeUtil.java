package com.kp.framework.annotation.impl.util;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kp.framework.annotation.KPObjectChangeLogNote;
import com.kp.framework.configruation.properties.KPFrameworkConfig;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.entity.bo.ObjectChangeLogBO;
import com.kp.framework.entity.bo.OperationUserMessageBO;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.mapper.ObjectChangeMapper;
import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.utils.kptool.KPIPUtil;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPNumberUtil;
import com.kp.framework.utils.kptool.KPReflectUtil;
import com.kp.framework.utils.kptool.KPRequsetUtil;
import com.kp.framework.utils.kptool.KPServiceUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPUuidUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ObjectChangeUtil {

    @Autowired
    private KPFrameworkConfig kpFrameworkConfig;


    /**
     * 处理请求前执行。
     * @author lipeng
     * 2024/2/22
     * @param proceedingJoinPoint 切面织入点
     * @param operateLog 注解
     * @return com.alibaba.fastjson2.JSONObject
     */
    public JSONObject boBefore(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog) {
        String type = ""; //类型
        List<Object> oldObject = new ArrayList<>(); //旧值

        switch (operateLog.operateType()) {
            case ObjectChangeLogOperateType.ADD:
                type = ObjectChangeLogOperateType.ADD;
                break;
            case ObjectChangeLogOperateType.ADD_BATCH:
                type = ObjectChangeLogOperateType.ADD_BATCH;
                break;
            case ObjectChangeLogOperateType.DELETE:
                type = ObjectChangeLogOperateType.DELETE;
                //记录旧值
                oldObject = this.getResult(proceedingJoinPoint, operateLog);
                break;
            case ObjectChangeLogOperateType.UPDATE:
                type = ObjectChangeLogOperateType.UPDATE;
                //记录旧值
                oldObject = this.getResult(proceedingJoinPoint, operateLog);
                break;
            case ObjectChangeLogOperateType.UPDATE_BATCH:
                type = ObjectChangeLogOperateType.UPDATE_BATCH;
                //记录旧值
                oldObject = this.getResult(proceedingJoinPoint, operateLog);
                break;
            case ObjectChangeLogOperateType.ADD_OR_UPDATE:
                //记录旧值
                oldObject = this.getResult(proceedingJoinPoint, operateLog);
                if (oldObject.size() == 0) {
                    type = ObjectChangeLogOperateType.ADD;
                } else {
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
     * 处理请求后执行。
     * @author lipeng
     * 2024/2/22
     * @param proceedingJoinPoint 切面织入点
     * @param operateLog 注解
     * @param beforeJson 请求前的数据
     */
    public void boAround(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, JSONObject beforeJson) throws Throwable {
        HttpServletRequest request = KPRequsetUtil.getRequest();
        beforeJson.put("url", request.getRequestURL().toString());
        beforeJson.put("IP", KPIPUtil.getClientIP(request));
        beforeJson.put("userMessage", request.getAttribute("userMessage"));


        new Thread(() -> {
            try {
                // 从切面织入点处通过反射机制获取织入点处的方法
                switch (beforeJson.getString("type")) {
                    case ObjectChangeLogOperateType.ADD:
                        this.add(proceedingJoinPoint, operateLog, this.getResult(proceedingJoinPoint, operateLog), beforeJson);
                        break;
                    case ObjectChangeLogOperateType.ADD_BATCH:
                        this.addBatch(proceedingJoinPoint, operateLog, this.getResult(proceedingJoinPoint, operateLog), beforeJson);
                        break;
                    case ObjectChangeLogOperateType.DELETE:
                        this.delete(proceedingJoinPoint, operateLog, beforeJson);
                        break;
                    case ObjectChangeLogOperateType.UPDATE:
                        this.update(proceedingJoinPoint, operateLog, this.getResult(proceedingJoinPoint, operateLog), beforeJson);
                        break;
                    case ObjectChangeLogOperateType.UPDATE_BATCH:
                        this.updateBatch(proceedingJoinPoint, operateLog, this.getResult(proceedingJoinPoint, operateLog), beforeJson);
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
     * 获取数据库内容。
     * @author lipeng
     * 2024/2/23
     * @param proceedingJoinPoint 切面织入点
     * @param objectChangeLog 注解
     * @return java.util.List<java.lang.Object>
     */
    private List<Object> getResult(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote objectChangeLog) {
        String fileName, databaseFileName;
        if (objectChangeLog.identification().contains(",")) {
            String[] strs = objectChangeLog.identification().split(",");
            fileName = strs[0];
            databaseFileName = strs[1];
        } else {
            fileName = objectChangeLog.identification();
            databaseFileName = objectChangeLog.identification();
        }

        List<Object> onlys = new ArrayList<>();
        if (onlys.size() == 0) {
            Object[] infos = proceedingJoinPoint.getArgs();
            for (Object info : infos) {
                Object value = null;
                if (info instanceof JSONObject) {
                    value = KPJsonUtil.toJson(info).get(fileName);
                } else if (info instanceof List) {
                    List<?> list = (List<?>) info;
                    if (KPStringUtil.isNotEmpty(list)) {
                        Object first = list.get(0);
                        if (first instanceof String || first instanceof Number || first instanceof Boolean) {
                            onlys.addAll(list);
                        } else {
                            try {
                                List<Object> result = ((ArrayList<?>) info).stream()
                                        .map(item -> KPReflectUtil.getField(item, fileName)) // 逐个反射获取字段值
                                        .filter(Objects::nonNull) // 过滤null值
                                        .collect(Collectors.toList()); // 收集为List
                                onlys.addAll(result);
                            } catch (Exception ex) {
                            }
                        }
                    }
                } else {
                    value = KPReflectUtil.getField(info, fileName);
                }
                if (value != null) onlys.add(value);
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
     * 比较新老数据差异。
     * @author lipeng
     * 2024/2/25
     * @param newObjects 新数据
     * @param oldObjects 老数据
     * @param operateLog 注解
     * @return java.util.List<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     */
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
                    } catch (Exception e) {
                        //表单当前类没有找到 去父类找
                        field = oldObjects.get(i).getClass().getSuperclass().getDeclaredField(key);
                    }

                    Schema dataName = field.getAnnotation(Schema.class);
                    Object oldValue = oldData.get(key) == null ? "" : oldData.get(key);
                    Object newValue = newData.get(key) == null ? "" : newData.get(key);


                    Map result = new HashMap();
                    result.put("filedName", key);
                    result.put("identification", newData.get(operateLog.identification().contains(",") ? operateLog.identification().split(",")[0] : operateLog.identification()));
                    result.put("oldValue", oldValue);
                    result.put("newValue", newValue);

                    if (dataName != null) {
                        oldValue = this.translate(oldData.get(key), dataName.description());
                        newValue = this.translate(newData.get(key), dataName.description());
                        result.put("filedName", dataName.description());
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
     * 翻译 把 数字转成字符 入 0-女 1 -男。
     * @author lipeng
     * 2024/2/23
     * @param obj 内容
     * @param value 转换的依据
     * @return java.lang.Object
     */
    private Object translate(Object obj, String value) {
        if (KPStringUtil.isEmpty(value)) return obj;

        try {
            Integer number = Integer.valueOf((String) obj);
            Map<Integer, String> map = KPNumberUtil.queryNumberSplit(value);
            return map.get(number);
        } catch (Exception ex) {
            return obj;
        }
    }

    /**
     * 新增处理逻辑。
     * @author lipeng
     * 2024/2/25
     * @param proceedingJoinPoint 切点
     * @param operateLog 注解
     * @param objects 新增数据
     * @param beforeJson 新增数据前的数据
     */
    private void add(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, List<Object> objects, JSONObject beforeJson) {
        if (objects.size() == 0) return;

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        for (Object obj : objects) {
            JSONObject row = KPJsonUtil.toJson(obj);
            ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
            OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
            if (opUserMessageBO != null) {
                objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
                objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
                objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
                objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
            }

            objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
            objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
            objectChangeLogBO.setIdentification(row.getString(operateLog.identification().contains(",") ? operateLog.identification().split(",")[0] : operateLog.identification()));
            objectChangeLogBO.setOperateType(beforeJson.getString("type"));
            objectChangeLogBO.setBusinessType(operateLog.businessType());
            objectChangeLogBO.setUrl(beforeJson.getString("url"));
            objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
            objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
            objectChangeLogBO.setProjectName(kpFrameworkConfig.getProjectName());
            objectChangeLogBO.setChangeBody(KPJsonUtil.toJsonString(obj));

            objectChangeLogBO.setCreateDate(LocalDateTime.now());
            objectChangeLogBO.setUpdateDate(LocalDateTime.now());
            objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

            DynamicDataSourceContextHolder.push(operateLog.saveDS());
            KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(operateLog.saveTableName(), objectChangeLogBO);
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * 批量新增。
     * @author lipeng
     * 2024/2/25
     * @param proceedingJoinPoint 切点
     * @param operateLog 注解
     * @param objects 新增数据
     * @param beforeJson 新增数据前的数据
     */
    private void addBatch(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, List<Object> objects, JSONObject beforeJson) {
        if (objects.size() == 0) return;

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();


        JSONObject row = KPJsonUtil.toJson(objects.get(0));
        ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
        OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
        if (opUserMessageBO != null) {
            objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
            objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
            objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
            objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
            objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
            objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
        }

        objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
        objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
        objectChangeLogBO.setIdentification(row.getString(operateLog.identification().contains(",") ? operateLog.identification().split(",")[0] : operateLog.identification()));
        objectChangeLogBO.setOperateType(beforeJson.getString("type"));
        objectChangeLogBO.setBusinessType(operateLog.businessType());
        objectChangeLogBO.setUrl(beforeJson.getString("url"));
        objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
        objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
        objectChangeLogBO.setProjectName(kpFrameworkConfig.getProjectName());
        objectChangeLogBO.setChangeBody(KPJsonUtil.toJavaObjectList(objects, JSONObject.class).toString());

        objectChangeLogBO.setCreateDate(LocalDateTime.now());
        objectChangeLogBO.setUpdateDate(LocalDateTime.now());
        objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

        DynamicDataSourceContextHolder.push(operateLog.saveDS());
        KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(operateLog.saveTableName(), objectChangeLogBO);
        DynamicDataSourceContextHolder.poll();
    }

    /**
     * 修改处理逻辑。
     * @author lipeng
     * 2024/2/25
     * @param proceedingJoinPoint 切点
     * @param operateLog 注解
     * @param newObject 修改数据
     * @param beforeJson 修改数据前的数据
     */
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
                changeBody += KPStringUtil.format("{0}由 {1} 修改为 {2} |", dataMap.get("filedName"), KPStringUtil.isEmpty(dataMap.get("oldValue")) ? "空值" : dataMap.get("oldValue"), KPStringUtil.isEmpty(dataMap.get("newValue")) ? "空值" : dataMap.get("newValue"));
                identification = String.valueOf(dataMap.get("identification"));
            }
            ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
            OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
            if (opUserMessageBO != null) {
                objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
                objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
                objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
                objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
            }

            objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
            objectChangeLogBO.setChangeBody(KPStringUtil.isEmpty(changeBody) ? "" : changeBody.substring(0, changeBody.length() - 1));
            objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
            objectChangeLogBO.setIdentification(identification);
            objectChangeLogBO.setOperateType(beforeJson.getString("type"));
            objectChangeLogBO.setBusinessType(operateLog.businessType());
            objectChangeLogBO.setUrl(beforeJson.getString("url"));
            objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
            objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
            objectChangeLogBO.setProjectName(kpFrameworkConfig.getProjectName());

            objectChangeLogBO.setCreateDate(LocalDateTime.now());
            objectChangeLogBO.setUpdateDate(LocalDateTime.now());
            objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

            DynamicDataSourceContextHolder.push(operateLog.saveDS());
            KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(operateLog.saveTableName(), objectChangeLogBO);
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * 批量修改处理逻辑。
     * @author lipeng
     * 2025/11/26
     * @param proceedingJoinPoint 切点
     * @param operateLog 注解
     * @param newObject 批量修改数据
     * @param beforeJson 修改数据前的数据
     * @return void
     */
    private void updateBatch(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, List<Object> newObject, JSONObject beforeJson) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        String fileName;
        if (operateLog.identification().contains(",")) {
            fileName = operateLog.identification().split(",")[0];
        } else {
            fileName = operateLog.identification();
        }

        //加了  newObject.forEach(obj -> {  是为了批量操作的时候记录多条 不至于数据混乱 例如 批量用户解锁等
        newObject.forEach(newData -> {
            // 直接从原始对象获取匹配字段值（用反射工具类）
            String newId = KPReflectUtil.getField(newData, fileName).toString();

            // 遍历原始旧数据列表，匹配字段值（保留原始对象）
            Object oldData = beforeJson.getJSONArray("oldObject").stream()
                    .filter(pldData -> {
                        String oldId = KPReflectUtil.getField(pldData, fileName).toString();
                        return newId != null && newId.equals(oldId);
                    })
                    .findFirst()
                    .orElse(null);

            if (oldData == null) return;

            //比较新数据与数据库原数据  第一次list 批量操作数据 如果不是批量操作 只有一天  第二个list 是改变的字段
            List<List<Map<String, Object>>> returnList = this.defaultDealUpdate(Arrays.asList(newData), Arrays.asList(oldData), operateLog);
            if (returnList.size() == 0) return;

            for (List<Map<String, Object>> list : returnList) {
                if (list.size() == 0) continue;


                //改内容记录  唯一标识
                String changeBody = "", identification = ""; //唯一标识
                for (Map<String, Object> dataMap : list) {
                    changeBody += KPStringUtil.format("{0}由 {1} 修改为 {2} |", dataMap.get("filedName"), KPStringUtil.isEmpty(dataMap.get("oldValue")) ? "空值" : dataMap.get("oldValue"), KPStringUtil.isEmpty(dataMap.get("newValue")) ? "空值" : dataMap.get("newValue"));
                    identification = String.valueOf(dataMap.get("identification"));
                }
                ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
                OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
                if (opUserMessageBO != null) {
                    objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
                    objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
                    objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
                    objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
                    objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
                    objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
                }

                objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
                objectChangeLogBO.setChangeBody(KPStringUtil.isEmpty(changeBody) ? "" : changeBody.substring(0, changeBody.length() - 1));
                objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
                objectChangeLogBO.setIdentification(identification);
                objectChangeLogBO.setOperateType(beforeJson.getString("type"));
                objectChangeLogBO.setBusinessType(operateLog.businessType());
                objectChangeLogBO.setUrl(beforeJson.getString("url"));
                objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
                objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
                objectChangeLogBO.setProjectName(kpFrameworkConfig.getProjectName());

                objectChangeLogBO.setCreateDate(LocalDateTime.now());
                objectChangeLogBO.setUpdateDate(LocalDateTime.now());
                objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

                DynamicDataSourceContextHolder.push(operateLog.saveDS());
                KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(operateLog.saveTableName(), objectChangeLogBO);
                DynamicDataSourceContextHolder.poll();
            }
        });
    }

    /**
     * 先删除后新增。
     * @author lipeng
     * 2024/3/13
     * @param proceedingJoinPoint 切点
     * @param operateLog 注解
     * @param result 批量修改数据
     * @param beforeJson 修改数据前的数据
     */
    private void deleteAdd(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote operateLog, List<Object> result, JSONObject beforeJson) {
        List<Object> oldObject = beforeJson.getJSONArray("oldObject");
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        if (oldObject.size() == 0 && result.size() == 0) return;

        List<String> notRecordField = Arrays.asList(operateLog.notRecordField().split(","));

        StringBuffer builder = new StringBuffer();
        if (oldObject.size() != 0) {
            List<JSONObject> body = new ArrayList<>();
            oldObject.forEach(obj -> {
                JSONObject json = KPJsonUtil.toJson(obj);
                notRecordField.forEach(field -> {
                    try {
                        json.remove(field);
                    } catch (Exception ex) {
                    }
                });
                body.add(json);
            });
            builder.append("删除的数据 ").append(KPJsonUtil.toJsonString(body));
        }

        if (result.size() != 0) {
            List<JSONObject> body = new ArrayList<>();
            result.forEach(obj -> {
                JSONObject json = KPJsonUtil.toJson(obj);
                notRecordField.forEach(field -> {
                    try {
                        json.remove(field);
                    } catch (Exception ex) {
                    }
                });
                body.add(json);
            });
            builder.append(oldObject.size() != 0 ? "|新增的数据 " : "新增的数据").append(KPJsonUtil.toJsonString(body));
        }


        String changeBody = builder.toString();
        String identification = "";
        if (result.size() != 0) {
            JSONObject newData = KPJsonUtil.toJson(result.get(0));
            identification = newData.getString(operateLog.identification().contains(",") ? operateLog.identification().split(",")[0] : operateLog.identification());
        } else if (oldObject.size() != 0) {
            JSONObject oldData = KPJsonUtil.toJson(oldObject.get(0));
            identification = oldData.getString(operateLog.identification().contains(",") ? operateLog.identification().split(",")[0] : operateLog.identification());
        }


        ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
        OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
        if (opUserMessageBO != null) {
            objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
            objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
            objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
            objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
            objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
            objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
        }

        objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
        objectChangeLogBO.setChangeBody(KPStringUtil.isEmpty(changeBody) ? "" : changeBody);
        objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
        objectChangeLogBO.setIdentification(identification);
        objectChangeLogBO.setOperateType(beforeJson.getString("type"));
        objectChangeLogBO.setBusinessType(operateLog.businessType());
        objectChangeLogBO.setUrl(beforeJson.getString("url"));
        objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
        objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
        objectChangeLogBO.setProjectName(kpFrameworkConfig.getProjectName());

        objectChangeLogBO.setCreateDate(LocalDateTime.now());
        objectChangeLogBO.setUpdateDate(LocalDateTime.now());
        objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

        DynamicDataSourceContextHolder.push(operateLog.saveDS());
        KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(operateLog.saveTableName(), objectChangeLogBO);
        DynamicDataSourceContextHolder.poll();
    }

    /**
     * 删除。
     * @author lipeng
     * 2025/10/28
     * @param proceedingJoinPoint 切点
     * @param kpObjectChangeLogNote 注解
     * @param beforeJson 修改数据前的数据
     */
    private void delete(ProceedingJoinPoint proceedingJoinPoint, KPObjectChangeLogNote kpObjectChangeLogNote, JSONObject beforeJson) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        String fileName = kpObjectChangeLogNote.identification();
        if (kpObjectChangeLogNote.identification().contains(","))
            fileName = kpObjectChangeLogNote.identification().split(",")[0];


        List<JSONObject> deleteList = KPJsonUtil.toJavaObjectList(beforeJson.getJSONArray("oldObject"), JSONObject.class);
        if (deleteList.size() == 0) return;

        final String fileName_final = fileName;
        deleteList.forEach(del -> {
            ObjectChangeLogBO objectChangeLogBO = new ObjectChangeLogBO();
            OperationUserMessageBO opUserMessageBO = beforeJson.getObject("userMessage", OperationUserMessageBO.class);
            if (opUserMessageBO != null) {
                objectChangeLogBO.setPhone(opUserMessageBO.getPhone());
                objectChangeLogBO.setSerial(opUserMessageBO.getSerial());
                objectChangeLogBO.setCreateUserName(opUserMessageBO.getName());
                objectChangeLogBO.setCreateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserId(opUserMessageBO.getId());
                objectChangeLogBO.setUpdateUserName(opUserMessageBO.getName());
            }


            objectChangeLogBO.setUuid(KPUuidUtil.getSimpleUUID());
            objectChangeLogBO.setChangeBody(KPStringUtil.format("数据被删除：删除用户id: {0} 删除用户姓名: {1}", opUserMessageBO == null ? "游客" : opUserMessageBO.getId(), opUserMessageBO == null ? "游客" : opUserMessageBO.getName()));
            objectChangeLogBO.setParameter(KPJsonUtil.toJsonString(proceedingJoinPoint.getArgs()));
            objectChangeLogBO.setIdentification(del.getString(fileName_final));
            objectChangeLogBO.setOperateType(beforeJson.getString("type"));
            objectChangeLogBO.setBusinessType(kpObjectChangeLogNote.businessType());
            objectChangeLogBO.setUrl(beforeJson.getString("url"));
            objectChangeLogBO.setClassName(proceedingJoinPoint.getTarget().getClass().getName() + "." + signature.getMethod().getName());
            objectChangeLogBO.setClinetIp(beforeJson.getString("IP"));
            objectChangeLogBO.setProjectName(kpFrameworkConfig.getProjectName());

            objectChangeLogBO.setCreateDate(LocalDateTime.now());
            objectChangeLogBO.setUpdateDate(LocalDateTime.now());
            objectChangeLogBO.setDeleteFlag(DeleteFalgEnum.NORMAL.code());

            DynamicDataSourceContextHolder.push(kpObjectChangeLogNote.saveDS());
            KPServiceUtil.getBean(ObjectChangeMapper.class).saveObjectChange(kpObjectChangeLogNote.saveTableName(), objectChangeLogBO);
            DynamicDataSourceContextHolder.poll();
        });
    }

}
