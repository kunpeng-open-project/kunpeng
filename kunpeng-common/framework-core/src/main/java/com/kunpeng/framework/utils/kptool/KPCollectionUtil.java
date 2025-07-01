package com.kunpeng.framework.utils.kptool;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.mapper.ParentMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @Author lipeng
 * @Description 集合相关的内容
 * @Date 2021/9/28 16:02
 * @return
 **/
public final class KPCollectionUtil {

    private KPCollectionUtil(){}

    /**
     * @Author lipeng
     * @Description 数字是否在数组中
     * @Date 2021/2/20 16:06
     * @param str 要比较的数字
     * @param arr 是否存在的数组
     * @return boolean
     **/
    public static final boolean isContain(Integer str, List<Integer> arr) {
        for (Integer num : arr) {
            if(str.equals(num))
                return true;
        }
        return false;
    }

    /**
     * @Author lipeng
     * @Description  Long是否在数组中
     * @Date 2021/12/23 11:21
     * @param str
     * @param arr
     * @return boolean
     **/
    public static final boolean isContain(Long str, List<Long> arr) {
        for (Long num : arr) {
            if(str.equals(num))
                return true;
        }
        return false;
    }

    public static final boolean isContain(String str, List<String> arr) {
        for (String num : arr) {
            if(str.contains(num))
                return true;
        }
        return false;
    }


    /**
     * @Author lipeng
     * @Description 查询list中某个属性的位置
     * @Date 2021/11/1 17:33
     * @param list list
     * @param field  属性
     * @param conditon 条件
     * @return java.lang.Integer
     **/
    public static Integer index(List<?> list, String field, String conditon) {
        for (int i = 0; i < list.size(); i++){
            JSONObject json = KPJsonUtil.toJson(list.get(i));
            String fieldValue = json.getString(field)==null?"":json.getString(field).trim();
            if (fieldValue.equals(conditon))
                return i;
        }
        return -1;
    }


    /**
     * @Author lipeng
     * @Description 物理分页
     * @Date 2022/1/25 17:30
     * @param list
     * @param pageNo
     * @param pageSize
     * @return java.util.List<?>
     **/
    public static List<?> paging(List<?> list, Integer pageNo, Integer pageSize) {
        return list.stream().skip((pageNo - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }


    /**
     * @Author lipeng
     * @Description 获取俩个对象key 值的补集 单独给 join 使用
     * @Date 2024/3/14 16:32
     * @param clazz 全量的对象
     * @param clazz2 比较的对象
     * @param prefix 前缀
     * @return java.lang.String
     **/
    public static String getFileNameDisjunction(Class clazz, Class clazz2, String prefix) {
        try {
            List<Field> fields = KPReflectUtil.getAllDeclaredFields(clazz);
            List<String> list1 = fields.stream().map(Field::getName).collect(Collectors.toList());
            List<String> list2 = KPReflectUtil.getAllDeclaredFields(clazz2).stream().map(Field::getName).collect(Collectors.toList());
            Map<String, String> map = CollectionUtils.disjunction(list1, list2).stream().collect(Collectors.toMap(Function.identity(), Function.identity()));

            StringBuilder sb = new StringBuilder();

            for (Field field : fields){
                if (map.get(field.getName()) == null) continue;

                String fileName = "";
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField == null) {
                    TableId tableId = field.getAnnotation(TableId.class);
                    if (tableId == null) continue;
                    fileName = tableId.value();
                }else{
                    if (!tableField.exist()) continue;
                    fileName = tableField.value();
                }



                if (KPStringUtil.isNotEmpty(prefix))
                    sb.append(prefix).append(".").append(fileName).append(",");
                if (KPStringUtil.isEmpty(prefix))
                    sb.append(fileName).append(",");
            }
            return sb.toString().substring(0, sb.toString().length()-1);
        }catch (KPServiceException ex){
            throw new KPServiceException(ex.getMessage());
        }catch (Exception e){
            throw new KPServiceException("获取补集异常");
        }
    }


    /**
     * 批量插入数据，自动按指定大小分组
     * @param baseMapper MyBatis-Plus的BaseMapper接口实例
     * @param list 待插入的数据列表
     * @param batchSize 每组大小（默认100）
     * @param <T> 实体类型
     * @return 成功插入的总记录数
     */
    public static <T> boolean insertBatch(ParentMapper<T> baseMapper, List<T> list, int batchSize) {
        if (KPStringUtil.isEmpty(list))  return true;

        Integer totalSuccess = 0;
        // 将大List按batchSize分割成多个子List
        List<List<T>> partitions = ListUtils.partition(list, batchSize);
        // 逐组插入并累加成功记录数
        for (List<T> batch : partitions) {
            int result = baseMapper.insertBatchSomeColumn(batch);
            if (result > 0) totalSuccess += result;
        }

        if (totalSuccess.equals(list.size())) return true;
        return false;
    }

}
