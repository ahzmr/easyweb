package com.wenin819.easyweb.core.db;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis 通用查询类
 *
 * @author wenin819@gmail.com
 */
public class CriteriaQuery {

    /**
     * order by
     */
    protected StringBuilder orderByClause;
    /**
     * distinct
     */
    protected boolean distinct;
    /**
     * or查询条件
     */
    protected Criteria criteria;
    private List<String> sqlList = null;
    private List<Object> valueList = null;
    private StringBuilder postSql = null;
    private Page<?> page;

    public CriteriaQuery(Page<?> page) {
        this();
        this.page = page;
    }

    public CriteriaQuery() {
        this.criteria = Criteria.newAndCriteria(this);
    }

    /**
     * 获取当前Order By查询条件
     * @return
     */
    public String getOrderByClause() {
        return null == orderByClause ? null : orderByClause.toString();
    }

    /**
     * 增加排序
     * @param filedName 字段名
     * @param isAsc 是否正序
     * @return
     */
    public CriteriaQuery addOrder(String filedName, boolean isAsc) {
        if (null == orderByClause) {
            orderByClause = new StringBuilder(filedName);
        } else {
            orderByClause.append(" ");
            orderByClause.append(filedName);
        }
        orderByClause.append(isAsc ? " ASC" : " DESC");
        return this;
    }

    /**
     * 对结果是否相同值合并处理
     * @return
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * 设置是否对结果相同值合并处理
     * @param distinct
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * 创建And子查询条件
     * @return
     */
    public Criteria createAndCriteria() {
        return criteria.createAndCriteria();
    }

    /**
     * 创建Or子查询条件
     * @return
     */
    public Criteria createOrCriteria() {
        return criteria.createOrCriteria();
    }

    /**
     * 清空查询条件
     */
    public void clear() {
        criteria = Criteria.newAndCriteria(this);
    }

    /**
     * 更改通知方法，方便子查询条件更改后告知当前查询条件，方便进行重新自动拼装Sql
     */
    public void notifyChange() {
        postSql = null;
        sqlList = null;
        valueList = null;
    }

    /**
     * 进行查询Sql和值的拼装
     */
    protected void buildSql() {
        postSql = new StringBuilder();
        sqlList = new ArrayList<String>();
        valueList = new ArrayList<Object>();
        criteria.genSql(null, sqlList, valueList, postSql);
    }

    public String getSelectClause() {
        return null;
    }

    public String getFromClause() {
        return null;
    }

    public String[] getWhereSqls() {
        if (null == sqlList) {
            buildSql();
        }
        return sqlList.toArray(new String[sqlList.size()]);
    }

    public Object[] getWhereValues() {
        if (null == valueList) {
            buildSql();
        }
        return valueList.toArray();
    }

    public String getWhereEndSql() {
        if (null == postSql) {
            buildSql();
        }
        return postSql.toString();
    }

    public void setPage(Page<?> page) {
        this.page = page;
    }

    public Page<?> getPage() {
        return page;
    }

    /**
     * 得到Where查询条件的Sql，变量用问题占位
     * @return
     */
    public String getWhereSql() {
        return StringUtils.arrayToDelimitedString(getWhereSqls(), "?") +
                (getWhereValues().length > 0 ? "?" : "") + getWhereEndSql();
    }
}
