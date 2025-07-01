package com.kunpeng.framework.entity.bo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kunpeng.framework.annotation.verify.KPMaxLength;
import com.kunpeng.framework.annotation.verify.KPNotNull;
import com.kunpeng.framework.utils.kptool.KPReflectUtil;
import com.kunpeng.framework.utils.kptool.KPStringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author lipeng
 * @Description 分页对象
 * @Date 2022/5/20 16:50
 * @return
 **/
@Data
public class PageBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前页", required = true, example = "1")
    @TableField(exist = false)
    @KPNotNull(errMeg = "请输入当前页")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "条数", required = true, example = "10")
    @TableField(exist = false)
    @KPNotNull(errMeg = "请输入每页条数")
    @KPMaxLength(max = 100, errMeg = "每页条数不能超过100")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "排序规则 如 id desc, name asc", required = false, example = "")
    @TableField(exist = false)
    private String orderBy;

    public String getOrderBy(Class clazz) {
        List<Field> fields = KPReflectUtil.getAllDeclaredFields(clazz);
        if (KPStringUtil.isEmpty(this.orderBy)) return " create_date desc";

        String orderByStr = this.orderBy;
        for (String orderByStrItem : this.orderBy.split(",")){
            if (orderByStrItem.trim().contains(" asc")) orderByStrItem = orderByStrItem.replaceAll(" asc", "");
            if (orderByStrItem.trim().contains(" desc")) orderByStrItem = orderByStrItem.replaceAll(" desc", "");

            for (Field field : fields){
                if (orderByStrItem.trim().equals(field.getName())){
//                if (orderByStrItem.trim().substring(0, orderByStrItem.trim().lastIndexOf(" ")).equals(field.getName())){
                    try {
                        orderByStr = orderByStr.replaceAll(field.getName(), field.getAnnotation(TableField.class).value());
                    }catch (Exception ex){
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
        for (String orderByStrItem : orderBy.split(",")){
            if (orderByStrItem.trim().contains(" asc")) orderByStrItem = orderByStrItem.replaceAll(" asc", "");
            if (orderByStrItem.trim().contains(" desc")) orderByStrItem = orderByStrItem.replaceAll(" desc", "");

            for (Field field : fields){
                if (orderByStrItem.trim().equals(field.getName())){
                    try {
                        orderByStr = orderByStr.replaceAll(field.getName(), field.getAnnotation(TableField.class).value());
                    }catch (Exception ex){
                        orderByStr = orderByStr.replaceAll(field.getName(), field.getAnnotation(TableId.class).value());
                    }
                    break;
                }
            }
        }
        return orderByStr;
    }

}
