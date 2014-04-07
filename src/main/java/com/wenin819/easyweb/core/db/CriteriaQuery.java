package com.wenin819.easyweb.core.db;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mybatis 通用查询类
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
    private byte[] lock = new byte[0];

    public CriteriaQuery() {
        this.criteria = Criteria.newAndCriteria();
    }

    public String getOrderByClause() {
        return null == orderByClause ? null : orderByClause.toString();
    }
    
    /**
     * 增加排序
     * @param filedName
     * @param isAsc
     * @return
     */
    public CriteriaQuery addOrder(String filedName, boolean isAsc) {
    	if(null == orderByClause) {
    		orderByClause = new StringBuilder(filedName);
    	} else {
    		orderByClause.append(" ");
    		orderByClause.append(filedName);
    	}
    	orderByClause.append(isAsc ? " ASC" : " DESC");
    	return this;
    }
    
    public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

    public Criteria createAndCriteria() {
        return criteria.createAndCriteria();
    }

    public Criteria createOrCriteria() {
        return criteria.createOrCriteria();
    }

    /**
     * 清空查询条件
     */
    public void clear() {
        criteria = Criteria.newAndCriteria();
    }

    public List<String> getSqlList() {
        if(null == sqlList) {
            buildSql();
        }
        return Collections.unmodifiableList(sqlList);
    }

    public List<Object> getValueList() {
        if(null == valueList) {
            buildSql();
        }
        return Collections.unmodifiableList(valueList);
    }

    public String getWhereSql() {
        return StringUtils.collectionToDelimitedString(getSqlList(), "?") +
                (getValueList().size() > 0 ? "?" : "") + getPostSql();
    }

    public String getPostSql() {
        if(null == postSql) {
            buildSql();
        }
        return postSql.toString();
    }

    public void buildSql() {
        postSql = new StringBuilder();
        sqlList = new ArrayList<>();
        valueList = new ArrayList<>();
        criteria.genSql(null, sqlList, valueList, postSql);
    }

}
