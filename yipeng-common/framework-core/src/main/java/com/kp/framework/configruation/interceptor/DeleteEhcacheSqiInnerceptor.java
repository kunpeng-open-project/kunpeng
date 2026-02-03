//package com.kp.framework.configruation.interceptor;
//
//import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
//import com.kp.framework.utils.kptool.KPCollectionUtil;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.ehcache.CacheManager;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * sql拦截器。
// * @author lipeng
// * 2024/4/15
// */
//@Slf4j
//public class DeleteEhcacheSqiInnerceptor implements InnerInterceptor {
//
//    /**
//     * 增删改的时候情况二级缓存。
//     * @author lipeng
//     * 2024/4/15
//     * @param executor 执行器
//     * @param ms 映射
//     * @param parameter 参数
//     */
//    @Override
//    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
//        try {
//            InnerInterceptor.super.beforeUpdate(executor, ms, parameter);
////           SqlCommandType sqlCommandType = ms.getSqlCommandType();
//            String msId = ms.getId();
//            CacheManager manager = CacheManager.create(this.getClass().getClassLoader().getResourceAsStream("classpath:ehcache.xml"));
//            manager.clearAllStartingWith(msId.substring(0, msId.lastIndexOf('.')));
//
//
//            try {
//                //删除扩展信息
//                String customerMapper = msId.substring(0, msId.indexOf(".mapper.") + 8) + "customer." + msId.substring(msId.indexOf(".mapper.") + 8, msId.lastIndexOf("Mapper")) + "CustomerMapper";
//                manager.clearAllStartingWith(customerMapper);
//            } catch (Exception ex) {
//            }
//        } catch (Exception ex) {
//            log.info("清空二级缓存失败！");
//        }
//    }
//
//    /**
//     * 查询的时候 如果是做级联查询  删除缓存。
//     * @author lipeng
//     * 2024/9/20
//     * @param executor 执行器
//     * @param ms 映射
//     * @param parameter 参数
//     * @param rowBounds 行数
//     * @param resultHandler 结果处理器
//     * @param boundSql  sql
//     */
//    @Override
//    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
//        String msId = ms.getId();
//        List<String> exclude = Arrays.asList(
//                "selectJoinList", "selectJoinOne", "selectJoinMap", "selectJoinMaps", "selectJoinPage", "selectJoinMapsPage", "selectJoinCount",
//                "updateJoin", "updateJoinAndNull",
//                "deleteJoin"
//        );
//        if (KPCollectionUtil.isContain(msId, exclude)) {
//            try {
//                CacheManager manager = CacheManager.create(this.getClass().getClassLoader().getResourceAsStream("classpath:ehcache.xml"));
//                manager.clearAllStartingWith(msId.substring(0, msId.lastIndexOf('.')));
//            } catch (Exception ex) {
//                log.info("清空查询级联二级缓存失败！");
//            }
//        }
//
//        InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
//    }
//}
