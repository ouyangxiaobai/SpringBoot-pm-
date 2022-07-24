package com.yuanlrc.base.bean;

/**
 * 支付用户类型
 */
public enum PayUserType {

    HOMEUSER(1,"前台用户"),
    ORGANIZATION(2,"企业机构");

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

    PayUserType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
