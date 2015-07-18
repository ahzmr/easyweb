package com.wenin819.easyweb.core.utils.tree.adapter;

import com.wenin819.easyweb.core.utils.tree.ITreeNode;
import com.wenin819.easyweb.core.utils.tree.ITreeNodeAdapter;

/**
 * 标准树结构适配器
 * @author wenin819@gmail.com
 * @date 2015-07-18.
 */
public class TreeNodeAdapter extends ITreeNodeAdapter<ITreeNode> {

    @Override
    public String getId(ITreeNode node) {
        return null == node ? null : node.getId();
    }

    @Override
    public String getPid(ITreeNode node) {
        return null == node ? null : node.getPid();
    }

    @Override
    public String getName(ITreeNode node) {
        return null == node ? null : node.getName();
    }

    @Override
    public Boolean getIsParent(ITreeNode node) {
        return null == node ? null : node.getIsParent();
    }
}
