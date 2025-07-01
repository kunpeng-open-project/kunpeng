package com.kunpeng.framework.utils.kptool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author 李鹏
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
public final class KPRedisUtil {

    private static Logger log = LoggerFactory.getLogger(KPRedisUtil.class);

    private KPRedisUtil(){}

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    private static final RedisTemplate redisTemplate =  KPServiceUtil.getBean("redisTemplate", RedisTemplate.class);

//
//    @Resource(name = "redisTemplate")
//    private RedisTemplate redisTemplate;



    /**
     * @Author lipeng
     * @Description 把键值对放入redis中
     * @Author lipeng
     * @Date 2025/3/9 17:22
     * @param key
     * @param value
     * @return void
     **/
    @SuppressWarnings("不建议永久保存redis, 可能造成内存溢出")
    @Deprecated
    public static void set(Object key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        }catch (Exception e){}
    }



    /**
     * 实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
     *
     * @param key
     * @param value
     * @param timeout （以秒为单位）
     */

    /**
     * @Author lipeng
     * @Description 把键值对放入redis中
     * @Date 2025/3/9 17:29
     * @param key
     * @param value
     * @param timeout 过期时间 单位默认秒
     * @return void
     **/
    public static void set(Object key, Object value, long timeout) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        }catch (Exception e){}
    }


    /**
     * @Author lipeng
     * @Description 把键值对放入redis中
     * @Date 2025/3/9 17:30
     * @param key
     * @param value
     * @param timeout 过期时间
     * @param timeUnit 指定单位
     * @return void
     **/
    public static void set(Object key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        }catch (Exception e){}
    }


    /**
     * @Author lipeng
     * @Description 获取键对应的值
     * @Date 2025/3/7 9:54
     * @param key 键
     * @return java.lang.Object 返回键对应的值
     **/
    public static Object get(String key){
        Object value = redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * @Author lipeng
     * @Description 获取键对应的值
     * @Date 2025/3/7 9:58
     * @param key 键
     * @return java.lang.Object 返回键对应的值
     **/
    public static String getString(String key){
        try {
            return KPRedisUtil.get(key).toString();
        }catch (Exception ex){}
        return "";
    }


    /**
     * @Author lipeng
     * @Description 获取键对应的值
     * @Date 2025/3/9 17:18
     * @param key 键
     * @return java.lang.Integer
     **/
    public static Integer getInteger(String key){
        try {
            return Integer.valueOf(KPRedisUtil.getString(key));
        }catch (Exception ex){}
        return null;
    }



//
//    /**
//     * 获取键对应的值
//     * @param key 键
//     * @param start 开始位置
//     * @param end 结束位置
//     * @return 返回范围内的对应键的值
//     */
//    public static Object get(Object key, long start, long end){
//        Object value = redisTem.opsForValue().get(key, start, end);
//        return value;
//    }
//    /**
//     * 获取键对应的值
//     * @param key 键
//     * @param start 开始位置
//     * @param end 结束位置
//     * @return 返回范围内的对应键的值
//     */
//    public static Object get(String key, long start, long end){
//        Object value = redisTem.opsForValue().get(key, start, end);
//        return value;
//    }























    /**
     * @Author lipeng
     * @Description key是否存在
     * @Date 2021/7/7 12:05
     * @param key
     * @return boolean
     **/
    public static boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception ex){
            return false;
        }

    }
    // Key（键），简单的key-value操作
    /**
     * 实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)。
     *
     * @param key
     * @return
     */
    public static long ttl(String key) {
        return redisTemplate.getExpire(key);
    }
    /**
     * 实现命令：expire 设置过期时间，单位秒
     *
     * @param key
     * @return
     */
    public static void expire(String key, long timeout) {
        try {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }catch (Exception e){}

    }

    /**
     * @Author lipeng
     * @Description 设置过期时间
     * @Date 2022/10/25 20:27
     * @param key
     * @param timeout
     * @param timeUnit
     * @return void
     **/
    public static void expire(String key, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.expire(key, timeout, timeUnit);
        }catch (Exception e){}

    }

    /**
     * 实现命令：INCR key，增加key一次
     *
     * @param key
     * @return
     */
    public static long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern.concat("*"));
    }


    // String（字符串）

    /**
     * 实现命令：GET key，返回 key所关联的字符串值。
     *
     * @param key
     * @return value
     */

    // Hash（哈希表）
    /**
     * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
     *
     * @param key
     * @param field
     * @param value
     */
    public static void hset(String key, String field, Object value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
        }catch (Exception ex){}

    }
    /**
     * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
     *
     * @param key
     * @param field
     * @return
     */
    public static Object hget(String key, String field) {
        try {
            return redisTemplate.opsForHash().get(key, field);
        }catch (Exception e){
            try {
                Thread.sleep(2000);
                return redisTemplate.opsForHash().get(key, field);
            } catch (Exception ex) {}
        }
        return null;

    }
    /**
     * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *
     * @param key
     * @param fields
     */
    public static void hdel(String key, Object... fields) {
        try {
            redisTemplate.opsForHash().delete(key, fields);
        }catch (Exception e){}
    }

    /**
     * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
     *
     * @param key
     * @return
     */
    public static Map<Object, Object> hgetall(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        }catch (Exception e){}
        return null;
    }


    /* *
     * @Author 李鹏
     * @Description //list
     * @Date 2019/9/6 10:08
     * @Param [key, list]
     * @return void
     **/
    public static void setListLeftPush(String key, Object obj){
        try {
            redisTemplate.opsForList().leftPush(key, obj);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void setListLeftAll(String key, List<?> list){
        try {
            redisTemplate.opsForList().leftPushAll(key, list);
        }catch (Exception e){
            System.out.println(e);
        }
    }

//    public static void setListLeftPush(String key, String value){
//        try {
//            redisTemplate.opsForList().leftPush(key,value);
//        }catch (Exception e){}
//    }
//
//    public static void setListRightPush(String key, String value){
//        try {
//            redisTemplate.opsForList().rightPush(key,value);
//        }catch (Exception e){}
//
//    }
//
//    public static void setListLeftPush(String key, Object value){
//        try {
//            redisTemplate.opsForList().leftPush(key,value);
//        }catch (Exception e){}
//
//    }
//
//    public static void setListRightPush(String key, List<Object> list){
//        try {
//            redisTemplate.opsForList().rightPush(key,list);
//        }catch (Exception e){}
//    }




    public static List<Object> getList111(String key){
        try {
            List<Object> list = redisTemplate.opsForList().range(key,0, -1);
            return list;
        }catch (Exception e){
            log.info("[操作redis异常]" + e.getMessage());
        }
        return null;
    }

    public static List<?> getList(String key){
        try {
            List<?> list = redisTemplate.opsForList().range(key,0, -1);
            return list;
        }catch (Exception e){
            log.info("[操作redis异常]" + e.getMessage());
        }
        return null;
    }

    public static ListOperations getRedisList(){
        return redisTemplate.opsForList();
    }

    public static Long removeList(String key, Object value){
        try {
            return redisTemplate.opsForList().remove(key,0, value);
        }catch (Exception e){}
        return null;
    }

    public static void remove(String key){
        try {
            redisTemplate.delete(key);
        }catch (Exception e){
            try {
                redisTemplate.expire(key, 1, TimeUnit.MILLISECONDS);
                Thread.sleep(500);
            }catch (Exception ex){}

        }

    }


    /**
     * @Author lipeng
     * @Description 批量删除
     * @Date 2024/9/4 9:05
     * @param key
     * @return void
     **/
    public static void removeBacth(String key){
        try {
            redisTemplate.delete(redisTemplate.keys(key.concat("*")));
        }catch (Exception e){}
    }


    /**
     * @Author lipeng
     * @Description 获取锁
     * @Date 2021/2/1 12:57
     * @param key 锁的Key
     * @param value 值(随便写毫无意义)
     * @param releaseTime 锁过期时间 防止死锁 秒
     * @return boolean
     **/
    public static boolean lock(String key, int value, long releaseTime) {
        // 尝试获取锁
        Boolean boo = redisTemplate.opsForValue().setIfAbsent(key, value, releaseTime, TimeUnit.SECONDS);
        // 判断结果
        return boo != null && boo;
    }


    public static boolean lock(String key, long releaseTime) {
        // 尝试获取锁
        Boolean boo = redisTemplate.opsForValue().setIfAbsent(key, 9999, releaseTime, TimeUnit.SECONDS);
        // 判断结果
        return boo != null && boo;
    }



}
