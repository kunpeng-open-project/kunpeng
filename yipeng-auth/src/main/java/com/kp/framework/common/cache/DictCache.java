package com.kp.framework.common.cache;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.common.properties.RedisSecurityConstant;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.enums.YesNoEnum;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.modules.dict.mapper.DictDataMapper;
import com.kp.framework.modules.dict.mapper.DictTypeMapper;
import com.kp.framework.modules.dict.po.DictDataPO;
import com.kp.framework.modules.dict.po.DictTypePO;
import com.kp.framework.modules.dict.po.DictTypeProjectPO;
import com.kp.framework.modules.project.po.ProjectPO;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import com.kp.framework.utils.kptool.KPServiceUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DictCache {

    private static final String key = RedisSecurityConstant.AUTHENTICATION + "dictCache";
    private static final DictDataMapper dictDataMapper = KPServiceUtil.getBean(DictDataMapper.class);
    private static final DictTypeMapper dictTypeMapper = KPServiceUtil.getBean(DictTypeMapper.class);


    /**
     * 根据类型查询字典内容。
     * @author lipeng
     * 2025/7/4
     * @param projectCode 项目code
     * @param dictType 字典类型
     * @return java.util.List<com.kp.framework.modules.dict.po.DictDataPO>
     */
    public static List<DictDataPO> getDictData(String projectCode, String dictType) {
        String dictTypeId = getDictTypeId(dictType);
        if (KPStringUtil.isEmpty(dictTypeId)) return new ArrayList<>();

        Map<String, List<DictDataPO>> dictDataMap = queryDictAll(projectCode);
        return dictDataMap.get(dictTypeId);
    }

    /**
     * 根据类型批量查询字典内容。
     * @author lipeng
     * 2025/7/4
     * @param projectCode 项目code
     * @param dictTypes 字典类型
     * @return java.util.Map<java.lang.String,java.util.List<com.kp.framework.modules.dict.po.DictDataPO>>
     */
    public static Map<String, List<DictDataPO>> getDictData(String projectCode, List<String> dictTypes) {
        Map<String, List<DictDataPO>> dictDataMap = queryDictAll(projectCode);

        List<String> dictTypeIds = getDictTypeIdList(dictTypes);
        if (KPStringUtil.isEmpty(dictTypeIds)) return dictDataMap;

        Map<String, List<DictDataPO>> map = new HashMap<>();
        dictTypeIds.forEach(dictTypeId -> {
            if (dictDataMap.get(dictTypeId) != null) map.put(getDictType(dictTypeId), dictDataMap.get(dictTypeId));

        });
        return map;
    }

    /**
     * 查询某个类型下的某个值是否存在。
     * @author lipeng
     * 2025/7/4
     * @param projectCode 项目code
     * @param dictType 字典类型
     * @param value 字典键值
     * @return boolean
     */
    public static boolean getIsExist(String projectCode, String dictType, String value) {
        String dictTypeId = getDictTypeId(dictType);
        if (KPStringUtil.isEmpty(dictTypeId)) return false;

        Map<String, List<DictDataPO>> dictDataMap = queryDictAll(projectCode);
        List<DictDataPO> dataList = dictDataMap.get(dictTypeId);
        if (KPStringUtil.isEmpty(dataList)) return false;

        for (DictDataPO dictDataPO : dataList) {
            if (dictDataPO.getValue().equals(value)) return true;
        }
        return false;
    }

    /**
     * 校验指定的内容是否存在。
     * @author lipeng
     * 2025/7/4
     * @param projectCode 项目code
     * @param dictType 字典类型
     * @param value 字典键值
     * @param fileName 校验提示文件名
     */
    public static void verify(String projectCode, String dictType, String value, String fileName) {
        String dictTypeId = getDictTypeId(dictType);
        if (KPStringUtil.isEmpty(dictTypeId)) throw new KPServiceException(dictType + "不存在, 请检查字典表中是否存在");

        KPVerifyUtil.notNull(value, fileName + "不能为空");
        Map<String, List<DictDataPO>> dictDataMap = queryDictAll(projectCode);
        List<DictDataPO> dataList = dictDataMap.get(dictTypeId);
        if (KPStringUtil.isEmpty(dataList)) throw new KPServiceException(fileName + "不存在, 请检查字典表中是否存在");

        for (DictDataPO dictDataPO : dataList) {
            if (dictDataPO.getValue().equals(value)) return;
        }
        throw new KPServiceException(fileName + "不存在, 请检查字典表中是否存在");
    }

    /**
     * 清空缓存。
     * @author lipeng
     * 2025/7/4
     */
    public static void clearAll() {
        KPRedisUtil.removeBacth(key);
    }

    /**
     * 查询所有字典数据。
     * @author lipeng
     * 2025/7/4
     * @param projectCode 项目code
     * @return java.util.Map<java.lang.String,java.util.List<com.kp.framework.modules.dict.po.DictDataPO>>
     */
    private static Map<String, List<DictDataPO>> queryDictAll(String projectCode) {
        String redisKet = key + ":" + projectCode;
        Map<String, List<DictDataPO>> dictDataMap = new HashMap<>();
        if (KPRedisUtil.hasKey(redisKet)) return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKet), DictDataPO.class).stream().collect(Collectors.groupingBy(DictDataPO::getDictTypeId));

        ProjectPO projectPO = ProjectCache.getProjectByCode(projectCode);
        if (KPStringUtil.isEmpty(projectPO)) return dictDataMap;

        MPJLambdaWrapper<DictDataPO> wrapper = new MPJLambdaWrapper<DictDataPO>()
                .selectAll(DictDataPO.class)
                .leftJoin(DictTypePO.class, on -> on
                        .eq(DictTypePO::getDictTypeId, DictDataPO::getDictTypeId)
                        .eq(DictTypePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                        .eq(DictTypePO::getStatus, YesNoEnum.YES.code())
                )
                .leftJoin(DictTypeProjectPO.class, on -> on
                        .eq(DictTypeProjectPO::getDictTypeId, DictTypePO::getDictTypeId)
                        .eq(DictTypeProjectPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .eq(DictTypeProjectPO::getProjectId, projectPO.getProjectId())
                .eq(DictDataPO::getStatus, YesNoEnum.YES.code())
                .orderByAsc(DictDataPO::getSort);


        List<DictDataPO> list = dictDataMapper.selectJoinList(DictDataPO.class, wrapper);
        if (KPStringUtil.isEmpty(list)) return dictDataMap;

        KPRedisUtil.set(redisKet, KPJsonUtil.toJsonString(list), 3600);
        return list.stream().collect(Collectors.groupingBy(DictDataPO::getDictTypeId));
    }

    /**
     * 根据dictType字典类型查询字典类型ID。
     * @author lipeng
     * 2025/7/30
     * @param dictType 字典类型
     * @return java.lang.String
     */
    public static String getDictTypeId(String dictType) {
        if (KPStringUtil.isEmpty(dictType)) return null;

        return queryDictTypeAll().stream()
                .filter(po -> po != null && dictType.equals(po.getDictType()))
                .map(DictTypePO::getDictTypeId)
                // 若没有匹配项则返回null
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据字典类型ID查询字典类型。
     * @author lipeng
     * 2025/7/30
     * @param dictTypeId 字典类型ID
     * @return java.lang.String
     */
    public static String getDictType(String dictTypeId) {
        if (KPStringUtil.isEmpty(dictTypeId)) return null;

        return queryDictTypeAll().stream()
                .filter(po -> po != null && dictTypeId.equals(po.getDictTypeId()))
                .map(DictTypePO::getDictType)
                // 若没有匹配项则返回null
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据dictType字典类型集合查询字典类型ID集合。
     * @author lipeng
     * 2025/7/30
     * @param dictTypes 字典类型集合
     * @return java.util.List<java.lang.String>
     */
    private static List<String> getDictTypeIdList(List<String> dictTypes) {
        if (KPStringUtil.isEmpty(dictTypes)) return null;


        Map<String, String> dictTypeToIdMap = queryDictTypeAll().stream()
                .filter(po -> po != null && po.getDictType() != null) // 过滤无效数据
                .collect(Collectors.toMap(
                        DictTypePO::getDictType,
                        DictTypePO::getDictTypeId,
                        (existing, replacement) -> existing // 处理重复key，保留第一个
                ));

        // 遍历输入集合，批量获取对应的dictTypeId
        return dictTypes.stream()
                .map(dictType -> dictTypeToIdMap.get(dictType))
                .collect(Collectors.toList());
    }

    /**
     * 查询所有字段类型。
     * @author lipeng
     * 2025/7/30
     * @return java.util.List<com.kp.framework.modules.dict.po.DictTypePO>
     */
    private static List<DictTypePO> queryDictTypeAll() {
        String redisKet = key + ":dictType";
        if (KPRedisUtil.hasKey(redisKet)) return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKet), DictTypePO.class);

        List<DictTypePO> list = dictTypeMapper.selectList(Wrappers.lambdaQuery(DictTypePO.class).eq(DictTypePO::getStatus, YesNoEnum.YES.code()));
        if (KPStringUtil.isEmpty(list)) return new ArrayList<>();

        KPRedisUtil.set(redisKet, KPJsonUtil.toJsonString(list), 3700);
        return list;
    }
}
