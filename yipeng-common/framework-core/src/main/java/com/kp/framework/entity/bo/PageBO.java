package com.kp.framework.entity.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import com.kp.framework.utils.kptool.KPReflectUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 分页对象。
 * @author lipeng
 * 2022/5/20
 */
@Data
public class PageBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @TableField(exist = false)
    @KPNotNull(errMeg = "请输入当前页")
    private Integer pageNum = 1;

    @Schema(description = "条数", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @TableField(exist = false)
    @KPNotNull(errMeg = "请输入每页条数")
    @KPMaxLength(max = 100, errMeg = "每页条数不能超过100")
    private Integer pageSize = 10;

    @Schema(description = "排序规则 如 id desc, name asc", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @TableField(exist = false)
    private String orderBy;

    public String getOrderBy(Class clazz) {
        List<Field> fields = KPReflectUtil.getAllDeclaredFields(clazz);
        if (KPStringUtil.isEmpty(this.orderBy)) return " create_date desc";

        String orderByStr = this.orderBy;
        for (String orderByStrItem : this.orderBy.split(",")) {
            if (orderByStrItem.trim().contains(" asc")) orderByStrItem = orderByStrItem.replaceAll(" asc", "");
            if (orderByStrItem.trim().contains(" desc")) orderByStrItem = orderByStrItem.replaceAll(" desc", "");

            for (Field field : fields) {
                if (orderByStrItem.trim().equals(field.getName())) {
//                if (orderByStrItem.trim().substring(0, orderByStrItem.trim().lastIndexOf(" ")).equals(field.getName())){
                    try {
                        orderByStr = orderByStr.replaceAll(field.getName(), field.getAnnotation(TableField.class).value());
                    } catch (Exception ex) {
                        orderByStr = orderByStr.replaceAll(field.getName(), field.getAnnotation(TableId.class).value());
                    }
                    break;
                }
            }
        }
        return orderByStr;
    }


    public String getOrderBy(String orderBy, Class clazz) {
        List<Field> fields = KPReflectUtil.getAllDeclaredFields(clazz);
        if (KPStringUtil.isEmpty(orderBy)) return " create_date desc";


//        for (Field field : fields){
//            if (orderBy.contains(field.getName())){
//                try {
//                    return orderBy.replaceAll(field.getName(), field.getAnnotation(TableField.class).value());
////                    if (orderBy.contains("number")){
////                        orderBy = orderBy.replaceAll("number", "");
////                        orderBy = orderBy.replaceAll(field.getName(), "REGEXP_REPLACE("+field.getName()+",'[^0-9]+','')+0");
////                    }
////                    return orderBy;
//                }catch (Exception ex){
//                    return orderBy.replaceAll(field.getName(), field.getAnnotation(TableId.class).value());
//                }
//            }
//        }

        String orderByStr = orderBy;
        for (String orderByStrItem : orderBy.split(",")) {
            if (orderByStrItem.trim().contains(" asc")) orderByStrItem = orderByStrItem.replaceAll(" asc", "");
            if (orderByStrItem.trim().contains(" desc")) orderByStrItem = orderByStrItem.replaceAll(" desc", "");

            for (Field field : fields) {
                if (orderByStrItem.trim().equals(field.getName())) {
                    try {
                        orderByStr = orderByStr.replaceAll(field.getName(), field.getAnnotation(TableField.class).value());
                    } catch (Exception ex) {
                        orderByStr = orderByStr.replaceAll(field.getName(), field.getAnnotation(TableId.class).value());
                    }
                    break;
                }
            }
        }
        return orderByStr;
    }

}
