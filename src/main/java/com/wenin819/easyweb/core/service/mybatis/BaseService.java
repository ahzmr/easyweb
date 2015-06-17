package com.wenin819.easyweb.core.service.mybatis;

import com.github.pagehelper.PageHelper;
import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.Page;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 * Created by wenin819@gmail.com on 2014-09-02.
 */
public abstract class BaseService<E extends BaseEntity> {

    protected Class<E> clazz;
    protected abstract MybatisBaseDao<E> getDao();
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    public BaseService() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            clazz = (Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
    }

    /**
     * 创建空的实体记录
     * @return
     */
    public E createEntity() {
        if(null == clazz) {
            return null;
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            final String msg = "创建新的实例失败：" + clazz.getName();
            if(logger.isErrorEnabled()) {
                logger.error(msg, e);
            }
            throw new IllegalArgumentException(msg, e);
        }
    }

    /**
     * 新增记录
     * @param record 待新增的记录
     * @return
     */
    public int insert(E record) {
        if(null == record) {
            return -1;
        }
        if(StringUtils.isBlank(record.getId())) {
            record.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        return getDao().insert(record);
    }

    /**
     * 通过主键查询记录
     * @param id 待查询记录的主键
     * @return
     */
    public E queryById(String id) {
        return getDao().queryById(id);
    }

    /**
     * 通过主键更新
     * @param record 待更新的记录
     * @return
     */
    public int update(E record) {
        if(null == record || StringUtils.isBlank(record.getId())) {
            return -1;
        }
        return getDao().updateById(record);
    }

    /**
     * 新增或更新记录
     * @param record 待新增或更新的记录
     * @return
     */
    public int createOrUpdate(E record) {
        if(!StringUtils.isBlank(record.getId())) {
            final int update = update(record);
            if(0 < update) {
                return update;
            }
        }
        return insert(record);
    }

    /**
     * 通过主键删除记录
     * @param id 待删除记录的主键
     * @return
     */
    public int delete(String id) {
        return getDao().deleteById(id);
    }

    /**
     * 通过条件查询
     * @param critQuery 条件查询
     * @return
     */
    public List<E> queryByCriteria(CriteriaQuery critQuery) {
        return getDao().queryByCriteria(critQuery);
    }

    /**
     * 通过条件分页查询
     * @param critQuery 条件查询
     * @param page 分页
     * @return
     */
    public Page<E> queryPageByCriteria(CriteriaQuery critQuery, Page<E> page) {
        if(null == page) {
            page = new Page<E>();
        }
        PageHelper.startPage(page);
        return (Page<E>) getDao().queryByCriteria(critQuery);
    }

    /**
     * 对条件查询求总数
     * @param critQuery 条件查询
     * @return
     */
    public int countByCriteria(CriteriaQuery critQuery) {
        return getDao().countByCriteria(critQuery);
    }

    /**
     * 通过条件查询删除
     * @param critQuery 条件查询
     * @return
     */
    public int deleteByCriteria(CriteriaQuery critQuery) {
        return getDao().deleteByCriteria(critQuery);
    }
}
