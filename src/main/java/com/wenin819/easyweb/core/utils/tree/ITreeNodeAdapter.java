package com.wenin819.easyweb.core.utils.tree;

/**
 * 树节点适配器，将一个普通的Object对象适配成树节点
 * @author wenin819@gmail.com
 */
public abstract class ITreeNodeAdapter<E> {

    /**
     * 得到当前节点ID
     * @return 当前节点ID
     */
    public abstract String getId(E node);

    /**
     * 得到父节点ID
     * @return 父节点ID
     */
    public abstract String getPid(E node);

    /**
     * 得到节点名称
     * @param node 节点
     * @return
     */
    public String getName(E node) {
        return null;
    }

    /**
     * 判断节点是否为父节点
     * @param node 节点
     * @return
     */
    public Boolean getIsParent(E node) {
        return null;
    }

}
