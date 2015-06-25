package com.wenin819.easyweb.filter.liquibase;

/**
 * 只保留Xml类型文件过滤器
 * @author lc3@yitong.com.cn
 */
public class IncludeXmlFilter extends AbstractIncludeFilter {
    @Override
    protected String[] getFileTypes() {
        return new String[]{"xml"};
    }
}
