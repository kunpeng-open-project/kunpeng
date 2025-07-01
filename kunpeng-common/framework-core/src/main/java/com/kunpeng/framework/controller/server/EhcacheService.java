package com.kunpeng.framework.controller.server;

import com.kunpeng.framework.entity.po.EhcacheHitListCustomerPO;
import com.kunpeng.framework.entity.po.EhcacheListCustomerPO;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lipeng
 * @Description
 * @Date 2024/4/10 10:00
 * @return
 **/
@Service
public class EhcacheService {



    /**
     * @Author lipeng
     * @Description 查询缓存记录列表
     * @Date 2024/4/10 10:02
     * @param
     * @return java.util.List<com.jfzh.rht.modules.log.po.customer.EhcacheHitListCustomerPO>
     **/
    public List<EhcacheHitListCustomerPO> queryList() {
        CacheManager manager = CacheManager.create(this.getClass().getClassLoader().getResourceAsStream("classpath:ehcache.xml"));
        DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] cacheNames = manager.getCacheNames();
        List<EhcacheHitListCustomerPO> rowList = new ArrayList<>();
        for (int i = 0; i < cacheNames.length; i++) {
            EhcacheHitListCustomerPO row = new EhcacheHitListCustomerPO();
            Cache cache = manager.getCache(cacheNames[i]);

            List keys = cache.getKeys();

            List<EhcacheListCustomerPO> list = new ArrayList<>();
            for (Object key : keys) {
                try {
                    EhcacheListCustomerPO po = new EhcacheListCustomerPO();
                    Element ele = cache.get(key);
                    po.setBody(ele.getValue());
                    po.setCreateDate(sf.format(ele.getCreationTime()));
                    po.setLastAccessDate(sf.format(ele.getLastAccessTime()));
                    po.setExpirationDate(sf.format(ele.getExpirationTime()));
                    po.setLastUpdateDate(sf.format(ele.getLastUpdateTime()));
                    po.setHtCount(ele.getHitCount());
                    po.setTimeToLive(ele.getTimeToLive() + "毫秒");
                    po.setTimeToIdle(ele.getTimeToIdle() + "毫秒");
                    list.add(po);
                }catch (Exception e){}
            }
            row.setSqlCacheList(list);
            Statistics stat = cache.getStatistics();

            row.setMemoryStoreSize(cache.getMemoryStoreSize());
            row.setDiskStoreSize(cache.getDiskStoreSize());
            row.setCacheHits(stat.getCacheHits());
            row.setInMemoryHits(stat.getInMemoryHits());
            row.setOnDiskHits(stat.getOnDiskHits());
            row.setCacheMisses(stat.getCacheMisses());
            row.setEvictionCount(stat.getEvictionCount());
            row.setMapper(cacheNames[i].substring(cacheNames[i].lastIndexOf(".")+1));


            rowList.add(row);
        }
        return rowList;
    }


    /**
     * @Author lipeng
     * @Description 清空所有缓存
     * @Date 2024/4/10 10:02
     * @param
     * @return void
     **/
    public void clearAll() {
        CacheManager manager = CacheManager.create(this.getClass().getClassLoader().getResourceAsStream("classpath:ehcache.xml"));
        manager.clearAll();
    }


    /**
     * @Author lipeng
     * @Description 清空指定缓存
     * @Date 2024/7/11 14:09
     * @param clazz
     * @return void
     **/
    public void clear(Class clazz) {
        CacheManager manager = CacheManager.create(this.getClass().getClassLoader().getResourceAsStream("classpath:ehcache.xml"));
        manager.clearAllStartingWith(clazz.getName());
    }
}
