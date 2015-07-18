package com.wenin819.easyweb.core.utils;

/**
 * @author wenin819@gmail.com
 * @date 2015-07-17.
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 系统常用日期格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 系统常用日期格式集
     */
    public static final String[] DATE_FORMATS = new String[] {DATE_FORMAT, "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd"};
}
