package com.wenin819.easyweb.codegen.vo;


import com.wenin819.easyweb.core.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表对应的代码生成辅助类.
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public class TableEntity {

    private String schema;
    private String name;
    private String remarks;
    private String className;
    private List<TableField> fieldList;
    private List<String> primaryKeyList = null;

    public List<TableField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<TableField> fieldList) {
        this.fieldList = fieldList;
    }

    public String getTableId() {
        return schema + '.' + name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.className = StringUtils.capitalizeAll(name);
    }

    public String getClassName() {
        return className;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void addPrimaryKeyColumn(String collumnName) {
        if (null == collumnName) {
            return;
        }
        if (null == primaryKeyList) {
            primaryKeyList = new ArrayList<>();
        }
        if (!primaryKeyList.contains(collumnName)) {
            primaryKeyList.add(collumnName);
        }
    }

    public TableField getPrimaryField() {
        if (primaryKeyList.isEmpty() || primaryKeyList.size() > 1) {
            return null;
        }
        for (TableField tableField : fieldList) {
            if (primaryKeyList.contains(tableField.getCollumnName())) {
                return tableField;
            }
        }
        return null;
    }

    public List<TableField> getFieldListWithoutKey() {
        List<TableField> list = new ArrayList<>(fieldList.size());
        TableField primaryField = getPrimaryField();
        for (TableField tableField : fieldList) {
            if (tableField != primaryField) {
                list.add(tableField);
            }
        }
        return list;
    }

    public Set<String> getImportTypeList() {
        if (null != this.fieldList) {
            Set<String> importTypeSet = new HashSet<>();
            for (TableField tableField : fieldList) {
                Class javaType = tableField.getJavaType();
                if (null != javaType && !javaType.getName().startsWith("java.lang")) {
                    importTypeSet.add(javaType.getName());
                }
            }
            return importTypeSet;
        }
        return null;
    }

    @Override
    public int hashCode() {
        return getTableId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getTableId().equals(obj);
    }

    @Override
    public String toString() {
        return "TableEntity{" +
               "schema='" + schema + '\'' +
               ", name='" + name + '\'' +
               ", remarks='" + remarks + '\'' +
               ", className='" + className + '\'' +
               ", fieldList=" + fieldList +
               ", primaryKeyList=" + primaryKeyList +
               '}';
    }
}
