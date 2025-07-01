package com.kunpeng.framework.configruation.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

public class MybatisPlusBathConfig extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
        methodList.add(new deleteAllById("deleteAllById"));
        methodList.add(new deleteAllByIds("deleteAllByIds"));
        return methodList;
    }


    /**
     * @Author lipeng
     * @Description 物业删除单条
     * @Date 2024/11/4 9:37
     * @return
     **/
    class deleteAllById extends AbstractMethod {
        protected deleteAllById(String methodName) {
            super(methodName);
        }

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            String method = "deleteAllById";
            SqlMethod sqlMethod = SqlMethod.DELETE_BY_ID;
            String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty());
            SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, method, sqlSource);
        }
    }


    /**
     * @Author lipeng
     * @Description 物业删除批量
     * @Date 2024/11/4 9:37
     * @return
     **/
    class deleteAllByIds extends AbstractMethod {

        protected deleteAllByIds(String methodName) {
            super(methodName);
        }

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            String method = "deleteAllByIds";
            SqlMethod sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
            String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(), SqlScriptUtils.convertForeach(SqlScriptUtils.convertChoose("@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(item.getClass())", "#{item}", "#{item." + tableInfo.getKeyProperty() + "}"), "coll", (String) null, "item", ","));
            SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, method, sqlSource);
        }
    }
}
