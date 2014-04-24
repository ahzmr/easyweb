package com.wenin819.easyweb.core.db;

import java.util.List;

/**
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public interface BaseDao<E extends BaseEntity> {

    int insert(E record);

    E queryById(String id);

    int updateById(E record);

    int deleteById(String id);

    List<E> queryByCriteria(CriteriaQuery critQuery);

    int countByCriteria(CriteriaQuery critQuery);

    int deleteByCriteria(CriteriaQuery critQuery);
}
