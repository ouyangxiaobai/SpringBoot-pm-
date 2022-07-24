package com.yuanlrc.base.bean;

/**
 * 登录者类型
 */
public enum LoginType {
    ADMINISTRATOR(1,"管理员"),
    ORGANIZATION(2,"机构");

    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    LoginType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
