package com.yuanlrc.base.bean;

/**
 * 提现状态
 */
public enum WithdrawalStatus {

    AUDIT(1, "审核"),
    PASS(2, "通过"),
    NOT_PASS(3, "未通过");

    private Integer code; //状态

    private String reason; //描述

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    WithdrawalStatus(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
