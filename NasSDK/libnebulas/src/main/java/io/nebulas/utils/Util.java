package io.nebulas.utils;

/**
 * 星云工具类
 * Created by donald99 on 18/5/23.
 */

public class Util {
    public static String getRandomCode(int length){
        String random = "";

        String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < length; i++) {
            double v = Math.random() * abc.length();
            v = Math.floor(v);
            random += abc.charAt((int) v);
        }

        return random;
    }
}
