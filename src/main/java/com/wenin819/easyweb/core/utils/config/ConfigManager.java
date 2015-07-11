package com.wenin819.easyweb.core.utils.config;

import com.wenin819.easyweb.core.utils.*;
import com.wenin819.easyweb.core.utils.observe.ObservableManager;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 参数配置管理工具，后来更新的配置，只能覆盖，不能删除配置项
 * @author wenin819@gmail.com
 */
public class ConfigManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    /**
     * 配置缓存
     */
    private final Properties configChaches = new Properties();
    /**
     * 加载过的配置文件列表
     */
    private final List<String> loadedPropertyFileList = new ArrayList<String>();
    /**
     * 配置监听器
     */
    private final Map<String, Set<ConfigListener>> configlisteners = new HashMap<String, Set<ConfigListener>>();
    /**
     * 上次缓存更新时间
     */
    private Date lastUpdateDate = null;
    /**
     * 扩展Bean最后更新时间
     */
    private Date beanLastUpdateDate = null;
    /**
     * 是否已经初始化
     */
    private boolean hasInit = false;

    /**
     * 工厂构造方法
     * @param bootstrapConfigFile
     * @return
     */
    public static ConfigManager getInstance(String bootstrapConfigFile) {
        return new ConfigManager(bootstrapConfigFile);
    }

    private ConfigManager(String bootstrapConfigFile) {
        init(bootstrapConfigFile);
    }

    private synchronized Properties init(String bootstrapConfigFile) {
        if(hasInit) {
            return configChaches;
        }
        logger.info("开始加载启动配置文件：" + bootstrapConfigFile);
        Resource resource = ConfigUtils.DEFAULT_RESOURCE_LOADER.getResource(bootstrapConfigFile);
        if(null == resource || !resource.exists()) {
            throw new IllegalArgumentException("启动配置文件没有找到：" + bootstrapConfigFile);
        }
        Properties properties;
        try {
            properties = PropertiesLoaderUtils.loadProperties(
                    new EncodedResource(resource, ConfigEnum.DEFAULT_CONFIG_FILE_ENCODING));
            loadProperties(properties);
            logger.info("成功加载启动配置文件：" + bootstrapConfigFile);

            loadExtProperties();

            hasInit = true;
        } catch (IOException e) {
            logger.error("加载启动配置文件失败：" + bootstrapConfigFile, e);
            throw new RuntimeException("加载启动配置文件失败：" + bootstrapConfigFile, e);
        }

        int defualtRefrechTime = ConfigName.CONFIG_FILE_REFRESH_SECOND_DEFVAL;
        int value = getValue(ConfigName.CONFIG_FILE_REFRESH_SECOND, defualtRefrechTime);
        if(value < 0) {
            logger.error("系统配置项{}有问题，为大于等于0的正整数，请检查，暂采用默认时间{}",
                    ConfigName.CONFIG_FILE_REFRESH_SECOND, defualtRefrechTime);
            value = defualtRefrechTime;
        }
        if(value > 0) {
            logger.info("开始启动定时刷新系统配置的定时器……");
            ExecutorUtils.getSystemScheduledExecutorService().scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    loadExtProperties();
                }
            }, value, value, TimeUnit.SECONDS);
            logger.info("成功启动定时刷新系统配置的定时器，更新间隔为{}秒。", value);
        }
        return properties;
    }

    /**
     * 加载扩展配置文件配置
     */
    private synchronized void loadExtProperties() {
        String extConfigFiles = getValue(ConfigName.EXT_CONFIG_FILES);
        logger.debug("开始加载扩展配置文件集合：" + extConfigFiles);
        Properties extProps = new Properties();
        if(StringUtils.isNotBlank(extConfigFiles)) {
            String[] files = extConfigFiles.split(ConfigEnum.SYS_DEFAULT_SPLIT_STR);
            for (String file : files) {
                loadPropFile(file, extProps);
            }
        }
        logger.debug("完成加载扩展配置文件集合：" + extConfigFiles);

        String extConfigBeans = getValue(ConfigName.EXT_CONFIG_BEANs);
        if (StringUtils.isNotBlank(extConfigBeans)) {
            loadBeanProps(extConfigBeans, extProps);
        }

        loadProperties(extProps);
        lastUpdateDate = new Date();
    }

    /**
     * 加载扩展Bean配置
     * @param extConfigBeans 扩展Bean
     * @param extProps 配置
     */
    private void loadBeanProps(final String extConfigBeans, final Properties extProps) {
        if (null != SpringContextUtils.getApplicationContext()) {
            logger.debug("开始加载扩展配置Bean：" + extConfigBeans);
            String[] beans = extConfigBeans.split(ConfigEnum.SYS_DEFAULT_SPLIT_STR);
            for (String bean : beans) {
                Properties properties = null;
                try {
                    ConfigDao configDao = SpringContextUtils.getBean(bean, ConfigDao.class);
                    if (null == configDao) {
                        logger.warn("系统配置持久化bean[{}]不存在，直接忽略", bean);
                        continue;
                    }
                    properties = configDao.queryConfig(beanLastUpdateDate);
                } catch (Exception e) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("执行系统配置持久化接口" + bean + "失败，直接忽略", e);
                    }
                    continue;
                }
                extProps.putAll(properties);
            }
            beanLastUpdateDate = new Date();
            logger.debug("成功加载扩展配置Bean：" + extConfigBeans);
        } else if (StringUtils.isNotBlank(extConfigBeans)) {
            logger.warn("由于Spring没有初始化完成，延迟加载扩展配置Bean：" + extConfigBeans);
            ObservableManager.get().getObservable(SpringContextUtils.class).addObserver(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    o.deleteObserver(this);
                    Properties beanProps = new Properties();
                    loadBeanProps(extConfigBeans, beanProps);
                    loadProperties(beanProps);
                }
            });
        }
    }

    /**
     * 加载配置文件
     * @param configFile 配置文件路径
     * @param props 已有配置
     */
    private void loadPropFile(String configFile, Properties props) {
        Assert.notNull(configFile, "configFile不能为空！");
        Assert.notNull(props, "props不能为空！");
        try {
            Resource resource = ConfigUtils.DEFAULT_RESOURCE_LOADER.getResource(configFile);
            if(null == resource || !resource.exists()) {
                logger.warn("扩展配置文件不存在：{}，直接忽略", configFile);
                return;
            }
            if(null != lastUpdateDate && resource.lastModified() <= lastUpdateDate.getTime()) {
                return;
            }
            PropertiesLoaderUtils.fillProperties(props, new EncodedResource(resource,
                    ConfigEnum.DEFAULT_CONFIG_FILE_ENCODING));
            logger.debug("成功加载扩展配置文件：{}", configFile);
        } catch (IOException e) {
            logger.warn("加载启动配置文件失败：" + configFile, e);
            return;
        }
        if(!loadedPropertyFileList.contains(configFile)) {
            loadedPropertyFileList.add(configFile);
        }
    }

    /**
     * 加载配置信息到系统配置缓存
     * @param properties 配置内容
     */
    private synchronized void loadProperties(Properties properties) {
        Assert.notNull(properties, "properties不能为空！");
        for (String key : properties.stringPropertyNames()) {
            String oldVal = configChaches.getProperty(key);
            String newVal = properties.getProperty(key);
            if(null == oldVal || !oldVal.equals(newVal)) {
                onConfigItemChange(key, oldVal, newVal);
                configChaches.put(key, newVal);
            }
        }
    }

    /**
     * 配置修改通知接口
     * @param key 配置项
     * @param oldVal 旧值
     * @param newVal 新值
     */
    private void onConfigItemChange(String key, String oldVal, String newVal) {
        if(logger.isDebugEnabled()) {
            logger.debug("配置项变更: {}, 旧值: {}, 新值: {}", key, oldVal, newVal);
        }
        Set<ConfigListener> configListeners = getConfigListenersByKey(key, false);
        if(null != configListeners) {
            for (ConfigListener configListener : configListeners) {
                try {
                    configListener.onChange(key, oldVal, newVal);
                } catch (Exception e) {
                    if(logger.isWarnEnabled()) {
                        logger.warn("配置项" + key + "变更，监听接口" + configListener.getClass() + "处理异常", e);
                    }
                }
            }
        }
    }

    /**
     * 注册配置监听器
     * @param key 监听的key，为空时监听所有变化，多个项可用分号分隔
     * @param listener 监听器
     */
    public void registerConfigListener(String key, ConfigListener listener) {
        synchronized (configlisteners) {
            String[] keys = getKeys(key);
            if (null == keys) {
                getConfigListenersByKey(null, true).add(listener);
            } else {
                for (String s : keys) {
                    getConfigListenersByKey(s, true).add(listener);
                }
            }
        }
    }

    /**
     * 取消注销监听器
     * @param key 监听的key，为空时监听所有变化，多个项可用分号分隔
     * @param listener 监听器
     */
    public void unRegisterConfigListener(String key, ConfigListener listener) {
        synchronized (configlisteners) {
            String[] keys = getKeys(key);
            if(null == keys) {
                Set<ConfigListener> configListeners = getConfigListenersByKey(null, false);
                if (null != configListeners) {
                    configListeners.remove(listener);
                }
            } else {
                for (String k : keys) {
                    Set<ConfigListener> configListeners = getConfigListenersByKey(k, false);
                    if (null != configListeners) {
                        configListeners.remove(listener);
                    }
                }
            }
        }
    }

    /**
     * 根据key获得监听器列表
     * @param key key
     * @param autoCreate 是否自动创建
     * @return
     */
    private Set<ConfigListener> getConfigListenersByKey(String key, boolean autoCreate) {
        synchronized (configlisteners) {
            Set<ConfigListener> configListeners = configlisteners.get(key);
            if (null == configListeners && autoCreate) {
                configListeners = new LinkedHashSet<ConfigListener>();
                configlisteners.put(key, configListeners);
            }
            return configListeners;
        }
    }

    /**
     * 拆分配置key为具体的key集合
     * @param key 原始Key
     * @return
     */
    private static String[] getKeys(String key) {
        if(null == key) {
            return null;
        }
        return key.trim().split(ConfigEnum.SYS_DEFAULT_SPLIT_STR);
    }

    /**
     * 获得系统配置值
     * @param key 键
     * @param classType 值类型
     * @param defaultVal 默认值
     * @param <T> 值泛型类型
     * @return 特定类型值
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, Class<T> classType, T defaultVal) {
        Assert.notNull(key, "key不能为空");
        Assert.notNull(classType, "classType不能为空");
        String strVal = configChaches.getProperty(key);
        if(null == strVal || strVal.isEmpty()) {
            return defaultVal;
        }
        if(CharSequence.class.isAssignableFrom(classType)) {
            return (T) strVal;
        } else {
            return (T) ConvertUtils.convert(strVal, classType);
        }
    }

    /**
     * 获得系统配置值
     * @param key 键
     * @param defaultVal 默认值，不能为空
     * @param <T> 泛型类型
     * @return 特定类型值
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue(String key, T defaultVal) {
        Assert.notNull(defaultVal, "defaultVal不能为空");
        return getValue(key, (Class<T>)defaultVal.getClass(), defaultVal);
    }

    /**
     * 获得系统配置值
     * @param key 键
     * @param defaultVal 默认值
     * @return string类型值
     */
    public String getValue(String key, String defaultVal) {
        return getValue(key, String.class, defaultVal);
    }

    /**
     * 获得系统配置值
     * @param key 键
     * @return string类型值
     */
    public String getValue(String key) {
        return getValue(key, null);
    }

    /**
     * 获得系统列表型配置值
     * @param key 键
     * @param classType 值元素类型
     * @param <T> 值元素泛型
     * @return
     */
    public <T> List<T> getValueList(String key, Class<T> classType) {
        String valueStr = getValue(key);
        if(StringUtils.isBlank(valueStr)) {
            return new ArrayList<T>(0);
        }
        String[] strings = valueStr.split(ConfigEnum.SYS_DEFAULT_SPLIT_STR);
        List<T> list = new ArrayList<T>(strings.length);
        for (String string : strings) {
            list.add((T) ConvertUtils.convert(string, classType));
        }
        return list;
    }

    /**
     * 得到最终字符串，替换系统中的变量，变量用"${varName}"表示，"$$"代表一个"$"
     * @param str 原始字符串
     * @return
     */
    public String fillConfigs(String str) {
        if(StringUtils.isBlank(str)) {
            return str;
        }
        boolean in = false; // 是否在变量表达式中
        StringBuilder sb = null;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(!in) {   // 不在变量表达式中
                if(ConfigEnum.SYS_DEFAULT_VAR_SIGN == c) { // 刚进变量表达式
                    in = true;
                    if(null == sb) {    // 补上第一变量之前的串
                        sb = new StringBuilder(str.substring(0, i));
                    }
                } else if(null != sb) { // 直到现在没有变量，如果一直没有变量，直接返回原串，提高效率
                    sb.append(c);
                }
                continue;
            }
            // 以下为在变量表达式中
            in = false;
            if(ConfigEnum.SYS_DEFAULT_VAR_SIGN == c) { // 双"$$"代表一个"$"符
                sb.append(c);
            } else if('{' == c) {
                int end = i + 1;
                while (end < str.length()) {
                    if('}' == str.charAt(end)) {
                        break;
                    }
                    end++;
                }
                String varName = str.substring(i + 1, end);
                String value = getValue(varName);
                if(null == value) { // 如果没有配置对应变量值，直接原样输出
                    sb.append(ConfigEnum.SYS_DEFAULT_VAR_SIGN).append('{').append(varName).append('}');
                } else {
                    sb.append(value);
                }
                i = end;
            } else {    // 不合法的表达式，直接原样输出
                sb.append(ConfigEnum.SYS_DEFAULT_VAR_SIGN);
                sb.append(c);
            }
        }
        return null == sb ? str : sb.toString();
    }

    /**
     * 通过前缀获取键集合
     * @param prefix 前缀
     * @return
     */
    public Set<String> keySetStartWith(String prefix) {
        if(StringUtils.isBlank(prefix)) {
            prefix = null;
        }
        Set<String> keys = new LinkedHashSet<String>();
        for (Object key : configChaches.keySet()) {
            if(!(key instanceof String)) {
                continue;
            }
            if(null == prefix || ((String) key).startsWith(prefix)) {
                keys.add((String) key);
            }
        }
        return keys;
    }

}
