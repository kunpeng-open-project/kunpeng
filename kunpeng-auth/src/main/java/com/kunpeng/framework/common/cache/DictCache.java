package com.kunpeng.framework.common.cache;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kunpeng.framework.common.properties.RedisSecurityConstant;
import com.kunpeng.framework.enums.DeleteFalgEnum;
import com.kunpeng.framework.enums.YesNoEnum;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.modules.dict.mapper.DictDataMapper;
import com.kunpeng.framework.modules.dict.po.DictDataPO;
import com.kunpeng.framework.modules.dict.po.DictTypePO;
import com.kunpeng.framework.utils.kptool.KPJsonUtil;
import com.kunpeng.framework.utils.kptool.KPRedisUtil;
import com.kunpeng.framework.utils.kptool.KPServiceUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DictCache {

    private static String key = RedisSecurityConstant.AUTHENTICATION + "dictCache";

    private static DictDataMapper dictDataMapper = KPServiceUtil.getBean(DictDataMapper.class);


    /**
     * @Author lipeng
     * @Description 根据类型查询字典内容
     * @Date 2025/7/4
     * @param dictType
     * @return java.util.List<com.kunpeng.framework.modules.dict.po.DictDataPO>
     **/
    public static List<DictDataPO> getDictData(String dictType) {
        Map<String, List<DictDataPO>> dictDataMap = queryDictAll();
        return dictDataMap.get(dictType);
    }


    /**
     * @Author lipeng
     * @Description 根据类型批量查询字典内容
     * @Date 2025/7/4
     * @param dictTypes
     * @return java.util.Map<java.lang.String, java.util.List < com.kunpeng.framework.modules.dict.po.DictDataPO>>
     **/
    public static Map<String, List<DictDataPO>> getDictData(List<String> dictTypes) {
        Map<String, List<DictDataPO>> dictDataMap = queryDictAll();

        Map<String, List<DictDataPO>> map = new HashMap<>();
        dictTypes.forEach(dictType -> {
            if (dictDataMap.get(dictType) != null) map.put(dictType, dictDataMap.get(dictType));

        });
        return map;
    }


    /**
     * @Author lipeng
     * @Description 查询某个类型下的某个值是否存在
     * @Date 2025/7/4
     * @param dictType
     * @param value
     * @return boolean
     **/
    public static boolean getIsExist(String dictType, String value) {
        Map<String, List<DictDataPO>> dictDataMap = queryDictAll();
        List<DictDataPO> dataList = dictDataMap.get(dictType);
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
    public static void verify(String dictType, String value, String fileName) {
        KPVerifyUtil.notNull(value, fileName + "不能为空");
        Map<String, List<DictDataPO>> dictDataMap = queryDictAll();
        List<DictDataPO> dataList = dictDataMap.get(dictType);
        if (KPStringUtil.isEmpty(dataList)) throw new KPServiceException(fileName + "不存在, 请检查字典表中是否存在");

        for (DictDataPO dictDataPO : dataList) {
            if (dictDataPO.getValue().equals(value)) return;
        }
        throw new KPServiceException(fileName + "不存在, 请检查字典表中是否存在");
    }


    public static void clearAll() {
        KPRedisUtil.remove(key);
    }


    /**
     * @Author lipeng
     * @Description 查询所有字典数据
     * @Date 2025/7/4
     * @param
     * @return java.util.Map<java.lang.String, java.util.List < com.kunpeng.framework.modules.dict.po.DictDataPO>>
     **/
    private static Map<String, List<DictDataPO>> queryDictAll() {
        Map<String, List<DictDataPO>> dictDataMap = new HashMap<>();
        if (KPRedisUtil.hasKey(key)) return KPJsonUtil.toJavaObjectList(KPRedisUtil.get(key), DictDataPO.class).stream().collect(Collectors.groupingBy(DictDataPO::getDictType));

        MPJLambdaWrapper<DictDataPO> wrapper = new MPJLambdaWrapper<DictDataPO>().selectAll(DictDataPO.class).leftJoin(DictTypePO.class, "dictType", on -> on.eq(DictTypePO::getDictType, DictDataPO::getDictType).eq(DictTypePO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())).disableSubLogicDel().eq(DictTypePO::getStatus, YesNoEnum.YES.code()).eq(DictDataPO::getStatus, YesNoEnum.YES.code()).orderByAsc(DictDataPO::getSort);


        List<DictDataPO> list = dictDataMapper.selectJoinList(DictDataPO.class, wrapper);
        if (list.size() == 0) return dictDataMap;

        KPRedisUtil.set(key, KPJsonUtil.toJsonString(list), 3600);
        return list.stream().collect(Collectors.groupingBy(DictDataPO::getDictType));
    }
}
