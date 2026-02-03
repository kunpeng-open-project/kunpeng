package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类。
 * @author lipeng
 * 2025/3/9
 */
@UtilityClass
public final class KPRedisUtil {

    private static Logger log = LoggerFactory.getLogger(KPRedisUtil.class);

    //    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    private static final RedisTemplate redisTemplate = KPServiceUtil.getBean("redisTemplate", RedisTemplate.class);


    //region -----------------------------String键值存取---------------------------------------------------------------

    /**
     * 把键值对放入redis中。
     * @author lipeng
     * 2025/3/9
     * @param key 键
     * @param value 值
     */
    @SuppressWarnings("不建议永久保存redis, 可能造成内存溢出")
    @Deprecated
    public static void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
        }
    }

    /**
     * 把键值对放入redis中。
     * @author lipeng
     * 2025/3/9
     * @param key 键
     * @param value 值
     * @param timeout 过期时间 单位默认秒
     */
    public static void set(String key, Object value, long timeout) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
    }

    /**
     * 把键值对放入redis中。
     * @author lipeng
     * 2025/3/9
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param timeUnit 指定单位
     */
    public static void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception e) {
        }
    }

    /**
     * 获取键对应的值。
     * @author lipeng
     * 2025/3/7
     * @param key 键
     * @return java.lang.Object
     */
    public static Object get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 获取键对应的值。
     * @author lipeng
     * 2025/3/7
     * @param key 键
     * @return java.lang.String
     */
    public static String getString(String key) {
        try {
            return KPRedisUtil.get(key).toString();
        } catch (Exception ex) {
        }
        return "";
    }

    /**
     * 获取键对应的值。
     * @author lipeng
     * 2025/3/9
     * @param key 键
     * @return java.lang.Integer
     */
    public static Integer getInteger(String key) {
        try {
            return Integer.valueOf(KPRedisUtil.getString(key));
        } catch (Exception ex) {
        }
        return null;
    }

    //endregion


    //region -----------------------------List键值存取---------------------------------------------------------------

    /**
     * 根据key存储到list的指定位置。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param index list中指定索引
     * @param value 值
     */
    public static void setList(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 存储到列表最左侧。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param obj 值
     */
    public static void setListByLeftPush(String key, Object obj) {
        redisTemplate.opsForList().leftPush(key, obj);
    }

    public static void setListByLeftPushAll(String key, List<T> list) {
        if (KPStringUtil.isEmpty(list)) return;
        redisTemplate.opsForList().leftPushAll(key, list);
    }

    /**
     * 存储到列表最右。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param obj 值
     */
    public static void setListByRightPush(String key, Object obj) {
        redisTemplate.opsForList().rightPush(key, obj);
    }

    public static void setListByRightPushAll(String key, List<T> list) {
        if (KPStringUtil.isEmpty(list)) return;
        redisTemplate.opsForList().rightPushAll(key, list);
    }

    /**
     * 查询list所有数据。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @return java.util.List
     */
    public static List getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 查询list 指定位置数据。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return java.util.List
     */
    public static List getList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

//    public static List<?> getList(String key){
//        try {
//            List<?> list = redisTemplate.opsForList().range(key,0, -1);
//            return list;
//        }catch (Exception e){
//            log.info("[操作redis异常]" + e.getMessage());
//        }
//        return null;
//    }

    /**
     * 获取对应key的list列表大小。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @return long
     */
    public static long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 删除List 指定数据。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param value 值
     * @return java.lang.Long
     */
    public static Long removeList(String key, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, 0, value);
        } catch (Exception e) {
        }
        return 0l;
    }

    //endregion


    //region -----------------------------Set(无序)键值存取---------------------------------------------------------------

    /**
     * 存储set类型的数据。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param values 值，可以是多个
     */
    public static void setSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 获取key对应set类型数据的大小。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @return long
     */
    public static long getSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 获取set类型的数据。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @return java.util.Set
     */
    public static Set setSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 删除set数据。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param values 值，可以是多个
     * @return long
     */
    public long removeSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
        }
        return 0;
    }
    //endregion


    //region -----------------------------ZSet(有序)键值存取---------------------------------------------------------------

    /**
     * 存储有序集合。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param value 值
     * @param score 排序
     */
    public static void setZSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public static void setZSet(String key, Set set) {
        redisTemplate.opsForZSet().add(key, set);
    }

    /**
     * 获取key指定范围的值。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return java.util.Set
     */
    public static Set getZSet(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public static Set getZSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    /**
     * 获取对用数据的大小。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @return long
     */
    public static long getZSetSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    //endregion


    //region -----------------------------HashMap键值存取------------------------------------------------------------------

    /**
     * 存储hashMap数据。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param hashKey map的id
     * @param value 值
     */
    public static void setHash(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取Hash大小。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     */
    public static void getHashSize(String key) {
        redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取hashMap给定域 hashKey的值。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param hashKey map的id
     * @return java.lang.Object
     */
    public static Object getHash(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 返回哈希表 key中，所有的域和值。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除Hash表 key 中的一个或多个指定域，不存在的域将被忽略。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param hashKeys 域，可以是多个
     * @return java.lang.Long
     */
    public static Long removeHash(Object key, Object... hashKeys) {
        try {
            return redisTemplate.opsForHash().delete(key, hashKeys);
        } catch (Exception e) {
        }
        return 0l;
    }

    //endregion

    /**
     * key是否存在。
     * @author lipeng
     * 2021/7/7
     * @param key 键
     * @return boolean
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * 获取键的过期时间。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @return long
     */
    public static long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 设置过期时间 单位秒。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param timeout 过期时间，单位秒
     * @return java.lang.Boolean
     */
    public static Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置过期时间。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     * @param timeout 过期时间
     * @param timeUnit 过期时间的单位
     * @return java.lang.Boolean
     */
    public static Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 实现命令：INCR key，增加key一次
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

    /**
     * 删除键值。
     * @author lipeng
     * 2025/7/4
     * @param key 键
     */
    public static void remove(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            try {
                redisTemplate.expire(key, 1, TimeUnit.MILLISECONDS);
                KPThreadUtil.sleep(500);
            } catch (Exception ex) {
            }
        }
    }

    /**
     * 批量删除。
     * @author lipeng
     * 2024/9/4
     * @param key 键
     */
    public static void removeBacth(String key) {
        try {
            redisTemplate.delete(redisTemplate.keys(key.concat("*")));
        } catch (Exception e) {
        }
    }


    /**
     * 获取键对应的值的大小
     * @param key 键
     * @return 大小
     */
    public static long getSize(Object key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 获取锁。
     * @author lipeng
     * 2021/2/1
     * @param key 锁的Key
     * @param value 值(随便写毫无意义)
     * @param releaseTime 锁过期时间 防止死锁 秒
     * @return boolean
     */
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
