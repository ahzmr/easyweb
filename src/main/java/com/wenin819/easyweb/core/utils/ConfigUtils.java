package com.wenin819.easyweb.core.utils;

import com.wenin819.easyweb.core.utils.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

/**
 * 参数配置管理工具，后来更新的配置，只能覆盖，不能删除配置项
 * @author wenin819@gmail.com
 */
public class ConfigUtils {

    /**
     * 系统引导程序的配置文件
     */
    public static final String BOOTSTRAP_CONFIG_FILE = "classpath:/easyweb.properties";
    /**
     * 系统默认的resource加载器
     */
    public static final DefaultResourceLoader DEFAULT_RESOURCE_LOADER = new DefaultResourceLoader();

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    private static final ConfigManager SYSTEM_CONFIG_MANAGER = ConfigManager.getInstance(BOOTSTRAP_CONFIG_FILE);

    /**
     * 得到系统默认配置器
     * @return
     */
    public static ConfigManager get() {
        return SYSTEM_CONFIG_MANAGER;
    }

    /**
     * 得到指定系统配置管理器
     * @param bootstrapConfigFile 配置启动文件
     * @return
     */
    public static ConfigManager get(String bootstrapConfigFile) {
        return ConfigManager.getInstance(bootstrapConfigFile);
    }



    /**
     * 获得系统配置值
     * @param key 键
     * @param defaultVal 默认值
     * @return string类型值
     */
    public static String getValue(String key, String defaultVal) {
        return get().getValue(key, String.class, defaultVal);
    }

    /**
     * 获得系统配置值
     * @param key 键
     * @return string类型值
     */
    public static String getValue(String key) {
        return get().getValue(key, null);
    }
}
