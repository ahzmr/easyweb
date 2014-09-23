package com.wenin819.easyweb.system.service.impl;

import com.wenin819.easyweb.core.db.BaseDao;
import com.wenin819.easyweb.core.db.BaseServiceImpl;
import com.wenin819.easyweb.core.db.CriteriaQuery;
import com.wenin819.easyweb.core.util.StringUtils;
import com.wenin819.easyweb.system.dao.SysUserDao;
import com.wenin819.easyweb.system.model.SysUser;
import com.wenin819.easyweb.system.service.SysUserService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;
    @Override
    protected BaseDao<SysUser> getDao() {
        return sysUserDao;
    }

    @Override
    public SysUser queryByLoginName(String username) {
        if(StringUtils.isBlank(username)) {
            return null;
        }
        CriteriaQuery query = new CriteriaQuery();
        query.createAndCriteria().equalTo(SysUser.TE.loginName, username);
        final List<SysUser> sysUsers = sysUserDao.queryByCriteria(query);
        if(sysUsers.size() == 1) {
            return sysUsers.get(0);
        } else {
            return null;
        }
    }
}
