package com.wenin819.easyweb.filter.liquibase;

import com.wenin819.easyweb.core.utils.StringUtils;
import liquibase.changelog.IncludeAllFilter;

/**
 * liquibase通用文件类型过滤器抽象类
 * @author lc3@yitong.com.cn
 */
public abstract class AbstractIncludeFilter implements IncludeAllFilter {
    protected abstract String[] getFileTypes();
    @Override
    public boolean include(String s) {
        String[] fileTypes = getFileTypes();
        String type = getFileType(s);
        if(null == fileTypes || StringUtils.isBlank(type)) {
            return false;
        }

        for (String fileType : fileTypes) {
            if(type.equalsIgnoreCase(fileType)) {
                return true;
            }
        }
        return false;
    }

    protected String getFileType(String s) {
        if(null == s) {
            return "";
        }
        int i = s.lastIndexOf('.');
        return -1 == i ? "" : s.substring(i + 1);
    }
}
