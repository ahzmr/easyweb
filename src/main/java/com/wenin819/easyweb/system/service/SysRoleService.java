package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.system.dao.SysRoleDao;
import com.wenin819.easyweb.system.model.SysRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
