package com.wenin819.easyweb.core.db;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public abstract class BaseEntity<TE extends IFiledEnum> {

    private final Map<String, Object> extPropertiesMap = new HashMap<>();

    public void setFieldLable(TE field, Object v) {
        extPropertiesMap.put(getLableKey(field), v);
    }

    public <V> V getFieldLable(TE field) {
        return (V) extPropertiesMap.get(getLableKey(field));
    }

    public void setExtProperty(String key, Object v) {
        extPropertiesMap.put(key, v);
    }

    public <V> V getExtProperty(String key) {
        return (V) extPropertiesMap.get(key);
    }

    private String getLableKey(TE field) {
        return field.getFiledName() + "_Lable";
    }
}
