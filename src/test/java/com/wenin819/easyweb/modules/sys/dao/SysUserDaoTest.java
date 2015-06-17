package com.wenin819.easyweb.modules.sys.dao;

import com.wenin819.easyweb.BaseSpringTest;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.system.dao.SysUserDao;
import com.wenin819.easyweb.system.model.SysUser;

import org.junit.Test;

import java.util.List;

import javax.annotation.Resource;

/**
* Created by wenin819@gmail.com on 2014/4/7.
*/
public class SysUserDaoTest extends BaseSpringTest {

    @Resource
    private SysUserDao sysUserDao;

    private List<SysUser> query4List() {
        CriteriaQuery critQuery = new CriteriaQuery();
        critQuery.setDistinct(true);
        critQuery.createOrCriteria().equalTo(SysUser.TE.name, "Thinkgem")
            .equalTo(SysUser.TE.loginName, "thinkgem");
        List<SysUser> list = sysUserDao.queryByCriteria(critQuery);
        return list;
    }

    @Test
    public void testQuery() {
        List<SysUser> list = query4List();
        logger.info(list.toString());
    }

    @Test
    public void testUpdateById() {
        List<SysUser> list = query4List();
        for (SysUser user : list) {
            user.setName("wenin819");
            sysUserDao.updateById(user);
        }
        list = query4List();
        logger.info(list.toString());
    }

    @Test
    public void testInsert() {
        sysUserDao.deleteById("7");
        SysUser user = sysUserDao.queryById("1");
        user.setId("7");
        user.setLoginName("wenin819");
        user.setName("wenin819");
        logger.info("成功增加{}条记录", sysUserDao.insert(user));
    }
}
