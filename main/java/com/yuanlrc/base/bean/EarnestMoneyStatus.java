package com.yuanlrc.base.bean;

/**
 * 报名保险金状态
 */
public enum EarnestMoneyStatus {

    NOT_RETURN(1,"未返还"),
    HAS_RETURN(2,"已返还"),
    BREACH(3,"违约");

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

    EarnestMoneyStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
