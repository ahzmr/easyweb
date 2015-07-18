package com.wenin819.easyweb.core.persistence.mybatis;

import com.wenin819.easyweb.core.persistence.TreeEntity;

/**
 * 树形结构实体的基础Dao
 * @author wenin819@gmail.com
 * @date 2015-07-17.
 */
public interface MybatisTreeBaseDao<E extends TreeEntity> extends MybatisBaseDao<E> {

    /**
     * 批量更新子实体的父实体集合字段
     * @return
     */
    int batchUpdateChildrenParentIds(TreeEntity entity);

}
