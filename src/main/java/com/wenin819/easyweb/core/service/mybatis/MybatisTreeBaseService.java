package com.wenin819.easyweb.core.service.mybatis;

import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.TreeEntity;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisTreeBaseDao;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.utils.ConfigName;
import com.wenin819.easyweb.core.utils.StringUtils;

/**
 * @author wenin819@gmail.com
 * @date 2015-07-17.
 */
public abstract class MybatisTreeBaseService<E extends TreeEntity> extends MybatisBaseService<E> {

    @Override
    public abstract MybatisTreeBaseDao<E> getDao();

    @Override
    public int save(E params) {
        if(null == params) {
            return -1;
        }
        E parent;
        String parentId = params.getParentId();
        if(null != parentId && null != (parent = queryById(parentId))) {
            String parentIds = StringUtils.join(parent.getParentIds(),
                    parentId, ConfigEnum.SYS_DEFAULT_SPLIT_STR);
            params.setParentIds(parentIds);
        } else {
            params.setParentId(ConfigName.treeEntityRootId());
            params.setParentIds(params.getParentId() + ConfigEnum.SYS_DEFAULT_SPLIT_STR);
        }
        int save = super.save(params);
        if(save > 0) {
            getDao().batchUpdateChildrenParentIds(params);
        }
        return save;
    }

    @Override
    public int delete(Object params) {
        if(null == params) {
            return -1;
        }
        String id = null;
        if(params instanceof BaseEntity) {
            id = ((BaseEntity) params).getId();
        } else {
            id = params.toString();
        }
        CriteriaQuery query = new CriteriaQuery();
        query.createAndCriteria().like(TreeEntity.TE.parentIds, "%;" + id + ";%");
        getDao().deleteByCriteria(query);

        return super.delete(params);
    }
}
