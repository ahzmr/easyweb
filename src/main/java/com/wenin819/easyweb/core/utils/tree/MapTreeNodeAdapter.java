package com.wenin819.easyweb.core.utils.tree;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

/**
 * Map类型树节点
 * @author wenin819@gmail.com
 */
public class MapTreeNodeAdapter<E extends Map> implements ITreeNodeAdapter<E> {

    /**
     * id的key名称
     */
    private String idKey = "id";
    /**
     * pid的key名称
     */
    private String pidKey = "pid";

    public MapTreeNodeAdapter() {
    }

    public MapTreeNodeAdapter(String idKey, String pidKey) {
        this.idKey = idKey;
        this.pidKey = pidKey;
    }

    @Override
    public String getId(Map node) {
        return null == node ? null : ObjectUtils.toString(node.get(idKey), null);
    }

    @Override
    public String getPid(Map node) {
        return null == node ? null : ObjectUtils.toString(node.get(pidKey), null);
    }

    private static final ITreeNodeAdapter<Map> DEFAULT = new MapTreeNodeAdapter<Map>();

    /**
     * 得到默认Map树节点适配器
     * @return 默认Map树节点适配器
     */
    public static ITreeNodeAdapter<Map> getDefaultInstance() {
        return DEFAULT;
    }
}
