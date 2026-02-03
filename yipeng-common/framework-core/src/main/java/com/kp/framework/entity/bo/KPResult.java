package com.kp.framework.entity.bo;

import com.github.pagehelper.PageSerializable;
import com.kp.framework.entity.internal.ResultCode;
import com.kp.framework.enums.IErrorCodeEnum;
import com.kp.framework.utils.kptool.KPStringUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 全局统一返回结果封装类。
 * 所有接口统一返回此对象，规范返回格式：code+message+success+data
 * @author lipeng
 * 2026/1/21
 * @param <T> 泛型，支持任意数据类型返回
 */
@Data
@Accessors(chain = true)
public class KPResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    //返回列表
    private T data;
    //返回code
    private Integer code;
    //提示内容
    private String message;
    //是否成功
    private Boolean success = false;


    /**
     * 私有内部统一赋值方法
     * 所有对外暴露的方法均通过此方法赋值，保证赋值逻辑唯一入口
     * @param data  返回数据
     * @param code  状态码
     * @param msg   提示信息
     * @param <T>   泛型
     * @return      统一返回结果对象
     */
    private static <T> KPResult<T> kpResult(T data, int code, String msg) {
        return new KPResult<T>()
                .setCode(code)
                .setData(data)
                .setMessage(msg)
                .setSuccess(code == ResultCode.SUCCESS.code());
    }

    /**
     * 操作成功-无返回数据
     * @param <T> 泛型占位
     * @return 统一返回结果对象
     */
    public static <T> KPResult<T> success() {
        return kpResult(null, ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
    }

    /**
     * 操作成功-带返回数据
     * @param data 返回的业务数据
     * @param <T> 泛型，支持任意数据类型
     * @return 统一返回结果对象
     */
    public static <T> KPResult<T> success(T data) {
        return kpResult(data, ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
    }

    /**
     * 操作成功-带返回数据+自定义提示信息
     * @param data 返回的业务数据
     * @param msg 自定义成功提示语
     * @param <T> 泛型，支持任意数据类型
     * @return 统一返回结果对象
     */
    public static <T> KPResult<T> success(T data, String msg) {
        return kpResult(data, ResultCode.SUCCESS.code(), msg);
    }

    /**
     * 操作失败-默认提示语
     * @param <T> 泛型占位
     * @return 统一返回结果对象
     */
    public static <T> KPResult<T> fail() {
        return kpResult(null, ResultCode.FAILED.code(), "操作失败");
    }

    /**
     * 操作失败-自定义失败提示语
     * @param msg 自定义失败提示语
     * @param <T> 泛型占位
     * @return 统一返回结果对象
     */
    public static <T> KPResult<T> fail(String msg) {
        return kpResult(null, ResultCode.FAILED.code(), msg);
    }

    /**
     * 操作失败-自定义状态码+自定义提示语
     * @param code 自定义失败状态码
     * @param msg 自定义失败提示语
     * @param <T> 泛型占位
     * @return 统一返回结果对象
     */
    public static <T> KPResult<T> fail(Integer code, String msg) {
        return kpResult(null, code, msg);
    }

    /**
     * 业务异常返回-适配自定义异常枚举
     * @param iErrorCodeEnum 异常枚举，实现IErrorCodeEnum接口
     * @param <T> 泛型占位
     * @return 统一返回结果对象
     */
    public static <T> KPResult<T> error(IErrorCodeEnum iErrorCodeEnum) {
        return kpResult(null, iErrorCodeEnum.code(), iErrorCodeEnum.message());
    }

    /**
     * 分页列表返回-自动封装分页对象
     * 空列表时自动提示【暂无数据】
     * @param list 分页后的业务数据列表
     * @param <T> 泛型，列表数据类型
     * @return 统一返回结果对象，内部封装分页数据
     */
    @SuppressWarnings("unchecked")
    public static <T> KPResult<T> list(List<T> list) {
        String msg = KPStringUtil.isEmpty(list) ? "暂无数据" : ResultCode.SUCCESS.message();
        return kpResult((T) new PageSerializable<>(list), ResultCode.SUCCESS.code(), msg);
    }
//    public static <T> KPResult<T> list(List<T> list) {
////        PageInfo pageInfo = new PageInfo<>(list);
////        return (KPResult<T>) kpResult(new KPJSONFactoryUtil()
////                .put("list", list) // 列表
////                .put("total", pageInfo.getTotal()) // 总数
////                .put("pageNum", pageInfo.getPageNum()) // 当前页
////                .put("pageSize", pageInfo.getSize()) // 每页条数 当前页实际的记录数（可能小于 pageSize，比如最后一页数据不足一页）
////                .put("pages", pageInfo.getPages()) // 总页数
////                .build(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
//        return (KPResult<T>) kpResult(new PageSerializable<>(list), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
//    }


    /**
     * 分页列表返回-手动传入总数（已废弃，建议使用无参list方法）
     * @param list 分页后的业务数据列表
     * @param total 数据总条数
     * @param <T> 泛型，列表数据类型
     * @return 统一返回结果对象，内部封装分页数据
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public static <T> KPResult<T> list(List<T> list, long total) {
        PageSerializable<T> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(total);
        String msg = KPStringUtil.isEmpty(list) ? "暂无数据" : ResultCode.SUCCESS.message();
        return kpResult((T) pageSerializable, ResultCode.SUCCESS.code(), msg);
    }
//    @Deprecated
//    public static <T> KPResult<T> list(List list, long total) {
//        PageSerializable pageSerializable = new PageSerializable<>(list);
//        pageSerializable.setTotal(total);
//        return (KPResult<T>) kpResult(pageSerializable, ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
//    }

    /**
     * 分页列表返回-传入分页列表+数据库原始列表（自动统计总数）
     * @param list 分页后的业务数据列表
     * @param databaseList 数据库查询的原始列表（用于统计总数）
     * @param <T> 泛型，列表数据类型
     * @return 统一返回结果对象，内部封装分页数据
     */
    @SuppressWarnings("unchecked")
    public static <T> KPResult<T> list(List<T> list, List<T> databaseList) {
        PageSerializable<T> pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(new PageSerializable<>(databaseList).getTotal());
        String msg = KPStringUtil.isEmpty(list) ? "暂无数据" : ResultCode.SUCCESS.message();
        return kpResult((T) pageSerializable, ResultCode.SUCCESS.code(), msg);
    }

//    /**
//     * @param list         返回列表
//     * @param databaseList 数据库查询出的列表，查询总数用
//     * @return com.daoben.framework.entity.bo.KPResult
//     * @Author lipeng
//     * @Description
//     * @Date 2022/5/31 14:50
//     **/
//    public static <T> KPResult<T> list(List list, List databaseList) {
//        PageSerializable pageSerializable = new PageSerializable<>(list);
//        pageSerializable.setTotal(new PageSerializable<>(databaseList).getTotal());
//        return (KPResult<T>) kpResult(pageSerializable, ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
//    }

}
