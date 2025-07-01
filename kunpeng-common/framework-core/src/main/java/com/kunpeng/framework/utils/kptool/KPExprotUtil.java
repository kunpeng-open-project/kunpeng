package com.kunpeng.framework.utils.kptool;

public final class KPExprotUtil {


//    /**
//     * @Author lipeng
//     * @Description 根据模板导出（模板在minio中）
//     * @Date 2022/7/7 10:36
//     * @param bucketName 桶名称
//     * @param fileName 文件名
//     * @param list 导出内容
//     * @return void
//     **/
//    public final static void minioExprotByTemplate(String bucketName, String fileName, List<?> list){
//        InputStream inputStream = null;
//        try {
//            inputStream = KPMinioUtil.getObject(bucketName, MinioConstant.TEMPLATE + fileName);
//            KPEasyExcelUtil.exportByTemplate(fileName, inputStream, list);
//        } catch (Exception e) {
//            throw new KPServiceException("导出异常，请检查模板是否存在！");
//        }finally {
//            try {
//                inputStream.close();
//            }catch (Exception ex){}
//            System.gc();
//        }
//    }
}
