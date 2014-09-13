package com.wenin819.easyweb.core.db;

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
public abstract class BaseServiceImpl<E extends BaseEntity> implements BaseService<E> {

    protected Class<E> clazz;
    protected abstract BaseDao<E> getDao();
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            clazz = (Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
    }

    @Override
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

    @Override
    public int insert(E record) {
        if(null == record) {
            return -1;
        }
        if(StringUtils.isBlank(record.getId())) {
            record.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        return getDao().insert(record);
    }

    @Override
    public E queryById(String id) {
        return getDao().queryById(id);
    }

    @Override
    public int update(E record) {
        if(null == record || StringUtils.isBlank(record.getId())) {
            return -1;
        }
        return getDao().updateById(record);
    }

    @Override
    public int createOrUpdate(E record) {
        if(!StringUtils.isBlank(record.getId())) {
            final int update = update(record);
            if(0 < update) {
                return update;
            }
        }
        return insert(record);
    }

    @Override
    public int delete(String id) {
        return getDao().deleteById(id);
    }

    @Override
    public List<E> queryByCriteria(CriteriaQuery critQuery) {
        return getDao().queryByCriteria(critQuery);
    }

    @Override
    public int countByCriteria(CriteriaQuery critQuery) {
        return getDao().countByCriteria(critQuery);
    }

    @Override
    public int deleteByCriteria(CriteriaQuery critQuery) {
        return getDao().deleteByCriteria(critQuery);
    }
}
