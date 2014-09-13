package com.wenin819.easyweb.modules.contacts.service.impl;

import com.wenin819.easyweb.core.db.BaseDao;
import com.wenin819.easyweb.core.db.BaseService;
import com.wenin819.easyweb.core.db.BaseServiceImpl;
import com.wenin819.easyweb.modules.contacts.dao.TxContactsDao;
import com.wenin819.easyweb.modules.contacts.model.TxContacts;
import com.wenin819.easyweb.modules.contacts.service.TxContactsService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wenin819@gmail.com on 2014-09-02.
 */
@Service
public class TxContactsServiceImpl extends BaseServiceImpl<TxContacts> implements TxContactsService {

    @Resource
    private TxContactsDao txContactsDao;

    @Override
    protected BaseDao<TxContacts> getDao() {
        return txContactsDao;
    }
}
