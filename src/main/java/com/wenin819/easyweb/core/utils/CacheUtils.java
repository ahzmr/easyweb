package com.wenin819.easyweb.core.utils;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

/**
 * Cache工具类
 * @author lc3@yitong.com.cn
 */
public class CacheUtils {
	
	private static CacheManager cacheManager = SpringContextUtils.getBean(CacheManager.class);

	private static final String SYS_CACHE = "sysCache";

    private CacheUtils() {}

    /**
     * 获得系统缓存对应的值
     * @param key 项
     * @param defaultVal 默认值
     * @param <T> 值泛型
     * @return
     */
	public static <T> T get(String key, T defaultVal) {
		return get(SYS_CACHE, key, defaultVal);
	}

    /**
     * 获得系统缓存对应的值
     * @param key 项
     * @param classType 值类型
     * @param defaultVal 默认值
     * @param <T> 值泛型
     * @return
     */
	public static <T> T get(String key, Class<T> classType, T defaultVal) {
		return get(SYS_CACHE, key, classType, defaultVal);
	}

    /**
     * 设置系统缓存对应的值
     * @param key 项
     * @param value 值
     * @param <T> 值泛型
     */
	public static <T> void put(String key, T value) {
		put(SYS_CACHE, key, value);
	}

    /**
     * 删除系统缓存对应的项
     * @param key 项
     */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

    /**
     * 获得缓存里的对应值
     * @param cacheName 缓存名称
     * @param key 项
     * @param defaultVal 默认值
     * @param <T> 值泛型
     * @return
     */
	@SuppressWarnings("unchecked")
    public static <T> T get(String cacheName, String key, T defaultVal) {
        Assert.notNull(defaultVal, "defaultVal不能为空！");
        return get(cacheName, key, (Class<T>)defaultVal.getClass(), defaultVal);
	}

    /**
     * 获得缓存里的对应值
     * @param cacheName 缓存名称
     * @param key 项
     * @param classType 值类型
     * @param defaultVal 默认值
     * @param <T> 值泛型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String cacheName, String key, Class<T> classType, T defaultVal) {
        Cache.ValueWrapper valueWrapper = getCache(cacheName).get(key);
		if(null == valueWrapper || null == valueWrapper.get()) {
            return defaultVal;
        }
		Object value = valueWrapper.get();
		if(null == classType || classType.isAssignableFrom(value.getClass())) {
			return (T) value;
		}
        return (T) ConvertUtils.convert(value, classType);
    }

    /**
     * 设置值
     * @param cacheName 缓存名称
     * @param key 项
     * @param value 值
     * @param <T> 值泛型
     */
	public static <T> void put(String cacheName, String key, T value) {
		getCache(cacheName).put(key, value);
	}

    /**
     * 删除缓存对应的项
     * @param cacheName 缓存名称
     * @param key 需要删除的项
     */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).evict(key);
	}
	
	/**
	 * 获得一个Cache，没有则报异常。
	 * @param cacheName 缓存名称
	 * @return
	 */
	private static Cache getCache(String cacheName){
        return cacheManager.getCache(cacheName);
	}

    /**
     * 获得缓存管理器
     * @return
     */
	public static CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
