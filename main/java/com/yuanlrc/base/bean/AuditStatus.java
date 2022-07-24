package com.yuanlrc.base.bean;

public enum AuditStatus {

    UNCOMMITTED(0, "未提交"),
    AUDIT(1, "审核中"),
    NOT_PASS(2, "未通过"),
    PASS(3, "通过"),
    FREEZE(4, "冻结");

    public Integer code;
    public String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    AuditStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
