package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.system.dao.SysMenuDao;
import com.wenin819.easyweb.system.model.SysMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author wenin819@gmail.com
 */
@Service
public class SysMenuService extends MybatisBaseService<SysMenu> {

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
    public MybatisBaseDao<SysMenu> getDao() {
        return sysMenuDao;
    }
}
