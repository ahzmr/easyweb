package com.wenin819.easyweb.core.utils;

import javax.servlet.jsp.jstl.core.Config;
import java.util.List;

/**
 * 本工程系统配置配置名称统一管理类
 * @author wenin819@gmail.com
 */
public class Configs {

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
     * 扩展配置文件的路径，多个路径用分号隔开
     */
    public static final String EXT_CONFIG_FILES = "extconfig.paths";

    /**
     * 扩展配置实现类，多个Bean用分号隔开
     */
    public static final String EXT_CONFIG_BEANS = "extconfig.beans";

    /**
     * 配置数据库名称
     */
    public static String schameConfigplat() {
        return ConfigUtils.get().getValue("schema.configPlat", "easyweb");
    }

    /**
     * 主键生成策略，可选值为sequence，与UUID
     */
    public static String keyGeneratorType() {
        return ConfigUtils.get().getValue("key.generator_type", "sequence");
    }
    /**
     * 表序列主键缓存值
     */
    public static int sequenceKeyCacheNum() {
        return ConfigUtils.get().getValue("key.sequence_key_cache_num", 10);
    }
    /**
     * 表序列主键默认值
     */
    public static int sequenceKeyDefaultValue() {
        return ConfigUtils.get().getValue("key.sequence_key_default_value", 1000);
    }

    /**
     * 超级管理员的用户ID配置项
     */
    public static List<String> systemSuperAdminUserIds() {
        return ConfigUtils.get().getValueList("system.super_admin.user_ids", String.class);
    }

    /**
     * 树形实体根默认ID
     */
    public static String treeEntityRootId() {
        return ConfigUtils.get().getValue("entity.tree_entity_root_id", "0");
    }

    /**
     * 最大登陆错误次数
     */
    public static int systemLoginMaxErrCnt() {
        return ConfigUtils.get().getValue("system.login.max_err_cnt", 5);
    }
}
