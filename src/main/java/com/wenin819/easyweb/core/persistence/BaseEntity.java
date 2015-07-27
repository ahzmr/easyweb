package com.wenin819.easyweb.core.persistence;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public abstract class BaseEntity<TE extends IFiledEnum> {

    /**
     * 主键
     */
    private String id;

    private final Map<String, Object> extPropertiesMap = new HashMap<String, Object>();

    public void setFieldLable(TE field, Object v) {
        extPropertiesMap.put(getLableKey(field), v);
    }

    @SuppressWarnings("unchecked")
    public <V> V getFieldLable(TE field) {
        return (V) getExtProperty(getLableKey(field));
    }

    public void setExtProperty(String key, Object v) {
        extPropertiesMap.put(key, v);
    }

    @SuppressWarnings("unchecked")
    public <V> V getExtProperty(String key) {
        return (V) extPropertiesMap.get(key);
    }

    public void removeExtProperty(String key) {
        extPropertiesMap.remove(key);
    }

    private String getLableKey(TE field) {
        return field.getFiledName() + "_Lable";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
