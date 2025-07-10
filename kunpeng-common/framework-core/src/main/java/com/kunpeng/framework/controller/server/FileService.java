package com.kunpeng.framework.controller.server;

import com.kunpeng.framework.constant.MinioConstant;
import com.kunpeng.framework.entity.bo.FileUploadBO;
import com.kunpeng.framework.entity.po.FilePO;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.utils.kptool.KPBigDecimalUtils;
import com.kunpeng.framework.utils.kptool.KPIOUtil;
import com.kunpeng.framework.utils.kptool.KPMinioUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import com.kunpeng.framework.utils.kptool.KPUuidUtil;
import com.kunpeng.framework.utils.kptool.KPVerifyUtil;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* *
 * @Author 李鹏
 * @Description 调用service
 * @Date 2020/5/18 22:38
 * @Param
 * @return
 **/
@Service
public class FileService {

    private static Logger log = LoggerFactory.getLogger( FileService.class );

    private long beforeTime;

    private long endTime;


//    private void before (String ServiceName, String methodName) {
//        log.info("");
//        beforeTime = System.currentTimeMillis();
//        log.info("服务名：" + ServiceName + "  方法名：" + methodName + " 执行开始");
//    }
//
//    private void end (String ServiceName, String methodName) {
//        endTime = System.currentTimeMillis();
//        long time = endTime - beforeTime;
//        log.info("服务名：" + ServiceName + "  方法名：" + methodName + " 执行结束");
//        log.info("耗时：" + time  +" 毫秒");
//    }

//    public ResultBO execute(String ServiceName, String methodName, JSONObject json) throws Exception {
//        ServiceName = ServiceName.concat("Service");
//        before(ServiceName, methodName);
//        Object ob = DBServiceUtil.getBean(ServiceName);
//        log.info("请求参数：" + json.toJSONString());
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String authorization = request.getHeader("Authorization");
////        json.put("token", authorization);
//        if (json.getInteger("pageNo")==null)  json.put("pageNo", 1);
//        if (json.getInteger("pageSize")==null)  json.put("pageSize", 999999999);
//        request.getSession().setAttribute("token", authorization);
//        ResultBO data = (ResultBO) ob.getClass().getMethod(methodName, JSONObject.class).invoke(ob, json);
//        log.info("返回报文：" + JSONObject.toJSONString(data));
//        end(ServiceName, methodName);
//        return data;
//    }
//
//
//
//    public void executeOriginal(HttpServletRequest request, HttpServletResponse response, String ServiceName, String methodName) throws Exception {
//        ServiceName = ServiceName.concat("Service");
//        before(ServiceName, methodName);
//        Object ob = DBServiceUtil.getBean(ServiceName);
//        JSONObject parament = DBRequsetUtil.getJSONParam();
//        JSONObject json = DBRequsetUtil.getJSONParam(request);
//        log.info("请求参数：" + json.toJSONString());
//        String authorization = request.getHeader("Authorization");
////        json.put("token", authorization);
//        request.getSession().setAttribute("token", authorization);
//        ob.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class, JSONObject.class).invoke(ob, request, response, json);
//        end(ServiceName, methodName);
//    }
//
//



    /**
     * @Author lipeng
     * @Description 单个上传文件
     * @Date 2022/1/7 18:10
     * @param file 文件
     **/
    public FileUploadBO upload(MultipartFile file) {
//        before("文件上传", "upload");
        if(file == null) throw new KPServiceException("请选择上传的文件！");
        if (file.isEmpty() || file.getSize() == 0) throw new KPServiceException("文件为空！");

        String fileName = file.getOriginalFilename();
        String newName = new StringBuilder()
                .append(MinioConstant.TEMPORARY_BUCKET_NAME)
                .append("/")
                .append(KPUuidUtil.getSimpleUUID())
                .append(fileName.substring(fileName.lastIndexOf(".")))
                .toString();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            KPMinioUtil.putObject(MinioConstant.TEMPORARY_BUCKET_NAME, newName, inputStream);
//            end("文件上传", "upload");
            return new FileUploadBO(file.getOriginalFilename(), file.getSize(), file.getContentType(), newName);
        }catch (Exception ex){
            throw new KPServiceException(file.getOriginalFilename().concat(" 上传失败！ 失败原因: ".concat(ex.getMessage()) ));
        }finally {
            try {
                inputStream.close();
            }catch (Exception ex){}
            System.gc();
        }
    }


    /**
     * @Author lipeng
     * @Description 批量上传文件
     * @Date 2022/1/6 14:47
     * @param files 文件集合
     **/
    public List<FileUploadBO> uploadByBatch(MultipartFile[] files) {
//        before("批量文件上传", "uploadByBatch");
        if(files == null) throw new KPServiceException("请选择上传的文件！");
        List<FileUploadBO> fileUploadBOList = new ArrayList<>();

        for(MultipartFile file : files) {
            if (file.isEmpty() || file.getSize() == 0) throw new KPServiceException("文件为空！");

            String fileName = file.getOriginalFilename();
            String newName = new StringBuilder()
                    .append(MinioConstant.TEMPORARY_BUCKET_NAME)
                    .append("/")
                    .append(KPUuidUtil.getSimpleUUID())
                    .append(fileName.substring(fileName.lastIndexOf(".")))
                    .toString();
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                KPMinioUtil.putObject(MinioConstant.TEMPORARY_BUCKET_NAME, newName, inputStream);
                //, DBMinioUtil.getObjectUrl(MinioConstant.TEMPORARY_BUCKET_NAME, newName)
                fileUploadBOList.add(new FileUploadBO(file.getOriginalFilename(), file.getSize(), file.getContentType(), newName));
            }catch (Exception ex){
                throw new KPServiceException(file.getOriginalFilename().concat(" 上传失败！ 失败原因: ".concat(ex.getMessage()) ));
            }finally {
                try {
                    inputStream.close();
                }catch (Exception ex){}
            }
        }
//        end("批量文件上传", "uploadByBatch");
        return fileUploadBOList;
    }

//
//    /**
//     * @Author lipeng
//     * @Description 上传模板
//     * @Date 2022/4/27 16:18
//     * @param file
//     * @param bucketName
//     * @return com.daoben.framework.entity.bo.ResultBO
//     **/
//    public ResultBO upload(MultipartFile file, String bucketName, String folder) {
//        DBVerifyUtil.notNull(folder, "请选择保存的文件夹！");
//        before("文件上传", "upload");
//        if(file == null)
//            throw new KPServiceException("请选择上传的文件！");
//        if (file.isEmpty() || file.getSize() == 0)
//            throw new KPServiceException("文件为空！");
//
//        String fileName = file.getOriginalFilename();
//        InputStream inputStream = null;
//        try {
//            inputStream = file.getInputStream();
//            DBMinioUtil.putObject(bucketName, folder+fileName, inputStream);
//            end("文件上传", "upload");
//            return new ResultBO().success(new FileUploadBO(file.getOriginalFilename(), file.getSize(), file.getContentType(), folder+fileName));
//        }catch (Exception ex){
//            throw new KPServiceException(file.getOriginalFilename().concat(" 上传失败！ 失败原因: ".concat(ex.getMessage()) ));
//        }finally {
//            try {
//                inputStream.close();
//            }catch (Exception ex){}
//        }
//    }


    /**
     * @Author lipeng
     * @Description 下载文件
     * @Date 2022/3/7 13:34
     * @param filePO
     * @return void
     **/
    public void downLoad(FilePO filePO) {
        try {
            InputStream inputStream = KPMinioUtil.getObject(filePO.getBucketName(), filePO.getFilePath());
            KPIOUtil.downLoad(inputStream, filePO.getFileName());
        } catch (Exception e) {
            throw new KPServiceException("文件下载失败！" + e.getMessage());
//            ResultBO bo = new ResultBO();
//            bo.error(e.getMessage());
//            KPResponseUtil.writeJson(response, bo);
        }
    }


    /**
     * @Author lipeng
     * @Description 图片校验
     * @Date 2022/3/7 13:34
     * @param file 图片
     * @param size 图片大小限制
     * @param ratio 图片比例限制
     * @return void
     **/
    public void imageFormatVerify(MultipartFile file, String size, String ratio) {
        KPVerifyUtil.twoChoiceOne(size, "图片大小限制", ratio, "图片比例限制", false);
        size = size.replaceAll("X", "x");
        size = size.replaceAll("x", "x");

        String ratios, sizes;
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            sizes = image.getWidth() + "x" + image.getHeight();
            ratios = new KPBigDecimalUtils(image.getWidth()).divide(image.getHeight(), 2).buildString();
        } catch (Exception e) {
            throw new KPServiceException("只能上传图片！");
        }

        //校验尺寸
        if (KPStringUtil.isNotEmpty(size)){
            if (!sizes.equals(size.trim())) throw new KPServiceException("图片尺寸不合规， 须" +size);
        }

        //校验比例
        if (KPStringUtil.isNotEmpty(ratio)){
            if (!ratio.contains(":")) throw new KPServiceException("ratio格式错误！");

            String[] ratioss = ratio.split(":");
            if (ratioss.length != 2) throw new KPServiceException("ratio格式错误！");

            if (!new KPBigDecimalUtils(ratioss[0]).divide(ratioss[1], 2).buildString().equals(ratios))
                throw new KPServiceException("图片比例不合规，须" + ratio);
        }
    }
}
