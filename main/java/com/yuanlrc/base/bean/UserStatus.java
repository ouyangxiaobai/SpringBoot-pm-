package com.yuanlrc.base.bean;

public enum UserStatus {
    FREEZE(0, "冻结"),
    ACTIVE(1, "可用");

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


    UserStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
