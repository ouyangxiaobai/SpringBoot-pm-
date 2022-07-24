package com.yuanlrc.base.bean;

/**
 * 前台用户竞拍项目状态
 */
public enum UserBiddingStatus {

    INPUBLIC(3,"公示中"),
    BIDDING(4,"竞价中"),
    SUCCESSFULBIDDING(5,"竞价成功"),
    ENDBIDDING(6,"竞价结束"),
    CLOSED(7,"已成交");

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

    UserBiddingStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
