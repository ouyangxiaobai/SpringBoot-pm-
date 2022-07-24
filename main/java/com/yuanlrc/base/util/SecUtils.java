package com.yuanlrc.base.util;

import java.util.Random;

public class SecUtils {

    /*
    伪支付
    */
    public static boolean dummyPay() {
        Random random = new Random();
        int result = random.nextInt(1000) % 2;
        if (result == 0) {
            return true;
        }
        return false;
    }
}
