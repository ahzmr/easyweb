package com.wenin819.easyweb.core.utils.tree.adapter;

import com.wenin819.easyweb.core.utils.tree.ITreeNodeAdapter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenin819@gmail.com
 */
public class ObjectTreeNodeAdapter<E> extends ITreeNodeAdapter<E> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * id的key名称
     */
    private String idKey = "id";
    /**
     * pid的key名称
     */
    private String pidKey = "parentId";
    /**
     * name的key名称
     */
    private String nameKey = "name";
    /**
     * 是否为父节点
     */
    private String isParent = "getIsParent";

    public ObjectTreeNodeAdapter() {
    }

    public ObjectTreeNodeAdapter(String idKey, String pidKey) {
        this.idKey = idKey;
        this.pidKey = pidKey;
    }

    public ObjectTreeNodeAdapter(String idKey, String pidKey, String nameKey) {
        this.idKey = idKey;
        this.pidKey = pidKey;
        this.nameKey = nameKey;
    }

    public ITreeNodeAdapter<E> setIsParent(String isParent) {
        this.isParent = isParent;
        return this;
    }

    @Override
    public String getId(E node) {
        try {
            return null == node ? null : ObjectUtils.toString(PropertyUtils.getProperty(node, idKey), null);
        } catch (Exception e) {
            if(logger.isWarnEnabled()) {
                logger.warn("获取Object类型树节点的id失败", e);
            }
        }
        return null;
    }

    @Override
    public String getPid(E node) {
        try {
            return null == node ? null : ObjectUtils.toString(PropertyUtils.getProperty(node, pidKey), null);
        } catch (Exception e) {
            if(logger.isWarnEnabled()) {
                logger.warn("获取Object类型树节点的pid失败", e);
            }
        }
        return null;
    }

    @Override
    public String getName(E node) {
        if(null == nameKey) {
            return null;
        }
        try {
            return null == node ? null : ObjectUtils.toString(PropertyUtils.getProperty(node, nameKey), null);
        } catch (Exception e) {
            if(logger.isWarnEnabled()) {
                logger.warn("获取Object类型树节点的name失败", e);
            }
        }
        return null;
    }

    @Override
    public Boolean getIsParent(E node) {
        if(null == isParent) {
            return false;
        }
        try {
            return null == node ? false : (Boolean) PropertyUtils.getProperty(node, isParent);
        } catch (Exception e) {
            if(logger.isWarnEnabled()) {
                logger.warn("获取Object类型树节点的isParent失败", e);
            }
        }
        return super.getIsParent(node);
    }

    private static final ITreeNodeAdapter<Object> DEFAULT = new ObjectTreeNodeAdapter<Object>();

    /**
     * 得到默认Object树节点适配器
     * @return 默认Map树节点适配器
     */
    public static ITreeNodeAdapter<Object> getDefaultInstance() {
        return DEFAULT;
    }
}
