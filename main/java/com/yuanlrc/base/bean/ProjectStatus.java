package com.yuanlrc.base.bean;

import java.util.ArrayList;
import java.util.List;

public enum ProjectStatus {
    NOTSUBMIT(0, "暂存中"),
    RELEASE(1,"发布中"),
    PASSAUDIT(2,"审核未通过"),
    INPUBLIC(3,"公示中"),
    BIDDING(4,"竞价中"),
    SUCCESSFULBIDDING(5,"竞价成功"),
    ENDBIDDING(6,"竞价结束"),
    CLOSED(7,"已成交"),
    REVIEWED(8,"待审核"),
    SHELF(9,"已下架"),
    REGISTERING(10,"报名中"),
    AUCTIONSOON(11,"即将开始竞拍");
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


    ProjectStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }



}
