package com.wenin819.easyweb.core.security;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;

/**
 * 编码工具类
 * Created by wenin819@gmail.com on 2014-10-09.
 */
public class CodeUtils {
    /**
     * 将字节数组转成Base64
     * @param bytes 字节数组
     * @return Base64
     */
    public static String toBase64String(byte[] bytes) {
        if(null == bytes) {
            return null;
        }
        return Base64.encodeToString(bytes);
    }

    /**
     * 将字符串转成字节数组
     * @param str 字符串
     * @return 字节数组
     */
    public static byte[] toBytes(String str) {
        if(null == str) {
            return null;
        }
        return CodecSupport.toBytes(str);
    }
}
