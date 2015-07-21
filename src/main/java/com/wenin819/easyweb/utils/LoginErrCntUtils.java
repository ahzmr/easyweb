package com.wenin819.easyweb.utils;

import com.wenin819.easyweb.core.utils.CacheUtils;
import com.wenin819.easyweb.core.utils.Configs;
import com.wenin819.easyweb.core.utils.SecurityUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 错误登陆次数管理工具
 * @author wenin819@gmail.com
 * @date 2015-07-21.
 */
public class LoginErrCntUtils {

    private static final AtomicInteger defaultVal = new AtomicInteger(0);

    /**
     * 检查是否达到最大错误登陆次数
     * @param username 用户名
     * @return
     */
    public static String checkIsMaxLoginErrCnt(String username) {
        int maxErrCnt = Configs.systemLoginMaxErrCnt();
        if(CacheUtils.get(SecurityUtils.ERR_LOGIN_CNT_CACHE_KEY, username, defaultVal).get() >= maxErrCnt) {
            return "用户密码错误次数大于" + maxErrCnt + "次，请过5分钟后再试";
        } else {
            return null;
        }
    }

    /**
     * 增加错误登陆次数
     * @param username 用户名
     */
    public static void incrementLoginErrCnt(String username) {
        AtomicInteger atomicInteger = CacheUtils.get(SecurityUtils.ERR_LOGIN_CNT_CACHE_KEY, username, AtomicInteger.class, null);
        if(null == atomicInteger) {
            synchronized (SecurityUtils.ERR_LOGIN_CNT_CACHE_KEY) {
                atomicInteger = CacheUtils.get(SecurityUtils.ERR_LOGIN_CNT_CACHE_KEY, username, AtomicInteger.class, null);
                if(null == atomicInteger) {
                    atomicInteger = new AtomicInteger(1);
                    CacheUtils.put(SecurityUtils.ERR_LOGIN_CNT_CACHE_KEY, username, atomicInteger);
                    return;
                }
            }
        }
        atomicInteger.incrementAndGet();
    }
}
