package com.wenin819.easyweb.core.utils.tree;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wenin819@gmail.com
 */
public class ObjectTreeNodeAdapter<E> implements ITreeNodeAdapter<E> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * id的key名称
     */
    private String idKey = "id";
    /**
     * pid的key名称
     */
    private String pidKey = "pid";

    public ObjectTreeNodeAdapter() {
    }

    public ObjectTreeNodeAdapter(String idKey, String pidKey) {
        this.idKey = idKey;
        this.pidKey = pidKey;
    }

    @Override
    public String getId(Object node) {
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
    public String getPid(Object node) {
        try {
            return null == node ? null : ObjectUtils.toString(PropertyUtils.getProperty(node, pidKey), null);
        } catch (Exception e) {
            if(logger.isWarnEnabled()) {
                logger.warn("获取Object类型树节点的pid失败", e);
            }
        }
        return null;
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
