package com.kp.framework.utils.kptool;

import cn.hutool.core.img.ImgUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.kp.framework.exception.KPServiceException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 条形码。
 * @author lipeng
 * 2022/10/13
 */
public final class KPBarCodeUtil {

    private BufferedImage bufferedImage; //条形码
    private String vaNumber;//VA 码
    private String words;//条形码下的文字
    private Integer width = 300; //条形码宽度
    private Integer height = 50; //条形码高度
    private Integer wordheight = 75; // 加文字的高度条形码
    private Color backColor = Color.WHITE; // 背景色
    private Color textColor = Color.BLACK; // 文字颜色
    private String imageType = ImgUtil.IMAGE_TYPE_PNG; //二维码类型
    private String font = "微软雅黑"; //字体


    private KPBarCodeUtil() {
    }

    public KPBarCodeUtil(String vaNumber, String words) {
        this.vaNumber = vaNumber;
        this.words = words;
    }

    public KPBarCodeUtil width(Integer width) {
        this.width = width;
        return this;
    }

    public KPBarCodeUtil height(Integer height) {
        this.height = height;
        return this;
    }

    public KPBarCodeUtil wordheight(Integer wordheight) {
        this.wordheight = wordheight;
        return this;
    }

    public KPBarCodeUtil backColor(Color backColor) {
        this.backColor = backColor;
        return this;
    }

    public KPBarCodeUtil textColor(Color textColor) {
        this.textColor = textColor;
        return this;
    }

    public KPBarCodeUtil imageType(String imageType) {
        this.imageType = imageType;
        return this;
    }

    public KPBarCodeUtil font(String font) {
        this.font = font;
        return this;
    }


    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }


    /**
     * 生成条形码。
     * @author lipeng
     * 2022/10/13
     * @return com.kp.framework.utils.kptool.KPBarCodeUtil
     */
    public KPBarCodeUtil generate() {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        Code128Writer writer = new Code128Writer();
        // 编码内容, 编码类型, 宽度, 高度, 设置参数
        BitMatrix bitMatrix = null;
        bitMatrix = writer.encode(this.vaNumber, BarcodeFormat.CODE_128, this.width, this.height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);


        // 新的图片，把带logo的二维码下面加上文字
        if (KPStringUtil.isNotEmpty(this.words)) {
            BufferedImage outImage = new BufferedImage(this.width, this.wordheight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = outImage.createGraphics();
            // 抗锯齿
            setGraphics2D(g2d);
            // 设置白色
            setColorWhite(g2d);
            // 画条形码到新的面板
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            // 画文字到新的面板
            g2d.setColor(this.textColor);
            // 字体、字型、字号
            g2d.setFont(new Font(this.font, Font.PLAIN, 18));
            //文字长度
            int strWidth = g2d.getFontMetrics().stringWidth(this.words);
            //总长度减去文字长度的一半  （居中显示）
            int wordStartX = (this.width - strWidth) / 2;
            //height + (outImage.getHeight() - height) / 2 + 12
            int wordStartY = this.height + 20;

            // 画文字
            g2d.drawString(this.words, wordStartX, wordStartY);
            g2d.dispose();
            outImage.flush();
            this.bufferedImage = outImage;
        }
        return this;
    }

    /**
     * 生成并下载条形码。
     * @author lipeng
     * 2022/10/13
     * @param downLoadFileNme 文件名
     */
    public void downLoad(String downLoadFileNme) {
        this.generate();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        KPIOUtil.setDownloadResponseHeader(downLoadFileNme + "." + this.imageType, response);
        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(bufferedImage, this.imageType, os);
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new KPServiceException("生成条形码异常！" + e.getMessage());
        }
    }

    /**
     * 简设置 Graphics2D 属性  （抗锯齿）
     * @author lipeng
     * 2022/10/13
     * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private static void setGraphics2D(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(s);
    }

    /**
     * 设置背景为白色
     * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private void setColorWhite(Graphics2D g2d) {
        g2d.setColor(this.backColor);
        //填充整个屏幕
        g2d.fillRect(0, 0, 600, 600);
        //设置笔刷
        g2d.setColor(this.backColor);
    }


    public static void main(String[] args) throws IOException {
        BufferedImage image = new KPBarCodeUtil("A80/90R8A8A", "A80/90R8A8A你大爷").textColor(Color.GREEN)
                .generate().getBufferedImage();
        ImageIO.write(image, "jpg", new File("D:/barcode.png"));
    }
}
