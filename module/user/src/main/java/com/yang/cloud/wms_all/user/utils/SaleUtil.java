package com.yang.cloud.wms_all.user.utils;

import java.util.Random;

public class SaleUtil {

    private static final char[] chars = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * 如果不加这个默认是 $1$
     */
    public static final String SALT_PREFIX = "$WMS$";

    public static String getSalt() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int index = new Random().nextInt(chars.length);
            stringBuffer.append(chars[index]);
        }
        return stringBuffer.toString();
    }
}
