package com.yuanlrc.base.bean;

/**
 * 提醒状态
 */
public enum RemindType {

    REMIND(1,"已提醒"),
    NOREMIND(0,"未提醒");

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

    RemindType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
