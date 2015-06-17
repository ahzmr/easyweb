package com.wenin819.easyweb.modules.contacts.service;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.BaseService;
import com.wenin819.easyweb.modules.contacts.dao.TxContactsDao;
import com.wenin819.easyweb.modules.contacts.model.TxContacts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wenin819@gmail.com on 2014-09-02.
 */
@Service
public class TxContactsService extends BaseService<TxContacts> {

    @Resource
    private TxContactsDao txContactsDao;

    @Override
    protected MybatisBaseDao<TxContacts> getDao() {
        return txContactsDao;
    }
}
