package com.kunpeng.framework.utils.kptool;

import org.apache.commons.lang3.RandomUtils;

import java.net.InetAddress;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author 常您波
 * @Description
 * @Date 2020/9/11
 * @Param
 * @return
 **/
public final class KPIDUtil {


    private KPIDUtil(){}

    /**
     * 订单号生成
     **/
    private static ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
    private static final AtomicInteger SEQ = new AtomicInteger(1000000);

    public static Long generateOrderNo() {
        if (SEQ.intValue() > 9999999) {
            SEQ.getAndSet(1000000);
        }
        String format = null;
        String localIpSuffix = null;
        int andIncrement = 0;
        synchronized (KPIDUtil.class) {
       //     format = String.valueOf(System.currentTimeMillis());
            format = String.valueOf(Instant.now().getEpochSecond());
        }
        localIpSuffix = getLocalIpSuffix();
        andIncrement = SEQ.getAndIncrement();
      //  System.out.println(Thread.currentThread());
        return Long.valueOf(format+localIpSuffix+andIncrement);
    }

    private volatile static String IP_SUFFIX = null;

    private static String getLocalIpSuffix() {
        if (null != IP_SUFFIX) {
            return IP_SUFFIX;
        }
        try {
            synchronized (KPIDUtil.class) {
                if (null != IP_SUFFIX) {
                    return IP_SUFFIX;
                }
                InetAddress addr = InetAddress.getLocalHost();
                //  172.17.0.4  172.17.0.199 ,
               String hostAddress = addr.getHostAddress();
                if (null != hostAddress && hostAddress.length() > 4) {
                    String ipSuffix = hostAddress.trim().split("\\.")[3];
                    if (ipSuffix.length() >1) {
                        IP_SUFFIX = ipSuffix.substring(ipSuffix.length()-1, Integer.parseInt(ipSuffix));
                        return IP_SUFFIX;
                    }
                  //  ipSuffix = "0" + ipSuffix;
                    IP_SUFFIX = ipSuffix;
                    return IP_SUFFIX;
                }
                IP_SUFFIX = RandomUtils.nextInt(1, 9) + "";
                return IP_SUFFIX;
            }
        } catch (Exception e) {
//            System.out.println("获取IP失败:" + e.getMessage());
            IP_SUFFIX = RandomUtils.nextInt(1, 9) + "";
            return IP_SUFFIX;
        }
    }


    public static void main(String[] args)  {

        for (int j = 0; j < 50; j++) {
            List<Long> orderNos = Collections.synchronizedList(new ArrayList<Long>());
            IntStream.range(0, 1000000).parallel().forEach(i -> {
                orderNos.add(generateOrderNo());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            List<Long> filterOrderNos = orderNos.stream().distinct().collect(Collectors.toList());

//            System.out.println("订单长度：" + Long.valueOf(filterOrderNos.get(22).length()));
            System.out.println("生成订单数：" + orderNos.size());
            System.out.println("过滤重复后订单数：" + filterOrderNos.size());
            System.out.println("重复订单数：" + (orderNos.size() - filterOrderNos.size()));

            System.out.println();
        }

    }
}
