package com.wenin819.easyweb.core.utils;

import java.nio.charset.Charset;

/**
 * 类配置信息
 * @author wenin819@gmail.com
 */
public class ConfigEnum {

    public static final String SYS_DEFAULT_SPLIT_STR = ";"; // 系统默认分隔符
    public static final char SYS_DEFAULT_VAR_SIGN = '$';    // 变量开始标志

    public static final Charset DEFAULT_CONFIG_FILE_ENCODING = Charset.forName("UTF-8");    // 默认的配置文件编码

    public static final String FILED_NAME_CREATE_TIME = "createDate";   // 创建时间字段名称
    public static final String FILED_NAME_CREATE_BY = "createBy";   // 创建人字段名称
    public static final String FILED_NAME_UPDATE_TIME = "updateDate";   // 更新时间字段名称
    public static final String FILED_NAME_UPDATE_BY = "updateBy";   // 更新人字段名称

    // 显示/隐藏
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    // 是/否
    public static final String YES = "1";
    public static final String NO = "0";

    // 删除标记（0：正常；1：删除；2：审核；）
    public static final String FIELD_DEL_FLAG = "delFlag";
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";

}
