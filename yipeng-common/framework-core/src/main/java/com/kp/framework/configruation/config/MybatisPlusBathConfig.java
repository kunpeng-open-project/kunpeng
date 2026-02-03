package com.kp.framework.configruation.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * MybatisPlus扩展方法。
 * @author lipeng
 * 2024/11/4
 */
public class MybatisPlusBathConfig extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
        methodList.add(new InsertBatchSomeColumn("kpInsertBatchSomeColumn", i -> i.getFieldFill() != FieldFill.UPDATE));
        methodList.add(new deleteAllById("kpDeleteAllById"));
        methodList.add(new deleteAllByIds("kpDeleteAllByIds"));
        return methodList;
    }

    /**
     * 物理删除单条。
     * @author lipeng
     * 2024/11/4
     */
    static class deleteAllById extends AbstractMethod {
        protected deleteAllById(String methodName) {
            super(methodName);
        }

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            SqlMethod sqlMethod = SqlMethod.DELETE_BY_ID;
            String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty());
            SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, this.methodName, sqlSource);
        }
    }

    /**
     * 物理删除批量。
     * @author lipeng
     * 2024/11/4
     */
    static class deleteAllByIds extends AbstractMethod {

        protected deleteAllByIds(String methodName) {
            super(methodName);
        }

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            SqlMethod sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
            String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(), SqlScriptUtils.convertForeach(SqlScriptUtils.convertChoose("@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(item.getClass())", "#{item}", "#{item." + tableInfo.getKeyProperty() + "}"), "coll", (String) null, "item", ","));
            SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, this.methodName, sqlSource);
        }
    }
}
