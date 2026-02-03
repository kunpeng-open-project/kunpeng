package com.kp.framework.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 相关操作。
 * @author lipeng
 * 2020/5/19
 */
public final class KPJWTUtil {

    public static final Integer DAY_10 = 864000; //10天
    public static final Integer DAY_1 = 86400; //1天

    //KEY
    public static final String KEY = "authToken";

    //密钥
    public static final String SECRET = "Y29tLnNtYWxsLnNob3BwaW5n";

    //主题
    public static final String SUBJECT = "GALAXY-DATA-SYSTEM-CA";

    private KPJWTUtil(){}

    /**
     * 生成Token。
     * @author lipeng
     * 2021/7/7
     * @param val 值
     * @param expireTime 过期时间 单位秒
     * @return java.lang.String
     */
    public static String createToken(String val, Integer expireTime)  {
        try {
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.SECOND, expireTime);
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
            throw  new IllegalArgumentException("生成token异常！");
        }
    }

   /**
    * 验证Token。
    * @author lipeng
    * 2021/7/7
    * @param token 验证Token
    * @return boolean
    */
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
     * 解析Token。
     * @author lipeng
     * 2021/7/7
     * @param token 验证Token
     * @return com.auth0.jwt.interfaces.Claim
     */
    public static Claim parseToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim(KEY);
    }
}
