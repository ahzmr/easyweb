package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisTreeBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisTreeBaseService;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.core.utils.Configs;
import com.wenin819.easyweb.core.utils.StringUtils;
import com.wenin819.easyweb.core.utils.tree.ITreeNodeAdapter;
import com.wenin819.easyweb.core.utils.tree.TreeSortUtils;
import com.wenin819.easyweb.core.utils.tree.adapter.ObjectTreeNodeAdapter;
import com.wenin819.easyweb.system.dao.SysMenuDao;
import com.wenin819.easyweb.system.model.SysMenu;
import com.wenin819.easyweb.system.model.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author wenin819@gmail.com
 */
@Service
public class SysMenuService extends MybatisTreeBaseService<SysMenu> {

    @Resource
    private SysMenuDao sysMenuDao;

    /**
     * 菜单的树结构适配器
     */
    public final ITreeNodeAdapter<SysMenu> menuTreeNodeAdapter = new ObjectTreeNodeAdapter<SysMenu>() {
        @Override
        public Boolean getIsParent(SysMenu node) {
            return isParent(node);
        }

        @Override
        public String getId(SysMenu node) {
            return null == node ? null : node.getId();
        }

        @Override
        public String getPid(SysMenu node) {
            return null == node ? null : node.getParentId();
        }

        @Override
        public String getName(SysMenu node) {
            return null == node ? null : node.getName();
        }
    };

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

    @Override
    public CriteriaQuery genCriteriaQuery(SysMenu entity) {
        CriteriaQuery query = super.genCriteriaQuery(entity);
        query.createAndCriteria().equalTo(SysMenu.TE.delFlag, ConfigEnum.DEL_FLAG_NORMAL);
        query.addOrder(SysMenu.TE.sort, true);
        return query;
    }

    /**
     * 查询所有菜单
     * @return
     */
    public List<SysMenu> queryAllMenus() {
        CriteriaQuery query = new CriteriaQuery();
        query.addOrder(SysMenu.TE.sort, true);
        List<SysMenu> sysMenus = getDao().queryByCriteria(query);
        return TreeSortUtils.sort4Tree(sysMenus, null, menuTreeNodeAdapter);
    }

    /**
     * 查询用户当前所有菜单
     * @param user 用户
     * @return
     */
    public List<SysMenu> queryMyMenus(SysUser user) {
        if(null == user) {
            return Collections.EMPTY_LIST;
        } else {
            return TreeSortUtils.sort4Tree(sysMenuDao.queryMyMemus(user), null, menuTreeNodeAdapter);
        }
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
            query.createAndCriteria().equalTo(SysMenu.TE.parentId, Configs.treeEntityRootId());
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
            query.createAndCriteria().equalTo(SysMenu.TE.parentId, Configs.treeEntityRootId());
        }
        return getDao().countByCriteria(query) > 0;
    }

    /**
     * 批量保存排序
     * @param ids
     * @param oldSorts
     * @param sorts
     */
    @Transient
    public void batchUpdateSorts(String[] ids, Integer[] oldSorts, Integer[] sorts) {
        if(null == ids) {
            return;
        }
        Assert.notNull(sorts, "sorts不能为空");
        Assert.isTrue(null == oldSorts || ids.length == oldSorts.length, "oldSorts长度不正确");
        Assert.isTrue(ids.length == sorts.length, "sorts长度不正确");
        SysMenu menu = new SysMenu();
        for (int i = 0; i < ids.length; i++) {
            Integer sort = sorts[i];
            if(null != oldSorts) {
                Integer oldSort = oldSorts[i];
                if(null != sort && sort.equals(oldSort)) {
                    continue;
                }
            }
            String id = ids[i];
            menu.setId(id);
            menu.setSort(sort);
            getDao().updateByIdSelective(menu);
        }
    }
}
