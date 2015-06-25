package com.wenin819.easyweb.core.persistence.mybatis;


import com.wenin819.easyweb.core.persistence.Page;

/**
 * Sql查询通用接口
 * Created by wenin819@gmail.com on 2014-07-11.
 */
public interface DBQuery {

    /**
     * @return 是否为Distinct查询
     */
    boolean isDistinct();

    /**
     *
     * @return select子句
     */
    String getSelectClause();

    /**
     *
     * @return from子句
     */
    String getFromClause();

    /**
     * sql语句片段列表，被参数分隔
     * @return sql语句片段列表
     */
    String[] getWhereSqls();

    /**
     * @return 参数列表
     */
    Object[] getWhereValues();

    /**
     *
     * @return 末尾Sql子句
     */
    String getWhereEndSql();

    /**
     *
     * @return 排序子句
     */
    String getOrderByClause();

    /**
     * 设置分页信息
     * @param page 分页信息
     */
    void setPage(Page<?> page);

    /**
     *
     * @return 分页查询信息
     */
    Page<?> getPage();
}
