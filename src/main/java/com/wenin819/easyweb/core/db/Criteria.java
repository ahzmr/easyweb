package com.wenin819.easyweb.core.db;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ibatis 通用查询条件
 * @author wenin819@gmail.com
 *
 */
public class Criteria {

    protected static final String CONDITION_KEY = "condition";
    protected static final String VALUES_KEY = "values";
	protected final String delim;
    protected final boolean isAnd;
	/**
	 * 不带值的查询条件
	 */
	protected List<String> criteriaWithoutValue;

	/**
	 * 单个值的查询条件
	 */
    protected List<Map<String, Object>> criteriaWithSingleValue;

    /**
     * 值为集合的查询条件
     */
    protected List<Map<String, Object>> criteriaWithListValue;

    /**
     * between查询条件
     */
    protected List<Map<String, Object>> criteriaWithBetweenValue;

    /**
     * 嵌套子查询条件
     */
    protected List<Criteria> criterias;

    private Criteria(boolean isAnd) {
        super();
        this.isAnd = isAnd;
        delim = isAnd ? " and " : " or ";
        criteriaWithoutValue = new ArrayList<>();
        criteriaWithSingleValue = new ArrayList<>();
        criteriaWithListValue = new ArrayList<>();
        criteriaWithBetweenValue = new ArrayList<>();
        criterias = new ArrayList<>();
    }

    protected static boolean isValid(List<Criteria> criteriaList) {
        if(null == criteriaList || criteriaList.isEmpty()) {
            return false;
        }
        for (Criteria criteria : criteriaList) {
            if(criteria.isValid()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValid() {
        return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0
                || isValid(criterias);
    }

    public Criteria createOrCriteria() {
        if(!this.isAnd) {
           return this;
        }
        Criteria criteria = Criteria.newOrCriteria();
        criterias.add(criteria);
        return criteria;
    }

    public Criteria createAndCriteria() {
        if(isAnd) {
            return this;
        }
        Criteria criteria = Criteria.newAndCriteria();
        criterias.add(criteria);
        return criteria;
    }

    public Criteria isNull(IFiledEnum filedEnum) {
        criteriaWithoutValue.add(filedEnum + " is null");
        return this;
    }
    
    public Criteria isNotNull(IFiledEnum filedEnum) {
        criteriaWithoutValue.add(filedEnum + " is not null");
        return this;
    }
    
    public Criteria equalTo(IFiledEnum filedEnum, Object value) {
        if(null == value) {
            isNull(filedEnum);
        } else {
            addCriterion(filedEnum, "=", value);
        }
		return this;
	}

	public Criteria notEqualTo(IFiledEnum filedEnum, Object value) {
        if(null == value) {
            isNotNull(filedEnum);
        } else {
            addCriterion(filedEnum, "<>", value);
        }
		return this;
	}

	public Criteria greaterThan(IFiledEnum filedEnum, Object value) {
		addCriterion(filedEnum, ">", value);
		return this;
	}

	public Criteria greaterThanOrEqualTo(IFiledEnum filedEnum, Object value) {
		addCriterion(filedEnum, ">=", value);
		return this;
	}

	public Criteria lessThan(IFiledEnum filedEnum, Object value) {
		addCriterion(filedEnum, "<", value);
		return this;
	}

	public Criteria lessThanOrEqualTo(IFiledEnum filedEnum, Object value) {
		addCriterion(filedEnum, "<=", value);
		return this;
	}

	public Criteria like(IFiledEnum filedEnum, Object value) {
		addCriterion(filedEnum, "like", value);
		return this;
	}

	public Criteria notLike(IFiledEnum filedEnum, Object value) {
		addCriterion(filedEnum, "not like", value);
		return this;
	}

	public <E extends Object> Criteria in(IFiledEnum filedEnum, List<E> values) {
		addCriterion(filedEnum, "in", values);
		return this;
	}

	public <E extends Object> Criteria notIn(IFiledEnum filedEnum, List<E> values) {
		addCriterion(filedEnum, "not in", values);
		return this;
	}

	public Criteria between(IFiledEnum filedEnum, Object value1, Object value2) {
		addCriterion(filedEnum, "between", value1, value2);
		return this;
	}

	public Criteria andApprIdNotBetween(IFiledEnum filedEnum, Object value1, Object value2) {
		addCriterion(filedEnum, "not between", value1, value2);
		return this;
	}
	
    protected void addCriterion(IFiledEnum filedEnum, String op, Object value) {
    	Assert.notNull(filedEnum, "filedEnum 不能为空");
    	Assert.notNull(op, "op 不能为空");
    	Assert.notNull(value, "value 不能为空");
        Map<String, Object> map = new HashMap<>();
        map.put(CONDITION_KEY, filedEnum.getFiledName() + ' ' + op);
        map.put(VALUES_KEY, value);
        criteriaWithSingleValue.add(map);
    }

    protected <E extends Object> void addCriterion(IFiledEnum filedEnum, String op, List<E> values) {
    	Assert.notNull(filedEnum, "filedEnum 不能为空");
    	Assert.notNull(op, "op 不能为空");
        Assert.notEmpty(values, "values 不能为空");
        Map<String, Object> map = new HashMap<>();
        map.put(CONDITION_KEY, filedEnum.getFiledName() + ' ' + op);
        map.put(VALUES_KEY, values);
        criteriaWithListValue.add(map);
    }

    protected void addCriterion(IFiledEnum filedEnum, String op, Object value1, Object value2) {
    	Assert.notNull(filedEnum, "filedEnum 不能为空");
    	Assert.notNull(op, "op 不能为空");
        Assert.notNull(value1, "value1 不能为空");
        Assert.notNull(value2, "value2 不能为空");
        List<Object> list = new ArrayList<>();
        list.add(value1);
        list.add(value2);
        Map<String, Object> map = new HashMap<>();
        map.put(CONDITION_KEY, filedEnum.getFiledName() + ' ' + op);
        map.put(VALUES_KEY, list);
        criteriaWithBetweenValue.add(map);
    }

    public void genSql(StringBuilder preSql, List<String> sqlList, List<Object> valueList, StringBuilder postSql) {
        Assert.notNull(sqlList, "sqlList不能为空");
        Assert.notNull(valueList, "valueList不能为空");
        Assert.notNull(postSql, "postSql不能为空");
        if(null == preSql) {
            preSql = new StringBuilder();
        }
        StringBuilder tmpSql = new StringBuilder();
        boolean hasAdd = false;
        for (Criteria criteria : this.criterias) {
            if(!criteria.isValid()) {
                continue;
            }
            if(hasAdd) {
                preSql.append(this.delim);
            }
            preSql.append("(");
            criteria.genSql(preSql, sqlList, valueList, tmpSql);
            preSql = tmpSql;
            tmpSql = new StringBuilder();
            preSql.append(")");
            hasAdd = true;
        }
        for (Map<String,Object> stringObjectMap : this.criteriaWithBetweenValue) {
            if(hasAdd) {
                preSql.append(this.delim);
            }
            List<Object> list = (List<Object>) stringObjectMap.get(VALUES_KEY);
            preSql.append((String)(stringObjectMap.get(CONDITION_KEY)));
            sqlList.add(preSql.toString());
            preSql = new StringBuilder();
            valueList.add(list.get(0));
            sqlList.add(this.delim);
            valueList.add(list.get(1));
            hasAdd = true;
        }
        for (Map<String, Object> stringObjectMap : this.criteriaWithListValue) {
            if(hasAdd) {
                preSql.append(this.delim);
            }
            List<Object> list = (List<Object>) stringObjectMap.get(VALUES_KEY);
            preSql.append((String) (stringObjectMap.get(CONDITION_KEY)));
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
        if(!this.criteriaWithoutValue.isEmpty()) {
            if(hasAdd) {
                preSql.append(this.delim);
            }
            preSql.append(StringUtils.collectionToDelimitedString(this.criteriaWithoutValue, this.delim));
            hasAdd = true;
        }
        for (Map<String,Object> stringObjectMap : this.criteriaWithSingleValue) {
            if(hasAdd) {
                preSql.append(this.delim);
            }
            preSql.append((String)(stringObjectMap.get(CONDITION_KEY)));
            sqlList.add(preSql.toString());
            preSql = new StringBuilder();
            valueList.add(stringObjectMap.get(VALUES_KEY));
            hasAdd = true;
        }
        if(preSql.length() > 0) {
            postSql.append(preSql);
            preSql.delete(0, preSql.length());
        } else if(postSql.length() > 0) {
            postSql.delete(0, postSql.length());
        }
    }

    public static Criteria newAndCriteria() {
        return new Criteria(true);
    }

    public static Criteria newOrCriteria() {
        return new Criteria(false);
    }
}
