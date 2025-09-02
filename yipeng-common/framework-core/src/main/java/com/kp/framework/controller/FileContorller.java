package com.kp.framework.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.controller.server.FileService;
import com.kp.framework.entity.bo.FileUploadBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.entity.po.FilePO;
import com.kp.framework.utils.kptool.KPMinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/* *
 * @Author 李鹏
 * @Description 文件相关接口
 * @Date 2020/5/18
 **/
@Api(value = "文件相关接口", tags = "文件相关接口")
@ApiSupport(author = "李鹏", order = -9998)
@RestController
@RequestMapping("/minio/file")
public class FileContorller {

    @Autowired
    private FileService fileService;


    /**
     * @Author lipeng
     * @Description 文件上传
     * @Date 2022/1/6 14:44
     * @param file 上传的文件
     * @return com.framework.core.entity.bo.ResultBO
     **/
    @ApiOperation(value = "单个文件上传")
    @ApiParam(name = "file", required = true)
    @PostMapping(value = "/upload")
    @CrossOrigin
    public KPResult<FileUploadBO> upload(MultipartFile file) {
        return KPResult.success(fileService.upload(file));
    }


    /**
     * @Author lipeng
     * @Description 文件上传
     * @Date 2022/1/6 14:44
     * @param files 上传的文件
     * @return com.framework.core.entity.bo.ResultBO
     **/
    @ApiOperation(value = "批量文件上传")
    @ApiImplicitParam(name = "files", value = "多个文件", allowMultiple = true, dataType = "__file")
    @PostMapping(value = "/upload/batch")
    public KPResult<List<FileUploadBO>> uploadByBatch(MultipartFile[] files) {
        return KPResult.success(fileService.uploadByBatch(files));
    }


    @ApiOperation(value = "单个图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", required = true, dataType = "__file"),
            @ApiImplicitParam(name = "size", value = "图片大小限制 长X宽 例如 1024x300 【size 和 ratio 二选一】", required = false),
            @ApiImplicitParam(name = "ratio", value = "图片比例限制 长:宽 例如 1:1 【size 和 ratio 二选一】", required = false),
    })
    @PostMapping(value = "/upload/image")
    @CrossOrigin
    @ResponseBody
    public KPResult<FileUploadBO> uploadImage(MultipartFile file, String size, String ratio) {
        fileService.imageFormatVerify(file, size, ratio);
        return KPResult.success(fileService.upload(file));
    }
//
//
//
//    @ApiOperation(value = "上传模板以及默认文件，后端开发人员使用，严禁前端和非后端人员使用")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "file", required = true, dataType = "__file"),
//            @ApiImplicitParam(name = "bucketName", required = true, allowableValues = MinioConstant.bucketNames, allowMultiple = true),
//            @ApiImplicitParam(name = "folder", value = "文件夹名称", allowableValues = "template/,default/", allowMultiple = true)
//    })
//    @PostMapping(value = "/upload/template")
//    @CrossOrigin
//    @ResponseBody
//    public ResultBO uploadTemplate(MultipartFile file, String bucketName, String folder) {
//        return lpServiceMonitor.upload(file, bucketName, folder);
//    }
//
//
//
//

    /**
     * @Author lipeng
     * @Description 文件下载
     * @Date 2022/3/23 21:22
     * @param filePO
     * @return void
     **/
    @ApiOperation(value = "文件下载")
    @PostMapping(value = "/downLoad")
    @KPVerifyNote
    public void downLoad(@RequestBody FilePO filePO) {
        fileService.downLoad(filePO);
    }

    @ApiOperation(value = "设置桶权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bucketName", required = true, value = "桶名称"),
            @ApiImplicitParam(name = "isPublic", value = "是否公有桶 true 公有桶 false 私有桶 ", required = true, dataType = "boolean", allowableValues = "true,false", defaultValue = "false")
    })
    @PostMapping(value = "/set/image")
    @CrossOrigin
    @ResponseBody
    public KPResult<FileUploadBO> setBucketAccessPolicy(String bucketName, Boolean isPublic) {
        KPMinioUtil.setBucketAccessPolicy(bucketName, isPublic);
        return KPResult.success();
    }
}
