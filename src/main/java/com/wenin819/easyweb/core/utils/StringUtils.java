package com.wenin819.easyweb.core.utils;

/**
 * String工具类 Created by wenin819@gmail.com on 2014/4/9.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String capitalizeAll(String str) {
        if (null == str || str.isEmpty()) {
            return str;
        }
        StringBuilder s = new StringBuilder(str.length());
        boolean isUpper = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ('_' == c) {
                isUpper = true;
                continue;
            }
            if (isUpper) {
                s.append(Character.toUpperCase(c));
                isUpper = false;
            } else {
                s.append(Character.toLowerCase(c));
            }
        }
        return s.toString();
    }
}
