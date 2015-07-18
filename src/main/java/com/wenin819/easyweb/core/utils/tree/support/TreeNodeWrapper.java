package com.wenin819.easyweb.core.utils.tree.support;

import com.wenin819.easyweb.core.utils.tree.ITreeNode;
import com.wenin819.easyweb.core.utils.tree.ITreeNodeAdapter;
import org.springframework.util.Assert;

/**
 * TreeNode装饰类
 * @author wenin819@gmail.com
 * @date 2015-07-18.
 */
public class TreeNodeWrapper<E> implements ITreeNode {

    private E node;
    private ITreeNodeAdapter adapter;

    public TreeNodeWrapper(E node, ITreeNodeAdapter adapter) {
        Assert.notNull(adapter, "adapter不能为空");
        this.node = node;
        this.adapter = adapter;
    }

    @Override
    public String getId() {
        return adapter.getId(node);
    }

    @Override
    public String getPid() {
        return adapter.getPid(node);
    }

    @Override
    public String getName() {
        return adapter.getName(node);
    }

    @Override
    public Boolean getIsParent() {
        return adapter.getIsParent(node);
    }
}
