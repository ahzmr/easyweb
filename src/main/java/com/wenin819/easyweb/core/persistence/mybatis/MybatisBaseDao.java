package com.wenin819.easyweb.core.persistence.mybatis;

import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.Page;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public interface MybatisBaseDao<E extends BaseEntity> {

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
    int updateById(E record);

    /**
     * 通过主键删除记录
     * @param id 待删除记录的主键
     * @return
     */
    int deleteById(String id);

    /**
     * 通过条件查询
     * @param critQuery 条件查询
     * @return
     */
    List<E> queryByCriteria(CriteriaQuery critQuery);

    /**
     * 通过条件查询
     * @param critQuery 条件查询
     * @param rowBounds 分页
     * @return
     */
    Page<E> queryByCriteria(CriteriaQuery critQuery, RowBounds rowBounds);

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
