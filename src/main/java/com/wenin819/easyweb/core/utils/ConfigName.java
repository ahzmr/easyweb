package com.wenin819.easyweb.core.utils;

/**
 * 本工程系统配置配置名称统一管理类
 * @author wenin819@gmail.com
 */
public class ConfigName {

    /**
     * 数据库类型： 可选值为 oracle, db2, mysql等
     */
    public static final String JDBC_TYPE = "jdbc.type";

    /**
     * 配置文件刷新时间，单位秒
     */
    public static final String CONFIG_FILE_REFRESH_SECOND = "config_file_refresh_second";
    public static final int CONFIG_FILE_REFRESH_SECOND_DEFVAL = 0;    // 不启用

    /**
     * 配置文件刷新时间，单位秒
     */
    public static final String SYSTEM_CACHE_REFRESH_SECOND = "system_cache_refresh_second";
    public static final int SYSTEM_CACHE_REFRESH_SECOND_DEFVAL = 300;    // 5分钟

    /**
     * 扩展配置文件的路径，多个路径用分号隔开
     */
    public static final String EXT_CONFIG_FILES = "extconfig.paths";

    /**
     * 扩展配置实现类，多个Bean用分号隔开
     */
    public static final String EXT_CONFIG_BEANs = "extconfig.beans";

    /**
     * 配置数据库名称
     */
    public static final String SCHAME_CONFIGPLAT = "schema.configPlat";
    public static final String SCHAME_CONFIGPLAT_DEFVAL = "easyweb";
    /**
     * 业务数据库名称
     */
    public static final String SCHEMA_INTERPLAT = "schema.interPlat";
    public static final String SCHEMA_INTERPLAT_DEFVAL = "inter";

    /**
     * 主键生成策略，可选值为sequence，与UUID
     */
    public static final String KEY_GENERATOR_TYPE = "key.generator_type";
    public static final String KEY_GENERATOR_TYPE_DEFVAL = "sequence";
    /**
     * 表序列主键缓存值
     */
    public static final String SEQUENCE_KEY_CACHE_NUM = "key.sequence_key_cache_num";
    public static final int SEQUENCE_KEY_CACHE_NUM_DEFVAL = 10;
    /**
     * 表序列主键默认值
     */
    public static final String SEQUENCE_KEY_DEFAULT_VALUE = "key.sequence_key_default_value";
    public static final int SEQUENCE_KEY_DEFAULT_VALUE_DEFVAL = 1000;

    /**
     * 超级管理员的用户ID配置项
     */
    public static final String SYSTEM_SUPER_ADMIN_USER_IDS = "system.super_admin.user_ids";

    /**
     * 树形实体根默认ID
     */
    public static final String treeEntityRootId() {
        return ConfigUtils.get().getValue("entity.tree_entity_root_id", "0");
    }
}
