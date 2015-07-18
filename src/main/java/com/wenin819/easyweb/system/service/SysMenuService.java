package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisTreeBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisTreeBaseService;
import com.wenin819.easyweb.core.utils.ConfigName;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.core.utils.StringUtils;
import com.wenin819.easyweb.system.dao.SysMenuDao;
import com.wenin819.easyweb.system.model.SysMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author wenin819@gmail.com
 */
@Service
public class SysMenuService extends MybatisTreeBaseService<SysMenu> {

    @Resource
    private SysMenuDao sysMenuDao;

    @Override
    public String getTableName() {
        return ConfigUtils.get().getValue("schema.configPlat") + ".sys_menu";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MybatisTreeBaseDao<SysMenu> getDao() {
        return sysMenuDao;
    }

    /**
     * 通过父节点查子节点
     * @param parent 父节点
     * @return
     */
    public List<SysMenu> queryByParent(SysMenu parent) {
        CriteriaQuery query = new CriteriaQuery();
        if(StringUtils.isNotBlank(parent.getId())) {
            query.createAndCriteria().equalTo(SysMenu.TE.parentId, parent.getId());
        } else {
            query.createAndCriteria().equalTo(SysMenu.TE.parentId, ConfigName.treeEntityRootId());
        }
        return getDao().queryByCriteria(query);
    }

    /**
     * 判断是否为父节点
     * @param parent 节点
     * @return
     */
    public boolean isParent(SysMenu parent) {
        if(null == parent) {
            return false;
        }
        CriteriaQuery query = new CriteriaQuery();
        if(StringUtils.isNotBlank(parent.getId())) {
            query.createAndCriteria().equalTo(SysMenu.TE.parentId, parent.getId());
        } else {
            query.createAndCriteria().equalTo(SysMenu.TE.parentId, ConfigName.treeEntityRootId());
        }
        return getDao().countByCriteria(query) > 0;
    }
}
