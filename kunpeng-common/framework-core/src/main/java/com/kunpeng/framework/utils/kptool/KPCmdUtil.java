package com.kunpeng.framework.utils.kptool;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author lipeng
 * @Description 操作命令窗口
 * @Date 2020/9/22
 * @Param
 * @return
 **/
public final class KPCmdUtil {

    private static Logger log = LoggerFactory.getLogger(KPCmdUtil.class);

    private KPCmdUtil(){}

    /**
     * @Author lipeng
     * @Description 执行cmd命令
     * @Date 2020/9/22
     * @Param [cmd]
     * @return java.lang.String
     **/
    public static String command(String cmd){
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            InputStream in = process.getInputStream();
            StringBuilder result = new StringBuilder();
            byte[] data = new byte[256];
            while(in.read(data) != -1){
                String encoding = System.getProperty("sun.jnu.encoding");
                result.append(new String(data,encoding));
            }
            return result.toString();
        }catch (Exception e){
            log.info("[执行cmd命令错误] cmd命令：{}, 错误原因：{}", cmd, e.getMessage());
        }
       return null;
    }

    /**
     * @Author lipeng
     * @Description 根据指定ip获取对应的mac地址
     * @Date 2020/9/22
     * @Param [ip]
     * @return java.lang.String
     **/
    public static String getMac(String ip) throws Exception{
//        String result = command("ping "+ip+" -n 2");
//        if(result.contains("TTL")){
//            result = command("arp -a "+ip);
//        }
        String result = command("arp -a "+ip);
        if(KPStringUtil.isEmpty(result))
            return null;
        String regExp = "([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(result);
        StringBuilder mac = new StringBuilder();
        while (matcher.find()) {
            String temp = matcher.group();
            mac.append(temp);
        }
        return mac.toString();
    }


    /**
     * @Author lipeng
     * @Description 查询所有ip 和 mac
     * @Date 2020/9/22
     * @Param []
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     **/
    public static List<JSONObject> getIPAndMacs() throws Exception{
        String result = command("arp -a");
        if(KPStringUtil.isEmpty(result))
            return null;
        String[] str =  result.split(System.lineSeparator());
        Pattern mac = Pattern.compile("([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}");
        Pattern ip = Pattern.compile("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))");

        List<JSONObject> list = new ArrayList<JSONObject>();
        for (String row : str){
            Matcher macMatcher = mac.matcher(row);
            Matcher matcheriP = ip.matcher(row);
            JSONObject jsonObject = new JSONObject();
            while (macMatcher.find()) {
                jsonObject.put("mac", macMatcher.group());

            }
            while (matcheriP.find()) {
                jsonObject.put("ip", matcheriP.group());

            }
            list.add(jsonObject);
        }
        return list;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws  Exception{
        command("arp -a");
        getMac("192.168.1.1");
        long start = System.currentTimeMillis();
        List<JSONObject> list = getIPAndMacs();
        for (JSONObject json : list){
            if(KPStringUtil.isNotEmpty(json.getString("ip")))
                System.out.println("ip：" + json.getString("ip") +"  mac：" + json.getString("mac"));
        }
        System.out.println("用时：" + (System.currentTimeMillis()- start) +"毫秒");
//        List<JSONObject> list = getIPAndMacs();
//        System.out.println(list);
//        System.out.println(System.currentTimeMillis());
//        System.out.println(getMacAddress("192.168.1.1"));
//        System.out.println(System.currentTimeMillis());

    }
}
