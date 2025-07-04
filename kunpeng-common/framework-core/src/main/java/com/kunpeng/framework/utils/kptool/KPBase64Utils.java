package com.kunpeng.framework.utils.kptool;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @Author lipeng
 * @Description
 * @Date 2023/9/28
 * @return
 **/
public class KPBase64Utils {

    //盐
    private Integer[] saltssite = {1};

    //加的内容
    private static final String[] values = {"0","1","2","3","4","5","6","7","8","9",
            "a","b","c","d","e","f","g","h","i","g","k","l","m","n","o","p","q","r","s","t","u","v","w","x","z","y",
            "!","@","#","$","%","^","&","*"};

    //明文
    private String body;

    public KPBase64Utils(Integer[] saltssite, String body){
        this.saltssite = saltssite;
        this.body = body;
    }


    /**
     * @Author lipeng
     * @Description 加密
     * @Date 2023/9/28
     * @return java.lang.String
     **/
    public KPBase64Utils encryption(){
        this.body = Base64.getEncoder().encodeToString(this.body.getBytes(StandardCharsets.UTF_8));
        return this;
    }


    /**
     * @Author lipeng
     * @Description 解密
     * @Date 2023/9/28
     * @return java.lang.String
     **/
    public KPBase64Utils decode(){
        this.body = new String(Base64.getDecoder().decode(this.body.getBytes()), StandardCharsets.UTF_8);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 返回结果
     * @Date 2023/9/28
     * @param
     * @return java.lang.String
     **/
    public String build(){
        return this.body;
    }


    /**
     * @Author lipeng
     * @Description 随机加盐
     * @Date 2023/9/28
     * @return java.lang.String
     **/
    public KPBase64Utils addSalt(){
        StringBuffer sb = new StringBuffer(this.body);
        for (int value: this.saltssite) {
            sb.insert(value, this.values[KPNumberUtil.rod(0, this.values.length-1)]);
        }
        this.body =  sb.toString();
        return this;
    }


    /**
     * @Author lipeng
     * @Description 去盐
     * @Date 2023/9/28
     * @return java.lang.String
     **/
    public KPBase64Utils delSalt(){
        StringBuffer sb = new StringBuffer(this.body);

        for (int i = 0; i <  this.saltssite.length; i++) {
            sb.deleteCharAt(this.saltssite[i]-i);
        }
        this.body =  sb.toString();
        return this;
    }


    /**
     * @Author lipeng
     * @Description 根据要加密的内容生成合适的盐
     * @Date 2023/10/13
     * @param body 明文
     * @param interval 间隔
     * @return java.lang.String
     **/
    public String generateSaltssite (String body, Integer interval){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < (body.length()-2)/interval; i++) {
            int satrt = i*interval+1, end = i*interval+interval;
//            System.out.println("("+satrt+"~"+end+")"+KPNumberUtil.rod(satrt, end) +"-");
            Integer num = KPNumberUtil.rod(satrt, end);
            if (!list.contains(num)){
                list.add(KPNumberUtil.rod(satrt, end));
            }else{
                return null;
            }
        }
        StringBuilder sb = new StringBuilder("{");
        list.forEach(num->{
            sb.append(num).append(",");
        });
        return sb.toString().substring(0, sb.toString().length()-1) + "}";

//        return list.stream().toArray(Integer[]::new);
    }


//    public static void main(String[] args) {
//        String aaa = new KPBase64Utils(KPBase64Utils.saltssite, "lipeng-lipeng-lipeng-lipeng-lipeng-lipeng-lipeng").encryption().build();
//        System.out.println(aaa);
//        System.out.println(new KPBase64Utils(KPBase64Utils.saltssite, aaa).decode().build());
//        System.out.println("--------------------------------");
//        String bbb = new KPBase64Utils(KPBase64Utils.saltssite,"lipeng-lipeng-lipeng-lipeng-lipeng-lipeng-lipeng").addSalt().encryption().build();
//        System.out.println(bbb);
//        System.out.println(new KPBase64Utils(KPBase64Utils.saltssite, bbb).decode().delSalt().build());
//        String RabbitMqListeningConfig = "eyJjUlNBX15DVVN3UDBSSWtLRVl2IjoiTWhJcElrI0V2UUk5NCpkQnhBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NCS2N3Z2dTakFnRUFBb0lCQVFEQkxCTitNUXpGTnNVa2w5RURubVFHRU01N0RZZDU2T25Fa2xZWGlKd28zL2JDRW93NWFSVDBDTDJSVWdOZDd0MDY0S1c2VjNoYVhCdGF3aEtYVG9CSkdRYjl6QWUxWWhnalhtbTdTRG1sa0M1V1VDa1d6YmJxYzVTSktPOGltV2R5SW9BU0VkaDNteS94dnJFNlB5YnBvc3c1Tk02a2RyVnJBbmFKb1RPOW5SaDkxa2dFM212eEJrV1hqUW5aeXBWU1lkRDl6QkhCbmlWc1ZLK1Bkcktnb25qQnFqejhmRXhqWm8vUTc2ZnVQd0JMMmNDUTI0dmRMdXZJNFZadVRDeFpFbStwY21sZUFiRThUa3hnbzZ3bDVUYzRBUkNmTEUyRllRckJlVTUrbHNUQTM1WE5ucmFhUUxoWEdCWXpSa3ROOGlrbDdVRTlnWG5jdGhxclllNHRBZ01CQUFFQ2dnRUFESjhldCsrb1dWNFgyVlA5RnNBdzRnK1RqNGs3QS9LOVlYZ1JTQ1l5S1RQaHJ0MExqS01WSTN2TERSVjAwd2RNMTdCcGRsM2U1eTB6cHJBSVlIQURPYU41RXVOVHBwem9uYTVUemhEczgvM1NIK3dnRklBYVMrMHVORWNOMXU3UWlrTXBwem51RzZTSUV3TFF3dER4QlRJbC9MVTZyT3dmdEVTbWc1MXZpM0Rub1N2K0hNVWhkMnJway84VDVHWnByR3BpZTJ0ZkJPeFpkTElrMnhOc00zT0JwN1BzRzFPaXdsS0tWTlRlcHl6WTd4NXBicWlmNWVzL3dyalRFNGtoby9jYmVaRVo1WnA0L3N3aWdna2t1M1V5b0hCNlFaMUIxcHlESVZVeEE1UHp1RGQ1bXkzYmkvTHJya2JtSVFmYUhsWlRhUHlWMFR1RU4zdUtPdjRCS1FLQmdRRGpoeEZUTGpOWW4vdjI3NGJBelRwdHhlUVk3cXpxS0tGQm9KNjIzeDJaY1NWeFBDR0htM1VRbEIwV3RHUVpaUWZiajRKUWY3djQ5dlBsZXJNNUYwWmI4YUh2YTJRSGMvakxYOG5CeTAzKzdoL0swVGhsK1JSaU9naytWbkZacEJscVVISHJoc212VWNjMGV2N1pkRFBWSkkwS1AvYlJiZTFsaVhTdEZyTDBHUUtCZ1FEWldHdFFNVlRDRmxDUmtlNFc1cytycXEyenBaSm01Vzg3NG40VjFKTlB1ZHNpQ3hFZlBkZEJ3REdPSnpXa3BMZE1nNVg3MCtHVzRlQ2hadGRHaGtaR2pTWDVUSzhMVE1WVXc3UmdDL3RjcEpaUzFLYU8zNmVXZlJaU0V3OUU3RW1Ta1BXTGVBQnNVSTFzNUJIclhCTnhybmoxaklmejVrYkJ2NmRNRkhVdE5RS0JnRmYycXdzR2pKRlI0TDY2SWRXK1FqMllTVFFlYWpscEZkYmlleG5tTG5KWkhRbW5IcXRudloxNE5ickdhUUNzWDVwUjVDYXRDNFlZSzNqbnRBeDVaQms5MU1aVU5XcndPaHFlWU1rTWlZM3FqOTRBZnhabGNxejdGUUhGdDdMWlQzNGJ0YjlCOEExWXgzUVJyUjl5M21zajQyYzREWHN3VFp4NHhPUDZKNXRaQW9HQVgwb1p1bzMyU0NXNmF5Z2N4Nk9vTnU1Y3U1K0M0V1FEOGJCcWNTM0M3RGhpNzdrRVo3c1lMZmFTZzIyRFlrenBKRDJvdTBENDdjcllUa3NsWlFFbnZIVFR5a29wa0gyM0ltT3ZLRkQ0Z25TU2gzdytEZlBXcU83c28wMUI2NGpnOU1aak1TT0tvL0pwSHUyYlFhSWIwRUtiTzZUQ1VsZUtmQldIeHA1TmhXVUNnWUVBbmhpOE9KeHIreTU0dlhYQURZN1VMVHpaWXU3WFRyTmlScGY3V1Z3c3pmeWtNTjl1a3o5QVRsd2JnOXd2M0RCaEdLUEFwVTRsSHNnek0rNWZ1QUtiQmtHWnZ1VzR6OUM3Q09RTkc4eU9IYVY5RVgxUk5ZVC95Y0k2S2F1aGpMVUNmcG5tTSsrRksvZzl2Sm5Wak9QcTF0SjZESlpEdG5OcCs2SkIrbjNWSUlzPSIsImFwcGlkIjoiMDAyODYyMzYifQ==";
//        System.out.println(new KPBase64Utils(KPBase64Utils.saltssite, RabbitMqListeningConfig).decode().delSalt().build());
//
//        String aa = new KPBase64Utils(KPBase64Utils.saltssite,"lipeng-lipeng-lipeng-lipeng-lipeng-lipeng-lipeng").addSalt().build();
//        System.out.println(aa);
//        System.out.println(new KPBase64Utils(KPBase64Utils.saltssite,aa).delSalt().build());
//    }

}
