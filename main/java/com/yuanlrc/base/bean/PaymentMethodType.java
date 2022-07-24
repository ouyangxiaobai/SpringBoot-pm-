package com.yuanlrc.base.bean;

public enum PaymentMethodType {
    ONLINE(0,"线上订单支付"),
    OFFLINE(1,"线下门店支付"),
    OTHER(2,"其他支付");

    private int code;

    private String value;

    PaymentMethodType(int code,String value){
        this.code=code;
        this.value=value;
    }

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
}
