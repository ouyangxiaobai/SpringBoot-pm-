package com.yuanlrc.base.bean;

/**
 * 竞拍状态
 */
public enum BiddingStatus {

    LEADING(1,"领先"),
    OUT(0,"出局");

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

    BiddingStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
