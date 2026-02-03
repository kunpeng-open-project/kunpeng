package com.kp.framework.controller.server;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.kp.framework.constant.ObjectChangeLogOperateType;
import com.kp.framework.controller.mapper.ObjectChangeLogMapper;
import com.kp.framework.controller.po.ObjectChangeLogPO;
import com.kp.framework.controller.po.param.ObjectChangeLogListParamPO;
import com.kp.framework.entity.bo.FileUploadBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.utils.kptool.KPJSONFactoryUtil;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPMinioUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类。
 * @author lipeng
 * 2025-11-10
 */
@Service
@Slf4j
public class ObjectChangeLogService extends ServiceImpl<ObjectChangeLogMapper,
        ObjectChangeLogPO> {

    /**
     * 查询列表。
     * @author lipeng
     * 2025-11-10
     * @param objectChangeLogListParamPO 查询参数
     * @return com.kp.framework.entity.bo.KPResult<com.kp.framework.modules.system.po.ObjectChangeLogPO>
     */
    public KPResult<ObjectChangeLogPO> queryPageList(ObjectChangeLogListParamPO objectChangeLogListParamPO) {
        //搜索条件
        LambdaQueryWrapper<ObjectChangeLogPO> queryWrapper = Wrappers.lambdaQuery(ObjectChangeLogPO.class)
                .eq(KPStringUtil.isNotEmpty(objectChangeLogListParamPO.getProjectName()), ObjectChangeLogPO::getProjectName, objectChangeLogListParamPO.getProjectName())
                .eq(KPStringUtil.isNotEmpty(objectChangeLogListParamPO.getBusinessType()), ObjectChangeLogPO::getBusinessType, objectChangeLogListParamPO.getBusinessType())
                .eq(KPStringUtil.isNotEmpty(objectChangeLogListParamPO.getIdentification()), ObjectChangeLogPO::getIdentification, objectChangeLogListParamPO.getIdentification());

        //分页和排序
        PageHelper.startPage(objectChangeLogListParamPO.getPageNum(), objectChangeLogListParamPO.getPageSize(), objectChangeLogListParamPO.getOrderBy(ObjectChangeLogPO.class));
        List<ObjectChangeLogPO> list = this.baseMapper.selectList(queryWrapper);

        this.handler(list);

        return KPResult.list(list);
    }

    /**
     * 处理器。
     * @author lipeng
     * 2025/11/11
     * @param list  列表
     */
    private void handler(List<ObjectChangeLogPO> list) {
        list.forEach(objectChangeLogPO -> {
            this.addBatch(objectChangeLogPO);
            this.deleteAdd(objectChangeLogPO);
        });
    }

    /**
     * 翻译批量新增中的附件。
     * @author lipeng
     * 2025/11/11
     * @param objectChangeLogPO 日志
     */
    private void addBatch(ObjectChangeLogPO objectChangeLogPO) {
        if (KPStringUtil.isEmpty(objectChangeLogPO.getChangeBody())) return;
        if (!objectChangeLogPO.getOperateType().equals(ObjectChangeLogOperateType.ADD_BATCH)) return;

        try {
            List<JSONObject> bodyList = new ArrayList<>();
            List<JSONObject> list = KPJsonUtil.toJavaObjectList(objectChangeLogPO.getChangeBody(), JSONObject.class);
            list.forEach(row -> {
                if (KPStringUtil.isNotEmpty(row.getString("filePath"))) {
                    bodyList.add(new KPJSONFactoryUtil(KPJsonUtil.toJson(KPJsonUtil.toJavaObject(row, FileUploadBO.class)))
                            .put("url", KPMinioUtil.getUrl(row.getString("filePath"), 1))
                            .build());
                }
            });

            objectChangeLogPO.setChangeBody(KPJsonUtil.toJsonString(bodyList));
        } catch (Exception ex) {
            log.error("翻译批量新增中的附件异常: {}", ex.getMessage());
        }
    }

    /**
     * 翻译先删后增的附件。
     * @author lipeng
     * 2025/11/11
     * @param objectChangeLogPO 日志
     */
    private void deleteAdd(ObjectChangeLogPO objectChangeLogPO) {
        if (KPStringUtil.isEmpty(objectChangeLogPO.getChangeBody())) return;
        if (!objectChangeLogPO.getOperateType().equals(ObjectChangeLogOperateType.DELETE_ADD)) return;

        try {
            Map<String, List<JSONObject>> mapBody = parseDeleteAddData(objectChangeLogPO.getChangeBody());

            if (mapBody == null) return;

            List<JSONObject> delList = new ArrayList<>();
            mapBody.get("del").forEach(row -> {
                if (KPStringUtil.isNotEmpty(row.getString("filePath"))) {
                    delList.add(new KPJSONFactoryUtil(KPJsonUtil.toJson(KPJsonUtil.toJavaObject(row, FileUploadBO.class)))
                            .put("url", KPMinioUtil.getUrl(row.getString("filePath"), 1))
                            .build());
                }
            });


            List<JSONObject> addList = new ArrayList<>();
            mapBody.get("add").forEach(row -> {
                if (KPStringUtil.isNotEmpty(row.getString("filePath"))) {
                    addList.add(new KPJSONFactoryUtil(KPJsonUtil.toJson(KPJsonUtil.toJavaObject(row, FileUploadBO.class)))
                            .put("url", KPMinioUtil.getUrl(row.getString("filePath"), 1))
                            .build());
                }
            });

            objectChangeLogPO.setChangeBody("删除的数据 " + KPJsonUtil.toJsonString(delList) + "|新增的数据 " + KPJsonUtil.toJsonString(addList));
        } catch (Exception ex) {
            log.error("翻译先删后增的附件异常: {}", ex.getMessage());
        }
    }

    /**
     * 提取有效内容。
     * @author lipeng
     * 2025/11/11
     * @param changeBody  内容
     * @return java.util.Map<java.lang.String,java.util.List<com.alibaba.fastjson2.JSONObject>>
     */
    public static Map<String, List<JSONObject>> parseDeleteAddData(String changeBody) {
        if (KPStringUtil.isEmpty(changeBody)) return null;

        // 用"|"分割删除数据和新增数据部分
        String[] parts = changeBody.split("\\|");
        String deleteStr = parts.length > 0 ? parts[0] : "";
        String addStr = parts.length > 1 ? parts[1] : "";


        String deletedJson = deleteStr.replace("删除的数据", "").trim();
        String addedJson = addStr.replace("新增的数据", "").trim();

        if (deletedJson.isEmpty()) deletedJson = "[]";
        if (addedJson.isEmpty()) addedJson = "[]";

        List<JSONObject> deletedFiles = KPJsonUtil.toJavaObjectList(deletedJson, JSONObject.class);
        List<JSONObject> addedFiles = KPJsonUtil.toJavaObjectList(addedJson, JSONObject.class);

        Map<String, List<JSONObject>> body = new HashMap<>();
        body.put("del", deletedFiles);
        body.put("add", addedFiles);
        return body;
    }
}
