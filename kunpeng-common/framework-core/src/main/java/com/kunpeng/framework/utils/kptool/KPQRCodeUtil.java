package com.kunpeng.framework.utils.kptool;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.kunpeng.framework.exception.KPServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author lipeng
 * @Description 生成二维码
 * @Date 2022/10/13 10:36
 * @return
 **/
@Slf4j
public final class KPQRCodeUtil {


    private BufferedImage bufferedImage; //二维码
    private String body; //二维码的内容
    private String imageType = ImgUtil.IMAGE_TYPE_PNG; //二维码类型
    private Integer width = 300;//二维码的长
    private Integer height = 300;//二维码的宽
    private Integer margin = 1; // 二维码的边距
    private Color foreColor; // 前景色
    private Color backColor; // 背景色
    private String logoUrl;//logo url地址
    private String logoLocalhostPath;//logo url地址


    private KPQRCodeUtil(){}
    public KPQRCodeUtil(String body){
        this.body=body;
    }
    public KPQRCodeUtil imageType(String imageType){
        this.imageType = imageType;
        return this;
    }
    public KPQRCodeUtil width(Integer width){
        this.width = width;
        return this;
    }
    public KPQRCodeUtil height(Integer height){
        this.height = height;
        return this;
    }
    public KPQRCodeUtil margin(Integer margin){
        this.margin = margin;
        return this;
    }
    public KPQRCodeUtil foreColor(Color foreColor){
        this.foreColor = foreColor;
        return this;
    }
    public KPQRCodeUtil backColor(Color backColor){
        this.backColor = backColor;
        return this;
    }
    public KPQRCodeUtil logoUrl(String logoUrl){
        this.logoUrl = logoUrl;
        return this;
    }
    public KPQRCodeUtil logoLocalhostPath(String logoLocalhostPath){
        this.logoLocalhostPath = logoLocalhostPath;
        return this;
    }




    /**
     * @Author lipeng
     * @Description 生成二维码
     * @Date 2022/10/13 10:36
     * @param
     * @return com.daoben.framework.util.KPQRCodeUtil
     **/
    public KPQRCodeUtil generate(){
        //设置二维码的大小
        QrConfig config = new QrConfig(this.width, this.height);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(this.margin);
        // 设置前景色，既二维码颜色（青色）
        if (foreColor != null ) config.setForeColor(this.foreColor);
        // 设置背景色（灰色）
        if (backColor != null) config.setBackColor(this.backColor);
        //设置logo
        String filePath = null;
        if (KPStringUtil.isNotEmpty(this.logoUrl)){
            String[] bb = this.logoUrl.split("[?]")[0].split("/");
            //得到最后一个分隔符后的名字
            String fileName = bb[bb.length - 1];
            //保存到本地的路径
            filePath = System.getProperty("user.dir") + "/" +fileName;
            config.setImg(KPFileUtil.getNetUrlHttp(this.logoUrl, filePath));
        }
        if (KPStringUtil.isNotEmpty(this.logoLocalhostPath)) config.setImg(this.logoLocalhostPath);

        this.bufferedImage = QrCodeUtil.generate(this.body, config);
        if (KPStringUtil.isNotEmpty(filePath)) KPFileUtil.deleteFile(filePath);
//        String finalFilePath = filePath;
//        new Thread(()->{
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {}
//            if (KPStringUtil.isNotEmpty(finalFilePath)) KPFileUtil.deleteFile(finalFilePath);
//        }).start();
        return this;
    }

    /**
     * @Author lipeng
     * @Description 返回二维码图片
     * @Date 2022/10/13 10:36
     * @param
     * @return java.awt.image.BufferedImage
     **/
    public BufferedImage getBufferedImage(){
        return this.bufferedImage;
    }


    /**
     * @Author lipeng
     * @Description 生成并且下载二维码
     * @Date 2022/10/13 10:37
     * @param downLoadFileNme
     * @return void
     **/
    public void downLoad(String downLoadFileNme){
        this.generate();
        HttpServletResponse response =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        KPIOUtil.downLoad(downLoadFileNme + "." + this.imageType, response);
        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(bufferedImage, this.imageType, os);
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new KPServiceException("生成二维码异常！"+ e.getMessage());
        }
    }

}
