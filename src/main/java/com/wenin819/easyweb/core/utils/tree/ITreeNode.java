package com.wenin819.easyweb.core.utils.tree;

/**
 * 标准树结点
 * @author wenin819@gmail.com
 * @date 2015-07-18.
 */
public interface ITreeNode {

    /**
     * 得到当前节点ID
     * @return 当前节点ID
     */
    String getId();

    /**
     * 得到父节点ID
     * @return 父节点ID
     */
    String getPid();

    /**
     * 得到节点名称
     * @return
     */
    String getName();

    /**
     * 是否是父节点
     * @return
     */
    Boolean getIsParent();

}
