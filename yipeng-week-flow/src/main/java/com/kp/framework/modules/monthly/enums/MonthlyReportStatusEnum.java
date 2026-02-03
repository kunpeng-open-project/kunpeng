package com.kp.framework.modules.monthly.enums;

import com.kp.framework.exception.KPServiceException;

import java.util.Collection;

/**
 * 月计划状态。
 * @author lipeng
 * 2025/8/30
 */
public enum MonthlyReportStatusEnum {
    DRAFT(1, "草稿"),
    SUBMIT_FOR_REVIEW(2, "提交审核"),
    REVIEW_PASSED_WAITING_FOR_SPLIT(3, "审核成功-待拆分"),
    REVIEW_REJECTED(4, "审核驳回"),
    SPLITTED_IN_PROGRESS(5, "已拆分-进行中"),
    COMPLETED(6, "已完成"),
    OVERDUE(7, "逾期");


    private final String message;
    private final Integer code;

    MonthlyReportStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }

    public static MonthlyReportStatusEnum getCode(Integer code) {
        for (MonthlyReportStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }


    /**
     * 校验当前状态是否允许操作（使用枚举集合）
     * @param currentCode 当前状态码
     * @param forbiddenEnums 不允许操作的枚举集合
     * @param errorMsg 校验失败时的错误信息
     * @throws IllegalArgumentException 当currentCode无效时抛出
     * @throws KPServiceException 当状态不允许操作时抛出
     */
    public static void validateAllowedByEnum(Integer currentCode, Collection<MonthlyReportStatusEnum> forbiddenEnums, String errorMsg) {
        MonthlyReportStatusEnum current = getCode(currentCode);
        if (current == null) throw new IllegalArgumentException("无效的月度计划状态码: " + currentCode);

        if (forbiddenEnums != null && !forbiddenEnums.isEmpty() && forbiddenEnums.contains(current))
            throw new KPServiceException(current.message + errorMsg);
    }
}
