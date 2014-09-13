package com.wenin819.easyweb.core.db;

import java.util.List;

/**
 * 基础服务接口
 * Created by wenin819@gmail.com on 2014-09-02.
 */
public interface BaseService<E extends BaseEntity> {

    E createEntity();

    int insert(E record);

    E queryById(String id);

    int update(E record);

    int createOrUpdate(E record);

    int delete(String id);

    List<E> queryByCriteria(CriteriaQuery critQuery);

    int countByCriteria(CriteriaQuery critQuery);

    int deleteByCriteria(CriteriaQuery critQuery);
}
