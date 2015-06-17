package com.wenin819.easyweb.core.db;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Ibatis 通用查询条件
 *
 * @author wenin819@gmail.com
 */
public class Criteria {

    protected final String delim;
    protected final boolean isAnd;
    protected CriteriaQuery query;
    /**
     * 不带值的查询条件
     */
    protected final List<String> noValues;

    /**
     * 单个值的查询条件
     */
    protected final List<Map.Entry<String, Object>> singleValues;

    /**
     * 值为集合的查询条件
     */
    protected final List<Map.Entry<String, List<?>>> listValues;

    /**
     * between查询条件
     */
    protected final List<Map.Entry<String, List<Object>>> betweenValues;

    /**
     * Sql查询条件
     */
    protected final List<Map.Entry<String, Object[]>> sqlValues;

    /**
     * 嵌套子查询条件
     */
    protected final List<Criteria> criterias;

    private Criteria(boolean isAnd, CriteriaQuery query) {
        super();
        this.isAnd = isAnd;
        this.query = query;
        delim = isAnd ? " and " : " or ";
        noValues = new ArrayList<String>();
        singleValues = new ArrayList<Map.Entry<String, Object>>();
        listValues = new ArrayList<Map.Entry<String, List<?>>>();
        betweenValues = new ArrayList<Map.Entry<String, List<Object>>>();
        criterias = new ArrayList<Criteria>();
        sqlValues = new ArrayList<Map.Entry<String, Object[]>>();
    }

    /**
     * 判断条件查询列表是否有效，即存在有用的条件查询
     * @param criteriaList 条件查询列表
     * @return
     */
    protected static boolean isValid(List<Criteria> criteriaList) {
        if (null == criteriaList || criteriaList.isEmpty()) {
            return false;
        }
        for (Criteria criteria : criteriaList) {
            if (criteria.isValid()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 基于当前条件创建And子查询条件
     * @return
     */
    public static Criteria newAndCriteria(CriteriaQuery query) {
        return new Criteria(true, query);
    }

    /**
     * 基于当前条件创建Or子查询条件
     * @return
     */
    public static Criteria newOrCriteria(CriteriaQuery query) {
        return new Criteria(false, query);
    }

    /**
     * 当前查询条件是否有效，即存在有用的条件查询
     * @return
     */
    public boolean isValid() {
        return noValues.size() > 0
               || singleValues.size() > 0
               || listValues.size() > 0
               || betweenValues.size() > 0
               || sqlValues.size() > 0
               || isValid(criterias);
    }

    /**
     * 基于当前条件创建Or子查询条件
     * @return
     */
    public Criteria createOrCriteria() {
        if (!this.isAnd) {
            return this;
        }
        Criteria criteria = Criteria.newOrCriteria(query);
        criterias.add(criteria);
        hasChange();
        return criteria;
    }

    /**
     * 基于当前条件创建And子查询条件
     * @return
     */
    public Criteria createAndCriteria() {
        if (isAnd) {
            return this;
        }
        Criteria criteria = Criteria.newAndCriteria(query);
        criterias.add(criteria);
        hasChange();
        return criteria;
    }

    /**
     * 为空查询
     * @param filedName 可以加函数的字段名称
     * @return
     */
    public Criteria isNull(IFiledEnum filedName) {
        noValues.add(filedName + " is null");
        hasChange();
        return this;
    }

    /**
     * 不为空查询
     * @param filedName 可以加函数的字段名称
     * @return
     */
    public Criteria isNotNull(IFiledEnum filedName) {
        noValues.add(filedName + " is not null");
        hasChange();
        return this;
    }

    /**
     * 相等查询
     * @param filedName 可以加函数的字段名称
     * @param value 值，可为null
     * @return
     */
    public Criteria equalTo(IFiledEnum filedName, Object value) {
        if (null == value) {
            isNull(filedName);
        } else {
            addCriterion(filedName, "=", value);
        }
        return this;
    }

    /**
     * 不相等查询
     * @param filedName 可以加函数的字段名称
     * @param value 值，可为null
     * @return
     */
    public Criteria notEqualTo(IFiledEnum filedName, Object value) {
        if (null == value) {
            isNotNull(filedName);
        } else {
            addCriterion(filedName, "!=", value);
        }
        return this;
    }

    /**
     * 大于查询
     * @param filedName 可以加函数的字段名称
     * @param value 值
     * @return
     */
    public Criteria greaterThan(IFiledEnum filedName, Object value) {
        addCriterion(filedName, ">", value);
        return this;
    }

    /**
     * 大于等于查询
     * @param filedName 可以加函数的字段名称
     * @param value 值
     * @return
     */
    public Criteria greaterThanOrEqualTo(IFiledEnum filedName, Object value) {
        addCriterion(filedName, ">=", value);
        return this;
    }

    /**
     * 小于查询
     * @param filedName 可以加函数的字段名称
     * @param value 值
     * @return
     */
    public Criteria lessThan(IFiledEnum filedName, Object value) {
        addCriterion(filedName, "<", value);
        return this;
    }

    /**
     * 小于等于查询
     * @param filedName 可以加函数的字段名称
     * @param value 值
     * @return
     */
    public Criteria lessThanOrEqualTo(IFiledEnum filedName, Object value) {
        addCriterion(filedName, "<=", value);
        return this;
    }

    /**
     * like查询
     * @param filedName 可以加函数的字段名称
     * @param value 值
     * @return
     */
    public Criteria like(IFiledEnum filedName, Object value) {
        addCriterion(filedName, "like", value);
        return this;
    }

    /**
     * not like查询
     * @param filedName 可以加函数的字段名称
     * @param value 值
     * @return
     */
    public Criteria notLike(IFiledEnum filedName, Object value) {
        addCriterion(filedName, "not like", value);
        return this;
    }

    /**
     * in查询
     * @param filedName 可以加函数的字段名称
     * @param values 值列表
     * @param <E>
     * @return
     */
    public <E> Criteria in(IFiledEnum filedName, List<E> values) {
        addCriterion(filedName, "in", values);
        return this;
    }

    /**
     * not in查询
     * @param filedName 可以加函数的字段名称
     * @param values 值列表
     * @param <E>
     * @return
     */
    public <E> Criteria notIn(IFiledEnum filedName, List<E> values) {
        addCriterion(filedName, "not in", values);
        return this;
    }

    /**
     * between查询
     * @param filedName 可以加函数的字段名称
     * @param value1 起始值
     * @param value2 结束值
     * @return
     */
    public Criteria between(IFiledEnum filedName, Object value1, Object value2) {
        addCriterion(filedName, "between", value1, value2);
        return this;
    }

    /**
     * not between查询
     * @param filedName 可以加函数的字段名称
     * @param value1 起始值
     * @param value2 结束值
     * @return
     */
    public Criteria notBetween(IFiledEnum filedName, Object value1, Object value2) {
        addCriterion(filedName, "not between", value1, value2);
        return this;
    }

    /**
     * 直接sql查询
     * @param sql 子sql语句
     * @param values 值列表
     * @return
     */
    public Criteria sql(String sql, Object... values) {
        Assert.hasText(sql, "sql 语句不能为空");
        int count = 0;
        int idx = -1;
        while(-1 != (idx = sql.indexOf('?', idx + 1))) {
            count ++;
        }
        Assert.isTrue(count == values.length, "sql 中的变量个数与值的个数不匹配");
        sqlValues.add(new AbstractMap.SimpleEntry<String, Object[]>(sql, values));
        hasChange();
        return this;
    }

    /**
     * 增加单值查询条件
     * @param filedName 可以加函数的字段名称
     * @param op 操作名
     * @param value 值
     */
    protected void addCriterion(IFiledEnum filedName, String op, Object value) {
        Assert.notNull(filedName, "filedName 不能为空");
        Assert.notNull(op, "op 不能为空");
        Assert.notNull(value, "value 不能为空");
        singleValues.add(new AbstractMap.SimpleEntry<String, Object>(filedName.getFiledName() + ' ' + op, value));
        hasChange();
    }

    /**
     * 增加列表值查询条件
     * @param filedName 可以加函数的字段名称
     * @param op 操作名
     * @param values 列表值
     * @param <E>
     */
    protected <E> void addCriterion(IFiledEnum filedName, String op,
                                                   List<E> values) {
        Assert.notNull(filedName, "filedName 不能为空");
        Assert.notNull(op, "op 不能为空");
        Assert.notEmpty(values, "values 不能为空");
        listValues.add(new AbstractMap.SimpleEntry<String, List<?>>(filedName.getFiledName() + ' ' + op, values));
        hasChange();
    }

    /**
     * 增加两个值查询条件
     * @param filedName 可以加函数的字段名称
     * @param op 操作名
     * @param value1 值1
     * @param value2 值2
     */
    protected void addCriterion(IFiledEnum filedName, String op, Object value1, Object value2) {
        Assert.notNull(filedName, "filedName 不能为空");
        Assert.notNull(op, "op 不能为空");
        Assert.notNull(value1, "value1 不能为空");
        Assert.notNull(value2, "value2 不能为空");
        List<Object> list = new ArrayList<Object>();
        list.add(value1);
        list.add(value2);
        betweenValues.add(new AbstractMap.SimpleEntry<String, List<Object>>(filedName.getFiledName() + ' ' + op, list));
        hasChange();
    }

    /**
     * 当前类改变后触发的方法
     */
    protected void hasChange() {
        if(null != query) {
            query.notifyChange();
        }
    }

    /**
     * 递归拼装查询Sql
     * @param preSql 起始Sql，可为空
     * @param sqlList Sql片断列表
     * @param valueList 值列表
     * @param postSql 结尾Sql，不能为空
     */
    public void genSql(StringBuilder preSql, List<String> sqlList, List<Object> valueList,
                       StringBuilder postSql) {
        Assert.notNull(sqlList, "sqlList不能为空");
        Assert.notNull(valueList, "valueList不能为空");
        Assert.notNull(postSql, "postSql不能为空");
        if (null == preSql) {
            preSql = new StringBuilder();
        }
        boolean hasAdd = false;
        // 拼装子查询
        for (Criteria criteria : this.criterias) {
            if (!criteria.isValid()) {
                continue;
            }
            if (hasAdd) {
                preSql.append(this.delim);
            }
            StringBuilder tmpSql = new StringBuilder();
            preSql.append("(");
            criteria.genSql(preSql, sqlList, valueList, tmpSql);
            preSql = tmpSql;
            preSql.append(")");
            hasAdd = true;
        }
        // 拼装Sql查询
        for (Map.Entry<String, Object[]> entry : sqlValues) {
            if (hasAdd) {
                preSql.append(this.delim);
            }
            preSql.append("(");
            String sql = entry.getKey();
            String[] sqls = sql.split("\\?");
            sqls[0] = preSql.append(sqls[0]).toString();
            Object[] values = entry.getValue();
            if(null != values && values.length > 0) {
                int i = 0;
                for (; i < values.length; i++) {
                    sqlList.add(sqls[i]);
                    valueList.add(values[i]);
                }
                if(i < sqls.length) {
                    preSql = new StringBuilder(sqls[i]);
                } else {
                    preSql = new StringBuilder();
                }
            }
            preSql.append(")");
            hasAdd = true;
        }
        for (Map.Entry<String, List<Object>> entry : this.betweenValues) {
            if (hasAdd) {
                preSql.append(this.delim);
            }
            List<Object> list = entry.getValue();
            preSql.append(entry.getKey());
            sqlList.add(preSql.toString());
            preSql = new StringBuilder();
            valueList.add(list.get(0));
            sqlList.add(this.delim);
            valueList.add(list.get(1));
            hasAdd = true;
        }
        for (Map.Entry<String, List<?>> entry : this.listValues) {
            if (hasAdd) {
                preSql.append(this.delim);
            }
            List<?> list = entry.getValue();
            preSql.append(entry.getKey());
            preSql.append(" (");
            sqlList.add(preSql.toString());
            preSql = new StringBuilder();
            valueList.add(list.get(0));
            for (int i = 1; i < list.size(); i++) {
                sqlList.add(",");
                valueList.add(list.get(i));
            }
            preSql.append(")");
            hasAdd = true;
        }
        if (!this.noValues.isEmpty()) {
            if (hasAdd) {
                preSql.append(this.delim);
            }
            preSql.append(StringUtils.collectionToDelimitedString(this.noValues, this.delim));
            hasAdd = true;
        }
        for (Map.Entry<String, Object> entry : this.singleValues) {
            if (hasAdd) {
                preSql.append(this.delim);
            }
            preSql.append(entry.getKey());
            sqlList.add(preSql.toString());
            preSql = new StringBuilder();
            valueList.add(entry.getValue());
            hasAdd = true;
        }
        if (preSql.length() > 0) {
            postSql.append(preSql);
            preSql.delete(0, preSql.length());
        } else if (postSql.length() > 0) {
            postSql.delete(0, postSql.length());
        }
    }
}
