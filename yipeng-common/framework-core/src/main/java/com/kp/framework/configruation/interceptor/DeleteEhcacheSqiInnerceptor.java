package com.kp.framework.configruation.interceptor;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.kp.framework.utils.kptool.KPCollectionUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lipeng
 * @Description sql拦截器
 * @Date 2024/4/15 18:18
 * @return
 **/
@Slf4j
public class DeleteEhcacheSqiInnerceptor implements InnerInterceptor {



    /**
     * @Author lipeng
     * @Description 增删改的时候情况二级缓存
     * @Date 2024/4/15 18:18
     * @param executor
     * @param ms
     * @param parameter
     * @return void
     **/
    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
       try {
           InnerInterceptor.super.beforeUpdate(executor, ms, parameter);
//           SqlCommandType sqlCommandType = ms.getSqlCommandType();
           String msId = ms.getId();
           CacheManager manager = CacheManager.create(this.getClass().getClassLoader().getResourceAsStream("classpath:ehcache.xml"));
           manager.clearAllStartingWith(msId.substring(0, msId.lastIndexOf('.')));


           try {
               //删除扩展信息
               String customerMapper = msId.substring(0, msId.indexOf(".mapper.") + 8) + "customer." + msId.substring(msId.indexOf(".mapper.") + 8, msId.lastIndexOf("Mapper")) + "CustomerMapper";
               manager.clearAllStartingWith(customerMapper);
           }catch (Exception ex){}
       }catch (Exception ex){
           log.info("清空二级缓存失败！");
       }
    }


    /**
     * @Author lipeng
     * @Description 查询的时候 如果是做级联查询  删除缓存
     * @Date 2024/9/20 10:28
     * @param executor
     * @param ms
     * @param parameter
     * @param rowBounds
     * @param resultHandler
     * @param boundSql
     * @return void
     **/
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        String msId = ms.getId();
        List<String> exclude = Arrays.asList(
                "selectJoinList", "selectJoinOne", "selectJoinMap", "selectJoinMaps", "selectJoinPage", "selectJoinMapsPage","selectJoinCount",
                "updateJoin", "updateJoinAndNull",
                "deleteJoin"
        );
        if (KPCollectionUtil.isContain(msId, exclude)){
            try {
                CacheManager manager = CacheManager.create(this.getClass().getClassLoader().getResourceAsStream("classpath:ehcache.xml"));
                manager.clearAllStartingWith(msId.substring(0, msId.lastIndexOf('.')));
            }catch (Exception ex){
                log.info("清空查询级联二级缓存失败！");
            }
        }

        InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }
}
