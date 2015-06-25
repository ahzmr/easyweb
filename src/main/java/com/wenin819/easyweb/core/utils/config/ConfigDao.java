package com.wenin819.easyweb.core.utils.config;

import java.util.Date;
import java.util.Properties;

/**
 * 扩展配置查询接口
 * @author wenin819@gmail.com
 */
public interface ConfigDao {

    /**
     * 根据时间查询最新配置
     * @param date 上次更新时间，为null代表第一次更新
     * @return 配置
     */
    Properties queryConfig(Date date);

}
