package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.system.dao.SysRoleDao;
import com.wenin819.easyweb.system.model.SysRole;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author wenin819@gmail.com
 */
@Service
public class SysRoleService extends MybatisBaseService<SysRole> {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public String getTableName() {
        return ConfigUtils.get().getValue("schema.configPlat") + ".sys_role";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MybatisBaseDao<SysRole> getDao() {
        return sysRoleDao;
    }

    /**
     * 查询角色菜单ID列表
     * @param role 角色
     * @return
     */
    public List<String> queryMenuIdsByRole(SysRole role) {
        if(null == role) {
            return Collections.EMPTY_LIST;
        }
        return sysRoleDao.queryMenuIdsByRole(role);
    }

    /**
     * 保存角色与菜单关系
     * @param role 角色
     * @param menuIds 菜单标识列表
     */
    public void saveRoleMenuRelations(SysRole role, List<String> menuIds) {
        Assert.notNull(role, "role不能为空");
        sysRoleDao.deleteMenuRelationByRole(role);
        if(null != menuIds) {
            for (String menuId : menuIds) {
                sysRoleDao.insertMenuRoleRelation(role.getId(), menuId);
            }
        }
    }

}
