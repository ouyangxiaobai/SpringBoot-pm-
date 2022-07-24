package com.yuanlrc.base.bean;

public enum CycleType {
    FIVE(5),
    TEN(10),
    FIFTEEN(15);


    private int days;
    
    CycleType(int days){
        this.days=days;
    }

    public int getdays() {
        return days;
    }

    public void setdays(int days) {
        this.days = days;
    }
    
}
