package com.wenin819.easyweb.core.persistence.mybatis;

import com.wenin819.easyweb.core.persistence.IFiledEnum;
import com.wenin819.easyweb.core.persistence.Page;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Mybatis 通用查询类，非多线程安全
 *
 * @author wenin819@gmail.com
 */
public class CriteriaQuery implements DBQuery {

    /**
     * 排序条件
     */
    protected StringBuilder orderByClause;
    /**
     * 是否去重复
     */
    protected boolean distinct;
    /**
     * 查询条件
     */
    protected Criteria criteria;
    /**
     * 生成的查询条件片断
     */
    private List<String> sqlList = null;
    /**
     * 生成的查询条件对应的值
     */
    private List<Object> valueList = null;
    /**
     * 生成的查询条件结尾SQL
     */
    private StringBuilder postSql = null;
    /**
     * 分页信息
     */
    private Page<?> page;

    /**
     * 选择条件
     */
    private StringBuilder selectClause;
    /**
     * 表条件
     */
    private StringBuilder fromClause;

    /**
     * 通过分页信息构造
     * @param page 分页信息
     */
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
     * 增加选择条件
     * @param filedName
     * @return
     */
    public CriteriaQuery addSelect(IFiledEnum filedName) {
        if (null == selectClause) {
            selectClause = new StringBuilder(filedName.getFiledName());
        } else {
            selectClause.append(", ");
            selectClause.append(filedName);
        }
        return this;
    }

    /**
     * 设置选择条件
     * @param filedNames 选择条件
     * @return
     */
    public CriteriaQuery setSelects(IFiledEnum... filedNames) {
        selectClause = null;
        if(null == filedNames || filedNames.length == 0) {
            return this;
        }
        for (IFiledEnum filedName : filedNames) {
            addSelect(filedName);
        }
        return this;
    }

    /**
     * 增加表条件
     * @param tableName
     * @return
     */
    public CriteriaQuery addFrom(String tableName) {
        if (null == fromClause) {
            fromClause = new StringBuilder(tableName);
        } else {
            fromClause.append(", ");
            fromClause.append(tableName);
        }
        return this;
    }

    /**
     * 设置表条件
     * @param tableNames 表条件
     * @return
     */
    public CriteriaQuery setFroms(String... tableNames) {
        fromClause = null;
        if(null == tableNames || tableNames.length == 0) {
            return this;
        }
        for (String tableName : tableNames) {
            addFrom(tableName);
        }
        return this;
    }

    /**
     * 增加排序
     * @param filedName 字段名
     * @param isAsc 是否正序
     * @return
     */
    public CriteriaQuery addOrder(IFiledEnum filedName, boolean isAsc) {
        if (null == orderByClause) {
            orderByClause = new StringBuilder(filedName.getFiledName());
        } else {
            orderByClause.append(", ");
            orderByClause.append(filedName);
        }
        orderByClause.append(isAsc ? " ASC" : " DESC");
        return this;
    }

    /**
     * 设置排序条件
     * @param filedNames 字段集合
     * @param isAscs 排序类型集合
     * @return
     */
    public CriteriaQuery setOrders(IFiledEnum[] filedNames, boolean[] isAscs) {
        orderByClause = null;
        if(null == filedNames || filedNames.length == 0) {
            return this;
        }
        Assert.isTrue(null != isAscs && isAscs.length == filedNames.length, "filedNames与isAscs传参不一致");
        for (int i = 0; i < filedNames.length; i++) {
            addOrder(filedNames[i], isAscs[i]);
        }
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
        sqlList = new LinkedList<String>();
        valueList = new LinkedList<Object>();
        criteria.genSql(null, sqlList, valueList, postSql);
    }

    public String getSelectClause() {
        return null == selectClause ? null : selectClause.toString();
    }

    public String getFromClause() {
        return null == fromClause ? null : fromClause.toString();
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
