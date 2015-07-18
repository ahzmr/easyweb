package com.wenin819.easyweb.core.utils.tree.adapter;

import com.wenin819.easyweb.core.utils.tree.ITreeNodeAdapter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

/**
 * Map类型树节点
 * @author wenin819@gmail.com
 */
public class MapTreeNodeAdapter<E extends Map> extends ITreeNodeAdapter<E> {

    /**
     * id的key名称
     */
    private String idKey = "id";
    /**
     * pid的key名称
     */
    private String pidKey = "pid";
    /**
     * name的key名称
     */
    private String nameKey = "name";
    /**
     * 是否为父节点
     */
    private String isParent = "getIsParent";

    public MapTreeNodeAdapter() {
    }

    public MapTreeNodeAdapter(String idKey, String pidKey) {
        this.idKey = idKey;
        this.pidKey = pidKey;
    }

    public MapTreeNodeAdapter(String idKey, String pidKey, String nameKey) {
        this.idKey = idKey;
        this.pidKey = pidKey;
        this.nameKey = nameKey;
    }

    public ITreeNodeAdapter<E> setIsParent(String isParent) {
        this.isParent = isParent;
        return this;
    }

    @Override
    public String getId(Map node) {
        return null == node ? null : ObjectUtils.toString(node.get(idKey), null);
    }

    @Override
    public String getPid(Map node) {
        return null == node ? null : ObjectUtils.toString(node.get(pidKey), null);
    }
    @Override
    public String getName(Map node) {
        if(null == nameKey) {
            return null;
        }
        return null == node ? null : ObjectUtils.toString(node.get(nameKey), null);
    }

    @Override
    public Boolean getIsParent(E node) {
        if(null == isParent) {
            return false;
        }
        return null == node ? false : (Boolean) node.get(isParent);
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
