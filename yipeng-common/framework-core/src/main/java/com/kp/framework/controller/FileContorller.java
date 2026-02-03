package com.kp.framework.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kp.framework.annotation.verify.KPVerifyNote;
import com.kp.framework.controller.server.FileService;
import com.kp.framework.entity.bo.FileUploadBO;
import com.kp.framework.entity.bo.KPResult;
import com.kp.framework.entity.po.FilePO;
import com.kp.framework.entity.po.UploadFilePO;
import com.kp.framework.utils.kptool.KPMinioUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件相关接口。
 * @author lipeng
 * 2020/5/18
 */
@Tag(name = "文件相关接口", description = "文件相关接口")
@ApiSupport(author = "lipeng", order = 2)
@RestController
@RequestMapping("/minio/file")
public class FileContorller {

    @Autowired
    private FileService fileService;


    @Operation(summary = "单个文件上传")
    @Parameter(name = "file", description = "文件", required = true, schema = @Schema(name = "file", format = "binary"))
    @PostMapping(value = "/upload")
    @ApiOperationSupport(author = "lipeng", order = 1)
    @CrossOrigin
    public KPResult<FileUploadBO> upload(@RequestParam("file") MultipartFile file) {
        return KPResult.success(fileService.upload(file));
    }


    @Operation(summary = "批量文件上传", description = "上传多个文件。请求体应为 multipart/form-data 格式，并包含名为 'files' 的文件数组。")
    @PostMapping(value = "/upload/batch", consumes = "multipart/form-data")
    @ApiOperationSupport(author = "lipeng", order = 2)
    public KPResult<List<FileUploadBO>> uploadByBatch(@RequestParam("files") MultipartFile[] files) {
        return KPResult.success(fileService.uploadByBatch(files));
    }


    @Operation(summary = "单个图片上传")
    @Parameters({
            @Parameter(name = "file", required = true, description = "上传的图片文件", schema = @Schema(name = "file", format = "binary")),
            @Parameter(name = "size", description = "图片大小限制 长X宽 例如 1024x300 【size 和 ratio 二选一】"),
            @Parameter(name = "ratio", description = "图片比例限制 长:宽 例如 1:1 【size 和 ratio 二选一】")
    })
    @PostMapping(value = "/upload/image")
    @ApiOperationSupport(author = "lipeng", order = 3)
    @CrossOrigin
    @ResponseBody
    public KPResult<FileUploadBO> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam(value = "size", required = false) String size, @RequestParam(value = "ratio", required = false) String ratio) {
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


    @Operation(summary = "文件下载")
    @PostMapping(value = "/downLoad")
    @ApiOperationSupport(author = "lipeng", order = 4)
    @KPVerifyNote
    public void downLoad(@RequestBody FilePO filePO) {
        fileService.downLoad(filePO);
    }


    @Operation(summary = "获取临时可访问路径(有效性2小时)")
    @PostMapping(value = "/temporary/url")
    @ApiOperationSupport(author = "lipeng", order = 5)
    @KPVerifyNote
    public KPResult<String> queryTemporaryUrl(@RequestBody FilePO filePO) {
        UploadFilePO uploadFilePO = new UploadFilePO(filePO.getFilePath());
        return KPResult.success(KPMinioUtil.getUrl(uploadFilePO.getBucketName(), uploadFilePO.getFilePath(), 2));
    }

    @Operation(summary = "设置桶权限")
    @Parameters({
            @Parameter(name = "bucketName", description = "桶名称", required = true),
            @Parameter(name = "isPublic", description = "是否公有桶 true 公有桶 false 私有桶", required = true, schema = @Schema(type = "boolean", allowableValues = {"true", "false"}, defaultValue = "false"))
    })
    @PostMapping(value = "/set/image")
    @ApiOperationSupport(author = "lipeng", order = 6)
    @CrossOrigin
    @ResponseBody
    public KPResult<FileUploadBO> setBucketAccessPolicy(@RequestParam("bucketName") String bucketName, @RequestParam("isPublic") Boolean isPublic) {
        KPMinioUtil.setBucketAccessPolicy(bucketName, isPublic);
        return KPResult.success();
    }
}
