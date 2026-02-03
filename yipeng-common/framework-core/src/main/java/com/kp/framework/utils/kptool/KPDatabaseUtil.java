package com.kp.framework.utils.kptool;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.exception.KPUtilException;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据库相关工具类。
 * @author lipeng
 * 2025/12/11
 */
@UtilityClass
public class KPDatabaseUtil {

    /**
     * 获取数据库类型标识。
     * @author lipeng
     * 2025/12/11
     * @return java.lang.String
     */
    public static String getDatabaseId() {
        SqlSessionFactory sqlSessionFactory = KPServiceUtil.getBean(SqlSessionFactory.class);
        return sqlSessionFactory.getConfiguration().getDatabaseId();
    }

    /**
     * 聚合函数：将字段按指定分隔符拼接（替代MySQL的GROUP_CONCAT）。
     * @author lipeng
     * 2025/12/11
     * @param column 字段名
     * @param distinct 别名
     * @param alias 是否去重
     * @return java.lang.String
     */
    public static String groupConcat(String column, boolean distinct, String alias) {
        String databaseId = KPServiceUtil.getBean(SqlSessionFactory.class).getConfiguration().getDatabaseId();
        if (KPStringUtil.isEmpty(databaseId)) {
            throw new KPUtilException("数据库类型标识不能为空");
        }

        String distinctKeyword = distinct ? "DISTINCT " : "";
        // 固定分隔符为逗号
        String separator = ",";
        StringBuilder functionSql = new StringBuilder();

        switch (databaseId) {
            case "mysql":
                functionSql.append("GROUP_CONCAT(").append(distinctKeyword).append(column).append(" SEPARATOR '").append(separator).append("')");
                break;
            case "postgresql":
            case "sqlserver":
                functionSql.append("STRING_AGG(").append(distinctKeyword).append(column).append(", '").append(separator).append("')");
                break;
            case "oracle":
                functionSql.append("LISTAGG(").append(distinctKeyword).append(column).append(", '").append(separator).append("') WITHIN GROUP (ORDER BY ").append(column).append(")");
                break;
            case "h2":
                functionSql.append("ARRAY_AGG(").append(distinctKeyword).append(column).append(" ORDER BY ").append(column).append(")");
                break;
            default:
                throw new UnsupportedOperationException("不支持的数据库类型: " + databaseId);
        }

        // 如果传入别名，则添加AS子句
        if (KPStringUtil.isNotEmpty(alias)) {
            functionSql.append(" AS ").append(alias);
        }

        return functionSql.toString();
    }

    /**
     * 聚合函数：将字段按指定分隔符拼接（替代MySQL的GROUP_CONCAT）。
     * 不传递别名时使用
     * @author lipeng
     * 2025/12/11
     * @param column 字段名
     * @param alias 是否去重
     * @return java.lang.String
     */
    public static String groupDistinctConcat(String column, String alias) {
        return groupConcat(column, true, alias);
    }

    /**
     * 使用聚合查询时 groupConcat 让.groupBy 适应各种数据库。
     * @author lipeng
     * 2025/8/27
     * @param wrapper MPJLambdaWrapper
     * @param tableAlias 数据库别名
     * @param entityClass 实体类
     */
    public static <T> void groupFieldsBy(MPJLambdaWrapper<T> wrapper, String tableAlias, Class<T> entityClass) {
        String databaseId = KPServiceUtil.getBean(SqlSessionFactory.class).getConfiguration().getDatabaseId();
        if (KPStringUtil.isEmpty(databaseId)) {
            throw new KPUtilException("数据库类型标识不能为空");
        }

        List<Field> allFields = KPReflectUtil.getAllDeclaredFields(entityClass).stream()
                .filter(field -> !Arrays.asList("serialversionuid", "entityclass").contains(field.getName().toLowerCase()))
                .collect(Collectors.toList());
        if (allFields.isEmpty()) return;

        List<String> groupByColumns = new ArrayList<>();

        switch (databaseId) {
            case "mysql":
                for (Field field : allFields) {
                    TableId primaryKey = field.getAnnotation(TableId.class);
                    if (primaryKey != null) {
                        groupByColumns.add(tableAlias + "." + primaryKey.value());
                        break;
                    }
                }
                break;
            case "postgresql":
            case "sqlserver":
            case "oracle":
            case "h2":
                for (Field field : allFields) {
                    TableId primaryKey = field.getAnnotation(TableId.class);
                    if (primaryKey != null) {
                        groupByColumns.add(tableAlias + "." + primaryKey.value());
                    } else {
                        TableField tableField = field.getAnnotation(TableField.class);
                        groupByColumns.add(tableAlias + "." + tableField.value());
                    }
                }
                break;
            default:
                throw new UnsupportedOperationException("不支持的数据库类型: " + databaseId);
        }
        wrapper.groupBy(String.join(",", groupByColumns));
    }

}