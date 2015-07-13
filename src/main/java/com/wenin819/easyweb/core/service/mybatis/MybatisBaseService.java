package com.wenin819.easyweb.core.service.mybatis;

import com.github.pagehelper.PageHelper;
import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.CurrentUserInfoDao;
import com.wenin819.easyweb.core.persistence.Page;
import com.wenin819.easyweb.core.persistence.mybatis.DBQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.utils.BeanUtils;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.utils.key.KeyFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 基于Ibatis的基本Service
 * @author lc3@yitong.com.cn
 */
public abstract class MybatisBaseService<E extends BaseEntity> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected SqlSessionTemplate template;

    @Autowired(required = false)
    private CurrentUserInfoDao currentUserInfoDao;

    /**
     * 得到表名
     * @return
     */
    public abstract String getTableName();

    /**
     * 得到主键名称
     * @return
     */
    public abstract String getIdKey();

    /**
     * 得到基本Dao
     * @return
     */
    public abstract MybatisBaseDao<E> getDao();

    /**
     * 是否程序自动生成主键
     * @return
     */
    public boolean autoGenId() {
        return true;
    }

    /**
     * 获取实体ID
     * @param entity 实体
     * @return 实体ID
     */
    public Object getId(Object entity) {
        String idKey = getIdKey();
        if(StringUtils.isBlank(idKey)) {
            throw new UnsupportedOperationException("MybatisBaseService.getId需要子类重写");
        }
        if(entity instanceof Map) {
            return ((Map) entity).get(idKey);
        } else {
            try {
                return PropertyUtils.getProperty(entity, idKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 通过主键查询实体
     * @param params 主键或含主键的Map或Object
     * @return
     */
    public E queryById(Object params) {
        return getDao().queryById(params);
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @param page 分页信息
     * @return 分页结果
     */
    public Page<E> queryPage(DBQuery query, Page<E> page) {
        PageHelper.startPage(page);
        getDao().queryByCriteria(query);
        return page;
    }

    /**
     * 增加或修改
     * @param params 参数
     * @return 增加或修改的记录数
     */
    public int save(E params) {
        if(null == params) {
            return -1;
        }
        Object id = getId(params);

        Date nowDate = new Date();
        BeanUtils.setProperty(params, ConfigEnum.FILED_NAME_UPDATE_TIME, nowDate, false);
        String curUserCode = null;
        if(null != currentUserInfoDao) {
            curUserCode = currentUserInfoDao.getLoginName();
        }
        if(null != curUserCode) {
            BeanUtils.setProperty(params, ConfigEnum.FILED_NAME_UPDATE_BY, curUserCode, false);
        }
        if(null == id || "".equals(id)) {
            if(autoGenId()) {
                id = genId();
            }
            BeanUtils.setProperty(params, getIdKey(), id, false);
            updateCreateEntity(params, nowDate, curUserCode);
            return getDao().insert(params);
        } else {
            int count = getDao().updateById(params);
            if(0 == count) {
                updateCreateEntity(params, nowDate, curUserCode);
                return getDao().insert(params);
            } else {
                return count;
            }
        }
    }

    /**
     * 更新新创建的实体
     * @param entity 新实体
     * @param createDate 创建时间
     * @param curUserCode   创建人
     */
    private void updateCreateEntity(E entity, Date createDate, String curUserCode) {
        BeanUtils.setProperty(entity, ConfigEnum.FILED_NAME_CREATE_TIME, createDate, false);
        if(null != curUserCode) {
            BeanUtils.setProperty(entity, ConfigEnum.FILED_NAME_CREATE_BY, curUserCode, false);
        }
        BeanUtils.setProperty(entity, ConfigEnum.FIELD_DEL_FLAG, ConfigEnum.DEL_FLAG_NORMAL, false);
    }

    /**
     * 删除
     * @param params 参数
     * @return 删除的记录数
     */
    public int delete(Object params) {
        return getDao().deleteById(params);
    }

    /**
     * 生成主键方法
     * @return
     */
    public String genId() {
        return KeyFactory.getKeyGenerator(getTableName()).genNextKey();
    }
}
