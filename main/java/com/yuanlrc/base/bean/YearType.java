package com.yuanlrc.base.bean;

public enum YearType {

    YEARS(1,"有年限"),
    NOTYEARS(0,"无年限");

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


    YearType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
