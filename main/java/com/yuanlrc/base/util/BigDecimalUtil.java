package com.yuanlrc.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

    /**
     * bigDecimal和int数据相乘
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal multiply(BigDecimal a,Integer b){
        BigDecimal bigDecimal = new BigDecimal(b);
        BigDecimal multiply = a.multiply(bigDecimal);
        return multiply;
    }

    /**
     * bigDecimal和double数据相乘
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal multiply(BigDecimal a, Double b){
        BigDecimal bigDecimal = new BigDecimal(b);
        BigDecimal multiply = a.multiply(bigDecimal);
        return multiply;
    }



    /**
     * 四舍五入保留小数
     * @param d
     * @return
     */
    public static BigDecimal format(BigDecimal d,int digits){
        BigDecimal bg = d.setScale(digits, RoundingMode.HALF_UP);
        return bg;
    }

}
