package com.wenin819.easyweb.core.utils.config;

/**
 * 系统配置更新监听器
 * @author wenin819@gmail.com
 */
public interface ConfigListener {

    /**
     * 系统配置项改变
     * @param key 配置项
     * @param oldVal 旧值
     * @param newVal 新值
     */
    void onChange(String key, String oldVal, String newVal);

}
