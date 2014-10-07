package com.wenin819.easyweb.core.db;

import java.util.List;

/**
 * 基础服务接口
 * Created by wenin819@gmail.com on 2014-09-02.
 */
public interface BaseService<E extends BaseEntity> {

    /**
     * 创建空的实体记录
     * @return
     */
    E createEntity();

    /**
     * 新增记录
     * @param record 待新增的记录
     * @return
     */
    int insert(E record);

    /**
     * 通过主键查询记录
     * @param id 待查询记录的主键
     * @return
     */
    E queryById(String id);

    /**
     * 通过主键更新
     * @param record 待更新的记录
     * @return
     */
    int update(E record);

    /**
     * 新增或更新记录
     * @param record 待新增或更新的记录
     * @return
     */
    int createOrUpdate(E record);

    /**
     * 通过主键删除记录
     * @param id 待删除记录的主键
     * @return
     */
    int delete(String id);

    /**
     * 通过条件查询
     * @param critQuery 条件查询
     * @return
     */
    List<E> queryByCriteria(CriteriaQuery critQuery);

    /**
     * 通过条件分页查询
     * @param critQuery 条件查询
     * @param page 分页
     * @return
     */
    Page<E> queryPageByCriteria(CriteriaQuery critQuery, Page<E> page);

    /**
     * 对条件查询求总数
     * @param critQuery 条件查询
     * @return
     */
    int countByCriteria(CriteriaQuery critQuery);

    /**
     * 通过条件查询删除
     * @param critQuery 条件查询
     * @return
     */
    int deleteByCriteria(CriteriaQuery critQuery);
}
