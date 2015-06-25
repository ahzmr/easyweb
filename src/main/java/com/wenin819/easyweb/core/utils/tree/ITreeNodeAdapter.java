package com.wenin819.easyweb.core.utils.tree;

/**
 * 树节点适配器，将一个普通的Object对象适配成树节点
 * @author wenin819@gmail.com
 */
public interface ITreeNodeAdapter<E> {

    /**
     * 得到当前节点ID
     * @return 当前节点ID
     */
    String getId(E node);

    /**
     * 得到父节点ID
     * @return 父节点ID
     */
    String getPid(E node);

}
