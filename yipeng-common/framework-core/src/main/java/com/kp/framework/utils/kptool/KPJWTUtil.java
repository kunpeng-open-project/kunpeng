package com.kp.framework.utils.kptool;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kp.framework.exception.KPServiceException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* *
 * @Author 李鹏
 * @Description JWT 相关操作
 * @Date 2020/5/19 11:29
 * @Param
 * @return
 **/
public final class KPJWTUtil {

    // 过期时间10天 单位秒  具体有效时间看redis
    public static final Integer EXPIRE_TIME = 60 * 60 * 24 * 10;

    //KEY
    public static final String KEY = "authToken";

    //密钥
    public static final String SECRET = "Y29tLnNtYWxsLnNob3BwaW5n";

    //主题
    public static final String SUBJECT = "GALAXY-DATA-SYSTEM-CA";

    private KPJWTUtil(){}

    /**
     * @Author lipeng
     * @Description 生成Token
     * @Date 2021/7/7 13:46
     * @param val
     * @return java.lang.String
     **/
    public static String createToken(String val)  {
        try {
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.SECOND, EXPIRE_TIME);
            Date expireDate = nowTime.getTime();

            Map<String, Object> map = new HashMap<>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");

            String token = JWT.create()
                    .withHeader(map)//头
                    .withClaim(KEY, val)
                    .withSubject(SUBJECT)
                    .withIssuedAt(new Date())//签名时间
                    .withExpiresAt(expireDate)//过期时间
                    .sign(Algorithm.HMAC256(SECRET));//签名
            return token;
        }catch (Exception ex){
            throw  new KPServiceException("生成token异常！");
        }
    }


   /**
    * @Author lipeng
    * @Description 验证Token
    * @Date 2021/7/7 13:46
    * @param token
    * @return boolean
    **/
    public static boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * @Author lipeng
     * @Description  解析Token
     * @Date 2021/7/7 13:46
     * @param token
     * @return java.lang.String
     **/
//    public static String parseToken(String token) {
//        DecodedJWT decodedJWT = JWT.decode(token);
//        return decodedJWT.getClaim(KEY).asString();
//    }

    public static Claim parseToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim(KEY);
    }

    public static void main(String[] args) {
        try {
            String aa =  KPJWTUtil.createToken("111");

            System.out.println(aa);


            System.out.println(KPJWTUtil.parseToken(aa));
            System.out.println(KPJWTUtil.verifyToken(aa));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
