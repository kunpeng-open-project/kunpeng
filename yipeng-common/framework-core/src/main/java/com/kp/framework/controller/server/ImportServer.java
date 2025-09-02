package com.kp.framework.controller.server;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.jfzh.framework.constant.RedisConstant;
//import com.jfzh.framework.entity.po.ImportCoursePO;
//import com.jfzh.framework.exception.KPServiceException;
//import com.jfzh.framework.utils.kptool.KPJSONUtil;
//import com.jfzh.framework.utils.kptool.KPRedisUtil;
//import com.jfzh.framework.utils.kptool.KPStringUtil;
//import com.jfzh.framework.utils.kptool.KPVerifyUtil;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ImportServer {
//
//    /**
//     * @Author lipeng
//     * @Description 导入结果查询
//     * @Date 2022/6/20 19:55
//     * @param identification
//     * @return java.lang.Object
//     **/
//    public Object doImportResult(String identification) {
//        KPVerifyUtil.notNull(identification, "请输入唯一标识！");
//        String row = KPRedisUtil.get(RedisConstant.IMPORT + ":" + identification + ":state");
//        if (KPStringUtil.isEmpty(row))
//            throw new KPServiceException("未查询到结果！");
//        return KPJSONUtil.toJavaObject(row, ImportCoursePO.class);
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 导入失败内容导出
//     * @Date 2022/6/20 20:28
//     * @param jsonObject
//     * @return void
//     **/
//    public void doImportFailList(JSONObject jsonObject) {
////        KPVerifyUtil.notNull(jsonObject.getString("identification"), "请输入唯一标识！");
////        KPVerifyUtil.notNull(jsonObject.getString("errorFilePath"), "请输入异常文件路径！");
////        List<Object> list = KPRedisUtil.getList(RedisConstant.IMPORT + ":" + jsonObject.getString("identification") + ":errorList");
////        if(list==null || list.size()==0) return;
////
////        InputStream inputStream = null;
////        try {
////            inputStream = KPMinioUtil.getObject(MinioConstant.FIREFIGHTING_BUCKET_NAME, jsonObject.getString("errorFilePath"));
////            KPEasyExcelUtil.exportByTemplate(jsonObject.getString("errorFilePath"), inputStream, list);
////        } catch (Exception e) {}finally {
////            try {
////                inputStream.close();
////            }catch (Exception ex){}
////        }
//    }
//}
