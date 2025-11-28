package com.kp.framework.utils.kptool;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.kp.framework.constant.MinioConstant;
import com.kp.framework.exception.KPServiceException;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author lipeng
 * @Description 操作EasyExcel工具类
 * @Date 2021/1/6 18:02
 * @Param
 * @return
 **/
@UtilityClass
public final class KPEasyExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(KPEasyExcelUtil.class);


    /**
     * @Author lipeng
     * @Description 根据模板导出Excel
     * @Date 2021/1/11 14:25
     * @param filename 文件名
     * @param tempLatePath tempLatePath 模板路径
     * @param list 导出内容
     * @return void
     **/
    public static final void exportByTemplate(String filename, String tempLatePath, List<?> list) {
        if (filename.indexOf("/") != -1)
            filename = filename.substring(filename.lastIndexOf("/") + 1);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            // 内容的策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short) 16);
            contentWriteCellStyle.setWriteFont(contentWriteFont);

            contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
            contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
            contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
            contentWriteCellStyle.setBottomBorderColor(IndexedColors.BLACK1.index);
            contentWriteCellStyle.setLeftBorderColor(IndexedColors.BLACK1.index);
            contentWriteCellStyle.setRightBorderColor(IndexedColors.BLACK1.index);
            contentWriteCellStyle.setTopBorderColor(IndexedColors.BLACK1.index);

            // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(null, contentWriteCellStyle);


            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType(new Tika().detect(filename));
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            filename = URLEncoder.encode(filename, "utf-8");


            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
//            response.setHeader("filename", URLEncoder.encode(filename.substring(0, filename.indexOf(".")), "utf-8"));
            ExcelWriter excelWriter = null;
//          ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(tempLatePath).registerWriteHandler(horizontalCellStyleStrategy).build();

            if (filename.substring(filename.indexOf("."), filename.length()).equalsIgnoreCase(".xls"))
                excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(tempLatePath).excelType(ExcelTypeEnum.XLS).registerWriteHandler(horizontalCellStyleStrategy).build();
            if (filename.substring(filename.indexOf("."), filename.length()).equalsIgnoreCase(".xlsx"))
                excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(tempLatePath).excelType(ExcelTypeEnum.XLSX).registerWriteHandler(horizontalCellStyleStrategy).build();


            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(list, writeSheet);
            excelWriter.finish();
        } catch (IOException e) {
            logger.info("[导出Excel失败]" + e.getMessage());
        }
    }


    /**
     * @Author lipeng
     * @Description 根据模板导出Excel
     * @Date 2021/1/11 14:25
     * @param filename 文件名
     * @param inputStream 模版流文件
     * @param list 导出内容
     * @return void
     **/
    public static final void exportByTemplate(String filename, InputStream inputStream, List<?> list) {
        if (filename.indexOf("/") != -1)
            filename = filename.substring(filename.lastIndexOf("/") + 1);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            // 内容的策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short) 16);
            contentWriteCellStyle.setWriteFont(contentWriteFont);

            contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
            contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
            contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
            contentWriteCellStyle.setBottomBorderColor(IndexedColors.BLACK1.index);
            contentWriteCellStyle.setLeftBorderColor(IndexedColors.BLACK1.index);
            contentWriteCellStyle.setRightBorderColor(IndexedColors.BLACK1.index);
            contentWriteCellStyle.setTopBorderColor(IndexedColors.BLACK1.index);

            // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(null, contentWriteCellStyle);


            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType(new Tika().detect(filename));
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系

//            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
//            response.setHeader("filename", URLEncoder.encode(filename.substring(0, filename.indexOf(".")), "utf-8"));
            ExcelWriter excelWriter = null;
            if (filename.substring(filename.indexOf("."), filename.length()).equalsIgnoreCase(".xls"))
                excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).excelType(ExcelTypeEnum.XLS).registerWriteHandler(horizontalCellStyleStrategy).build();
            if (filename.substring(filename.indexOf("."), filename.length()).equalsIgnoreCase(".xlsx"))
                excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).excelType(ExcelTypeEnum.XLSX).registerWriteHandler(horizontalCellStyleStrategy).build();

            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(list, writeSheet);
            excelWriter.finish();
        } catch (IOException e) {
            logger.info("[导出Excel失败]" + e.getMessage());
        }
    }


    /**
     * @Author lipeng
     * @Description 根据模板导出，适用任何情况
     * @Date 2022/10/8 10:39
     * @param filename
     * @param inputStream
     * @param obj
     * @return void
     **/
    public static final void exportByTemplate(String filename, InputStream inputStream, Object... obj) {
        if (filename.indexOf("/") != -1)
            filename = filename.substring(filename.lastIndexOf("/") + 1);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType(new Tika().detect(filename));
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
//            response.setHeader("filename", URLEncoder.encode(filename.substring(0, filename.indexOf(".")), "utf-8"));
            ExcelWriter excelWriter = null;
            if (filename.substring(filename.indexOf("."), filename.length()).equalsIgnoreCase(".xls"))
                excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).excelType(ExcelTypeEnum.XLS).build();
            if (filename.substring(filename.indexOf("."), filename.length()).equalsIgnoreCase(".xlsx"))
                excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(inputStream).excelType(ExcelTypeEnum.XLSX).build();

            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            //开启自动换行， 自动换行表示每次写入一条数据都会重新生成一行空行
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            for (int i = 0; i < obj.length; i++) {
                excelWriter.fill(obj[i], fillConfig, writeSheet);
            }

            excelWriter.finish();
        } catch (IOException e) {
            logger.info("[导出Excel失败]" + e.getMessage());
        }
    }


    /**
     * @Author lipeng
     * @Description 根据模板导出（模板在minio中）
     * @Date 2022/7/7 10:36
     * @param bucketName 桶名称
     * @param fileName 文件名
     * @param list 导出内容
     * @return void
     **/
    public static void minioExprotByTemplate(String bucketName, String fileName, List<?> list) {
        InputStream inputStream = null;
        try {
            inputStream = KPMinioUtil.getObject(bucketName, MinioConstant.TEMPLATE + fileName);
            KPEasyExcelUtil.exportByTemplate(fileName, inputStream, list);
        } catch (Exception e) {
            throw new KPServiceException("导出异常，请检查模板是否存在！");
        } finally {
            try {
                inputStream.close();
            } catch (Exception ex) {
            }
            System.gc();
        }
    }

    /**
     * @Author lipeng
     * @Description 导出模板
     * @Date 2021/1/11 14:32
     * @Param [tempLatePath]
     * @return void
     **/
//    public static final void exportTemplate(String tempLatePath, String filename){
//        HttpServletResponse response =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
//        try {
//            Workbook workbook = WorkbookFactory.create( new FileInputStream(new File(tempLatePath)));
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/octet-stream;charset=utf-8");
//            filename = URLEncoder.encode(filename, "utf-8");
//            response.setHeader("Content-disposition", "attachment;filename=" + filename + ".xls");
//            response.setHeader("filename", filename + ".xls");
//            workbook.write(response.getOutputStream());
//        }catch (Exception e){
//            logger.info("[导出Excel模板失败]" + e.getMessage());
//        }
//    }
}
