package com.wenin819.easyweb.core.db;

import java.util.List;

/**
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public interface BaseDao<E extends BaseEntity> {

    public List<E> queryByCriteria(CriteriaQuery critQuery);
}
