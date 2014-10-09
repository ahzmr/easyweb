package com.wenin819.easyweb.core.util;

import com.wenin819.easyweb.core.exception.DataException;
import com.wenin819.easyweb.core.security.CodeUtils;
import com.wenin819.easyweb.system.model.SysUser;

import org.apache.shiro.crypto.hash.Sha512Hash;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 安全工具类
 * Created by wenin819@gmail.com on 2014-10-08.
 */
public class SecurityUtils extends org.apache.shiro.SecurityUtils {

    public static final int HASH_ITERATIONS = 1024;
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * 获得当前用户
     * @return
     */
    public static SysUser getCurrentUser() {
        return (SysUser) getSubject().getPrincipal();
    }

    /**
     * 生成保存到数据库的最终密码
     * @param pwd 密码
     * @param salt 盐值
     * @return
     */
    public static String genFinalPasswd(String pwd, String salt) {
        return new Sha512Hash(pwd, salt, HASH_ITERATIONS).toBase64();
    }

    /**
     * 生成HMacSha256编码
     * @param data 数据
     * @param key key
     * @return
     */
    public static String genHMacSha256(String data, String key) {
        SecretKeySpec signingKey = new SecretKeySpec(CodeUtils.toBytes(key), HMAC_SHA256_ALGORITHM);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            return CodeUtils.toBase64String(mac.doFinal(CodeUtils.toBytes(data)));
        } catch (Exception e) {
            throw new DataException("生成HMacSha256编码失败", e);
        }
    }
}
