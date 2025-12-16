package com.kp.framework.utils.kptool.history;//package com.kp.framework.utils.kptool.history;
//
//
//import lombok.experimental.UtilityClass;
//import org.apache.tika.Tika;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import sun.misc.BASE64Decoder;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.geom.RoundRectangle2D;
//import java.awt.image.BufferedImage;
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author 李鹏
// * @Description
// **/
//@UtilityClass
//public final class KPFileUtil {
//
//    private static Logger log = LoggerFactory.getLogger(KPFileUtil.class);
//
//
//    /**
//     * @Author lipeng
//     * @Description 创建文件夹
//     * @Date 2020/9/21
//     * @Param [path]
//     * @return boolean
//     **/
//    public static boolean createFolder(String path) throws IOException {
//        File file = new File(path);
////        if (!new Tika().detect(path).equals("application/octet-stream")){
////            file = file.getParentFile();
////        }
//
//        if (!file.exists() && !file.isDirectory()) {
//            file.mkdirs();
//        }
//        return true;
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 判断是否是文件
//     * @Date 2020/10/26 14:13
//     * @Param [filePath]
//     * @return boolean
//     **/
//    public static boolean isFile(String filePath){
//        File file = new File(filePath);
//        //isFile()：如果文件存在并且是常规文件，则此方法返回true，请注意，如果文件不存在，则返回false。isDirectory()：如果路径/文件实际上是一个目录，则此方法返回true，如果路径不存在，则返回false。//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/java/check-file-directory-file-java.html
//        if(file.isFile())
//            return true;
//        return false;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 删除文件
//     * @Date 2020/10/26 14:15
//     * @Param [path, flag]
//     * @return void
//     **/
//    public static boolean deleteFile(String filePath){
//        File file = new File(filePath);
//        if(file.isFile())
//            return file.delete();
//        return false;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 删除文件夹
//     * @Date 2020/10/26 14:14
//     * @Param [path, flag] flag 是否删除子目录
//     * @return void
//     **/
//    public static void deleteFiles(File path, boolean flag){
//        File[] files = path.listFiles();
//        try {
//            //遍历文件和文件夹
//            for(File file :files){
//                //如果是文件夹，递归查找
//                if(file.isDirectory()){
//                    if(flag){
//                        deleteFiles(file, flag);
//                    }
//                    continue;
//                }
//                if(file.isFile()){
//                    if(file.delete()) log.info("删除文件成功：{}", file.getPath());
//                }
//            }
//            if(path.delete()) log.info("删除文件夹成功：{}", path.getPath());
//        }catch (Exception ex){}
//
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 保存文件
//     * @Date 2020/10/9 15:20
//     * @Param [path, fileName, base64]
//     * @return void
//     **/
//    public static String saveFileb(String path, String fileName, String base64) throws IOException {
//        if(!KPFileUtil.createFolder(path))
//            return "";
//        Files.write(Paths.get(path.concat(fileName)),
//                KPFileUtil.base64String2ByteFun(base64));
//        return path.concat(fileName);
//    }
//
//
//    /* *
//     * @Author 李鹏
//     * @Description 在线url 转byte
//     * @Date 2020/3/12 1:11
//     * @Param [path]
//     * @return byte[]
//     **/
//    public static byte[] image2byte(String path) throws IOException {
//        byte[] data = null;
//        URL url = null;
//        InputStream input = null;
//        try{
//            url = new URL(path);
//            HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
//            httpUrl.connect();
//            httpUrl.getInputStream();
//            input = httpUrl.getInputStream();
//        }catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        byte[] buf = new byte[1024];
//        int numBytesRead = 0;
//        while ((numBytesRead = input.read(buf)) != -1) {
//            output.write(buf, 0, numBytesRead);
//        }
//        data = output.toByteArray();
//        output.close();
//        input.close();
//        return data;
//    }
//
//
//    /* *
//     * @Author 李鹏
//     * @Description //在二维码中间加个logo
//     * @Date 2020/3/30 14:47
//     * @Param [url, logos]
//     * @return byte[]
//     **/
//    public static byte[] encodeWithLogo(String url, String logos) {
//        byte[] data = null;
//        try {
//            Image image2 = ImageIO.read(new URL(url));//  null;//ImageIO.read(qrFile);
//            int width = image2.getWidth(null);
//            int height = image2.getHeight(null);
//            BufferedImage bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            //BufferedImage bufferImage =ImageIO.read(image);
//            Graphics2D g2 = bufferImage.createGraphics();
//            g2.drawImage(image2, 0, 0, width, height, null);
//            int matrixWidth = bufferImage.getWidth();
//            int matrixHeigh = bufferImage.getHeight();
//
//            //读取Logo图片
//            BufferedImage logo = GenerateImage(logos);
//            //开始绘制图片
//            g2.drawImage(logo,matrixWidth/5*2,matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5, null);//绘制
//            BasicStroke stroke = new BasicStroke(5,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
//            g2.setStroke(stroke);// 设置笔画对象
//            //指定弧度的圆角矩形
//            RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth/5*2, matrixHeigh/5*2, matrixWidth/5, matrixHeigh/5,20,20);
//            g2.setColor(Color.white);
//            g2.draw(round);// 绘制圆弧矩形
//
//            //设置logo 有一道灰色边框
//            BasicStroke stroke2 = new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
//            g2.setStroke(stroke2);// 设置笔画对象
//            RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth/5*2+2, matrixHeigh/5*2+2, matrixWidth/5-4, matrixHeigh/5-4,20,20);
//            g2.setColor(new Color(128,128,128));
//            g2.draw(round2);// 绘制圆弧矩形
//
//            g2.dispose();
//
//            bufferImage.flush();
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            ImageIO.write(bufferImage, "jpeg", out);
//            data = out.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//        return data;
//    }
//
//
//    /* *
//     * @Author 李鹏
//     * @Description //base64字符串转化流文件
//     * @Date 2020/3/30 14:48
//     * @Param [imgStr]
//     * @return java.awt.image.BufferedImage
//     **/
//    public static BufferedImage GenerateImage(String imgStr) {
//        //和base64String2ByteFun 冲突 用的时候解决
//        String s[] = imgStr.split(",");
//        byte[] src1 = base64String2ByteFun(imgStr); //Base64.getDecoder().decode(s[1]);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(src1);
//        BufferedImage bi = null;
//        try {
//            bi = ImageIO.read(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return  bi;
//    }
//
//    //base64字符串转byte[]
//    public static byte[] base64String2ByteFun(String base64Str){
//        byte[] bytes = null;
//        try {
//            bytes =  new BASE64Decoder().decodeBuffer(base64Str.split("base64,")[1]);
//        } catch (IOException e) {
//            bytes = new byte[1];
//        }
//        return bytes;
//    }
//
//    //byte[]转base64
//    public static String byte2Base64StringFun(byte[] b){
//        return Base64.encodeBase64String(b);
//    }
//
//    /* *
//     * @Author 李鹏
//     * @Description 将文件转换成Byte数组
//     * @Date 2020/3/31 11:18
//     * @Param [pathStr]
//     * @return byte[]
//     **/
//    public static byte[] getBytesByFile(String pathStr) {
//        File file = new File(pathStr);
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
//            byte[] b = new byte[1000];
//            int n;
//            while ((n = fis.read(b)) != -1) {
//                bos.write(b, 0, n);
//            }
//            fis.close();
//            byte[] data = bos.toByteArray();
//            bos.close();
//            return data;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//
//    /* *
//     * @Author 李鹏
//     * @Description 将Byte数组转换成文件
//     * @Date 2020/3/31 11:18
//     * @Param [bytes, filePath, fileName]
//     * @return void
//     **/
//    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
//        BufferedOutputStream bos = null;
//        FileOutputStream fos = null;
//        File file = null;
//        try {
//            File dir = new File(filePath);
//            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
//                dir.mkdirs();
//            }
//            file = new File(filePath + "\\" + fileName);
//            fos = new FileOutputStream(file);
//            bos = new BufferedOutputStream(fos);
//            bos.write(bytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (bos != null) {
//                try {
//                    bos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//
//    /**
//     * @Author lipeng
//     * @Description 文件或字符串重复后重新命名
//     * @Date 2022/7/5 11:33
//     * @param map map对象
//     * @param name 文件名称或字符串
//     * @return java.lang.String
//     **/
//    public final static String duplicateName(Map<String, Integer> map, String name){
//        String postfix = "";
//        if (!new Tika().detect(name).equals("application/octet-stream")){
//            postfix = name.substring(name.lastIndexOf("."));
//            name = name.substring(0, name.lastIndexOf("."));
//        }
//
//        Integer num = 1;
//        String newName = name;
//        if (map.get(name) != null){
//            num = map.get(name) + 1;
//            newName = name + "(" + num + ")";
//        }
//        map.put(name, num);
//        return newName + postfix;
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 吧在线url转file
//     * @Date 2022/10/13 13:55
//     * @param url url地址
//     * @param filePath 本地路径
//     * @return java.io.File
//     **/
//    public static File getNetUrlHttp(String url, String filePath){
//        //对本地文件命名，path是http的完整路径，主要得到资源的名字
//        System.out.println(filePath);
//        log.info(filePath);
//        File file = new File(filePath);
//
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//        try{
//            //判断文件的父级目录是否存在，不存在则创建
//            if(!file.getParentFile().exists()){
//                file.getParentFile().mkdirs();
//            }
//            try{
//                //创建文件
//                file.createNewFile();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            //下载
//            inputStream = new URL(url).openStream();
//            outputStream = new FileOutputStream(file);
//
//            int bytesRead = 0;
//            byte[] buffer = new byte[8192];
//            while ((bytesRead=inputStream.read(buffer,0,8192))!=-1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (null != outputStream) {
//                    outputStream.close();
//                }
//                if (null != inputStream) {
//                    inputStream.close();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return file;
//    }
//
//
//
//    /**
//     * 读取目录下的所有文件
//     *
//     * @param dir
//     *            目录
//     * @param fileNames
//     *            保存文件名的集合
//     * @return
//     */
//    public static void findFileList(File dir, List<String> fileNames) {
//        if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
//            return;
//        }
//        String[] files = dir.list();// 读取目录下的所有目录文件信息
//        for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
//            File file = new File(dir, files[i]);
//            if (file.isFile()) {// 如果文件
//                fileNames.add(dir + "\\" + file.getName());// 添加文件全路径名
//            } else {// 如果是目录
//                findFileList(file, fileNames);// 回调自身继续查询
//            }
//        }
//    }
//}
