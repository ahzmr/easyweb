package com.wenin819.easyweb.codegen.vo;

import com.wenin819.easyweb.codegen.util.JavaTypeResolverUtil;
import com.wenin819.easyweb.core.util.StringUtils;

import java.sql.JDBCType;

/**
 * 表字段对应的代码生成辅助类.
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public class TableField {

    private String name;
    private String methodName;
    private String collumnName;
    private JDBCType jdbcType;
    private int length;
    private int scale;
    private boolean nullable;
    private String remarks;

    public String getName() {
        return name;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getCollumnName() {
        return collumnName;
    }

    public void setCollumnName(String collumnName) {
        this.collumnName = collumnName;
        this.methodName = StringUtils.capitalizeAll(collumnName);
        this.name = StringUtils.uncapitalize(this.methodName);
    }

    public JDBCType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JDBCType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJdbcTypeName() {
        return null == jdbcType ? null : jdbcType.getName();
    }

    public Class getJavaType() {
        return JavaTypeResolverUtil.calculateJavaType(this);
    }

    public String getJavaTypeName() {
        Class jdbcType = getJavaType();
        return null == jdbcType ? null : jdbcType.getSimpleName();
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "TableField{" +
               "name='" + name + '\'' +
               ", methodName='" + methodName + '\'' +
               ", collumnName='" + collumnName + '\'' +
               ", jdbcType=" + jdbcType +
               ", javaType=" + getJavaTypeName() +
               ", length=" + length +
               ", scale=" + scale +
               ", nullable=" + nullable +
               ", remarks='" + remarks + '\'' +
               '}';
    }
}
