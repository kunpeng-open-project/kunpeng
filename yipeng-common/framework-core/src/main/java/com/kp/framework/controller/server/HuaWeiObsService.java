package com.kp.framework.controller.server;
//
//
//import com.jfzh.framework.constant.HuaWeiObsConstans;
//import com.jfzh.framework.entity.po.ObsFile;
//import com.jfzh.framework.exception.KPServiceException;
//import com.jfzh.framework.utils.HuaWeiObsUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.InputStream;
//
//@Service
//public class HuaWeiObsService {
//
//    @Value("${env}")
//    private String env;
//
//    public ObsFile uploadAloneFile(MultipartFile file){
//        if(file == null)
//            throw new KPServiceException("请选择上传的文件！");
//        if (file.isEmpty() || file.getSize() == 0)
//            throw new KPServiceException("文件为空！");
//
//        String fileName = file.getOriginalFilename();
//        InputStream inputStream = null;
//        try {
//            inputStream = file.getInputStream();
//            ObsFile obsFile = HuaWeiObsUtils.obsFileUpload(HuaWeiObsConstans.TEMP_NAME, inputStream, null, fileName, env);
//            obsFile.setPathUrl(HuaWeiObsUtils.quertTempUrl(HuaWeiObsConstans.TEMP_NAME, obsFile.getServerFileName()));
//            return obsFile;
//        }catch (Exception ex){
//            throw new KPServiceException(file.getOriginalFilename().concat(" 上传失败！ 失败原因: ".concat(ex.getMessage()) ));
//        }finally {
//            try {
//                inputStream.close();
//            }catch (Exception ex){}
//            System.gc();
//        }
//    }
//}
