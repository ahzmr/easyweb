package com.wenin819.easyweb.modules.sys.dao;

import com.wenin819.easyweb.BaseSpringTest;
import com.wenin819.easyweb.core.db.CriteriaQuery;
import com.wenin819.easyweb.modules.sys.model.User;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wenin819@gmail.com on 2014/4/7.
 */
public class UserDaoTest extends BaseSpringTest {

    @Resource
    private UserDao userDao;

    @Test
    public void testQuery() {
        CriteriaQuery critQuery = new CriteriaQuery();
        critQuery.setDistinct(true);
        critQuery.createOrCriteria().equalTo(User.TE.name, "Thinkgem")
            .equalTo(User.TE.id, "13");
        List<User> list = userDao.queryByCriteria(critQuery);
        logger.info(list.toString());
    }
}
