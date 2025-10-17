package com.kp.framework.util;

import com.alibaba.fastjson2.JSONArray;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.kp.framework.utils.kptool.KPStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lipeng
 * @Description 结果校验器
 * @Date 2025/7/31
 * @return
 **/
public class FeignVerifyUtil {
//    /**
//     * @Description 校验list 并且返回list
//     * @Date 2022/6/23 17:52
//     * @param listResult
//     * @param interfaceName
//     * @return void
//     **/
//    public final List<T> verifyListByList(Result<List<T>> listResult, String interfaceName){
//        if (!listResult.getCode().equals(0))
//            throw new ServiceException(DBStringUtil.format("调用mom接口-{0}异常！", interfaceName));
//        List<?> list = listResult.getData();
//        if (list == null ||  list.size()==0)
//            throw new ServiceException(DBStringUtil.format("调用mom接口-{0}未查询到有效信息！", interfaceName));
//        return listResult.getData();
//    }


//    /**
//     * @Description 校验list 并返回单条数据
//     * @Date 2022/6/23 18:08
//     * @param listResult
//     * @param interfaceName
//     * @return T
//     **/
//    public final T verifyListBySingle(Result<List<T>> listResult, String interfaceName){
//        if (!listResult.getCode().equals(0))
//            throw new ServiceException(DBStringUtil.format("调用mom接口-{0}异常！", interfaceName));
//        List<?> list = listResult.getData();
//        if (list == null ||  list.size()==0)
//            throw new ServiceException(DBStringUtil.format("调用mom接口-{0}未查询到有效信息！", interfaceName));
//
//        if (list.size()>1)
//            throw new ServiceException(DBStringUtil.format("调用mom接口-{0}查询到多条信息！", interfaceName));
//
//        return listResult.getData().get(0);
//    }

//    /**
//     * @Description 简单校验是否调用成功
//     * @Date 2022/6/23 20:17
//     * @param result
//     * @param interfaceName
//     * @return void
//     **/
//    public final static void verifySingleIsSucceed(String serviceName, KPResult result, String interfaceName) {
//        if (!result.getCode().equals(200))
//            throw new KPServiceException(KPStringUtil.format("【调用{0}】-{0}接口异常！", serviceName, interfaceName));
//    }

    /**
     * @Author lipeng
     * @Description 校验数据并返回查询到的对象
     * @Date 20254/9/01
     * @param serviceName 服务名名称
     * @param result 接口返回值
     * @param interfaceName 调用的接口名称
     * @param clazz 返回的类型
     * @return T
     **/
    public final static <T> T verifyBySingle(String serviceName, KPResult result, String interfaceName, Class<T> clazz) {
        if (!result.getCode().equals(200))
            throw new KPServiceException(KPStringUtil.format("【调用{0}】-{1}接口异常！", serviceName, interfaceName));

        return KPJsonUtil.toJavaObject(result.getData(), clazz);
    }


    /**
     * @Author lipeng
     * @Description 校验带分页的list接口
     * @Date 20254/9/01
     * @param serviceName 服务名名称
     * @param result 接口返回值
     * @param interfaceName 调用的接口名称
     * @param clazz 返回的类型
     * @return java.util.List<T>
     **/
    public static <T> List<T> verifyPageList(String serviceName, KPResult result, String interfaceName, Class<T> clazz) {
        if (!result.getCode().equals(200))
            throw new KPServiceException(KPStringUtil.format("【调用{0}】-{1}接口异常！", serviceName, interfaceName));

        JSONArray jsonArray = KPJsonUtil.toJson(result.getData()).getJSONArray("list");
        if (jsonArray == null) return new ArrayList<>(); // 若list字段不存在，返回空列表

        return KPJsonUtil.toJavaObjectList(jsonArray, clazz);
    }




    /**
     * @Author lipeng
     * @Description 校验不带分页的list接口
     * @Date 20254/9/01
     * @param serviceName 服务名名称
     * @param result 接口返回值
     * @param interfaceName 调用的接口名称
     * @param clazz 返回的类型
     * @return java.util.List<T>
     **/
    public static <T> List<T> verifyList(String serviceName, KPResult result, String interfaceName, Class<T> clazz) {
        if (!result.getCode().equals(200))
            throw new KPServiceException(KPStringUtil.format("【调用{0}】-{1}接口异常！", serviceName, interfaceName));

        JSONArray jsonArray = KPJsonUtil.toJson(result).getJSONArray("data");
        if (jsonArray == null) return new ArrayList<>(); // 若list字段不存在，返回空列表

        return KPJsonUtil.toJavaObjectList(jsonArray, clazz);
    }
}
