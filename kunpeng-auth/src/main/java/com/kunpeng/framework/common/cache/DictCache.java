package com.kunpeng.framework.common.cache;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kunpeng.framework.common.properties.RedisSecurityConstant;
import com.kunpeng.framework.enums.DeleteFalgEnum;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.dict.mapper.DictDataMapper;
import com.kunpeng.framework.modules.dict.mapper.DictTypeMapper;
import com.kunpeng.framework.modules.dict.po.DictDataPO;
import com.kunpeng.framework.modules.dict.po.DictTypePO;
import com.kunpeng.framework.modules.dict.po.DictTypeProjectPO;
import com.kunpeng.framework.modules.project.po.ProjectPO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPRedisUtil;
import com.kunpeng.framework.utils.kptool.KPServiceUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DictCache {

    private static String key = RedisSecurityConstant.AUTHENTICATION + "dictCache";

    private static DictDataMapper dictDataMapper = KPServiceUtil.getBean(DictDataMapper.class);
    private static DictTypeMapper dictTypeMapper = KPServiceUtil.getBean(DictTypeMapper.class);

    /**
     * @Author lipeng
     * @Description 根据类型查询字典内容
     * @Date 2025/7/4
     * @param projectCode 项目code
     * @param dictType
     * @return java.util.List<com.kunpeng.framework.modules.dict.po.DictDataPO>
     **/
    public static List<DictDataPO> getDictData(String projectCode, String dictType) {
        String dictTypeId = getDictTypeId(dictType);
        if (KPStringUtil.isEmpty(dictTypeId)) return new ArrayList<>();

        Map<String, List<DictDataPO>> dictDataMap = queryDictAll(projectCode);
        return dictDataMap.get(dictTypeId);
    }


    /**
     * @Author lipeng
     * @Description 根据类型批量查询字典内容
     * @Date 2025/7/4
     * @param projectCode 项目code
     * @param dictTypes
     * @return java.util.Map<java.lang.String, java.util.List < com.kunpeng.framework.modules.dict.po.DictDataPO>>
     **/
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
     * @Author lipeng
     * @Description 查询某个类型下的某个值是否存在
     * @Date 2025/7/4
     * @param projectCode 项目code
     * @param dictType
     * @param value
     * @return boolean
     **/
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
     * @Author lipeng
     * @Description 校验
     * @Date 2025/7/4
     * @param dictType 类型
     * @param value 值
     * @param fileName dictType 的中文意思
     * @return void
     **/
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


    public static void clearAll() {
        KPRedisUtil.removeBacth(key);
    }


    /**
     * @Author lipeng
     * @Description 查询所有字典数据
     * @Date 2025/7/4
     * @param
     * @return java.util.Map<java.lang.String, java.util.List < com.kunpeng.framework.modules.dict.po.DictDataPO>>
     **/
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
        if (list.size() == 0) return dictDataMap;

        KPRedisUtil.set(redisKet, KPJsonUtil.toJsonString(list), 3600);
        return list.stream().collect(Collectors.groupingBy(DictDataPO::getDictTypeId));
    }



    /**
     * @Author lipeng
     * @Description 根据dictType字典类型查询字典类型ID
     * @Date 2025/7/30
     * @param dictType
     * @return java.lang.String
     **/
    public static String getDictTypeId(String dictType) {
        if (KPStringUtil.isEmpty(dictType))  return null;

        return queryDictTypeAll().stream()
                .filter(po -> po != null && dictType.equals(po.getDictType()))
                .map(DictTypePO::getDictTypeId)
                // 若没有匹配项则返回null
                .findFirst()
                .orElse(null);
    }


    /**
     * @Author lipeng
     * @Description 根据字典类型ID查询字典类型
     * @Date 2025/7/30
     * @param dictTypeId
     * @return java.lang.String
     **/
    public static String getDictType(String dictTypeId) {
        if (KPStringUtil.isEmpty(dictTypeId))  return null;

        return queryDictTypeAll().stream()
                .filter(po -> po != null && dictTypeId.equals(po.getDictTypeId()))
                .map(DictTypePO::getDictType)
                // 若没有匹配项则返回null
                .findFirst()
                .orElse(null);
    }


    /**
     * @Author lipeng
     * @Description 根据dictType字典类型集合查询字典类型ID集合
     * @Date 2025/7/30
     * @param dictTypes
     * @return java.util.List<java.lang.String>
     **/
    private static List<String> getDictTypeIdList(List<String> dictTypes) {
        if (KPStringUtil.isEmpty(dictTypes))  return null;


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
     * @Author lipeng
     * @Description 查询所有字段类型
     * @Date 2025/7/30
     * @return java.util.List<java.lang.String>
     **/
    private static List<DictTypePO> queryDictTypeAll() {
        String redisKet = key + ":dictType";
        if (KPRedisUtil.hasKey(redisKet)) return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(redisKet), DictTypePO.class);
        
        List<DictTypePO> list = dictTypeMapper.selectList(Wrappers.lambdaQuery(DictTypePO.class).eq(DictTypePO::getStatus, YesNoEnum.YES.code()));
        if (list.size() == 0) return new ArrayList<>();

        KPRedisUtil.set(redisKet, KPJsonUtil.toJsonString(list), 3700);
        return list;
    }
}
