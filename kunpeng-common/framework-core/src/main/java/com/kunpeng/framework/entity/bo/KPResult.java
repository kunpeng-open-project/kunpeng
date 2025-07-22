package com.kunpeng.framework.entity.bo;

import com.github.pagehelper.PageSerializable;
import com.kunpeng.framework.entity.internal.ResultCode;
import com.kunpeng.framework.enums.IErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lipeng
 * @Description 返回参数
 * @Date 2021/10/12 14:41
 * @return
 **/
@Data
public class KPResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //返回列表
    private T data;

    //返回code
    private Integer code;

    //提示内容
    private String message;

    //是否成功
    private Boolean success = false;

    private static <T> KPResult<T> kpResult(T data, int code, String msg) {
        KPResult<T> kpResult = new KPResult<>();
        kpResult.setCode(code);
        kpResult.setData(data);
        kpResult.setMessage(msg);
        if (code == ResultCode.SUCCESS.code()) kpResult.setSuccess(true);
        return kpResult;
    }

    public static <T> KPResult<T> success() {
        return kpResult(null, ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
    }

    public static <T> KPResult<T> success(T data) {
        return kpResult(data, ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
    }

    public static <T> KPResult<T> fail() {
        return kpResult(null, ResultCode.FAILED.code(), "操作失败");
    }

    public static <T> KPResult<T> fail(String msg) {
        return kpResult(null, ResultCode.FAILED.code(), msg);
    }

    public static <T> KPResult<T> fail(T data, String msg) {
        return kpResult(data, ResultCode.FAILED.code(), msg);
    }

    public static <T> KPResult<T> fail(Integer code, String msg) {
        return kpResult(null, code, msg);
    }

    public static <T> KPResult<T> error(IErrorCodeEnum iErrorCodeEnum) {
        return kpResult(null, iErrorCodeEnum.code(), iErrorCodeEnum.message());
    }

    public static <T> KPResult<T> list(List<T> list) {
//        PageInfo pageInfo = new PageInfo<>(list);
//        return (KPResult<T>) kpResult(new KPJSONFactoryUtil()
//                .put("list", list) // 列表
//                .put("total", pageInfo.getTotal()) // 总数
//                .put("pageNum", pageInfo.getPageNum()) // 当前页
//                .put("pageSize", pageInfo.getSize()) // 每页条数 当前页实际的记录数（可能小于 pageSize，比如最后一页数据不足一页）
//                .put("pages", pageInfo.getPages()) // 总页数
//                .build(), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
        return (KPResult<T>) kpResult(new PageSerializable<>(list), ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
    }


    /**
     * @param list  返回列表
     * @param total 总数
     * @return com.daoben.framework.entity.bo.KPResult
     * @Author lipeng
     * @Description
     * @Date 2022/5/31 14:49
     **/
    @Deprecated
    public static <T> KPResult<T> list(List list, long total) {
        PageSerializable pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(total);
        return (KPResult<T>) kpResult(pageSerializable, ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
    }

    /**
     * @param list         返回列表
     * @param databaseList 数据库查询出的列表，查询总数用
     * @return com.daoben.framework.entity.bo.KPResult
     * @Author lipeng
     * @Description
     * @Date 2022/5/31 14:50
     **/
    public static <T> KPResult<T> list(List list, List databaseList) {
        PageSerializable pageSerializable = new PageSerializable<>(list);
        pageSerializable.setTotal(new PageSerializable<>(databaseList).getTotal());
        return (KPResult<T>) kpResult(pageSerializable, ResultCode.SUCCESS.code(), ResultCode.SUCCESS.message());
    }
}
