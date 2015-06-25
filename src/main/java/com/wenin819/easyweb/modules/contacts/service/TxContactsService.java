package com.wenin819.easyweb.modules.contacts.service;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigName;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.modules.contacts.dao.TxContactsDao;
import com.wenin819.easyweb.modules.contacts.model.TxContacts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wenin819@gmail.com on 2014-09-02.
 */
@Service
public class TxContactsService extends MybatisBaseService<TxContacts> {

    @Resource
    private TxContactsDao txContactsDao;

    @Override
    public String getTableName() {
        return ConfigUtils.get().getValue(ConfigName.SCHAME_CONFIGPLAT) + ".tx_contacts";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MybatisBaseDao<TxContacts> getDao() {
        return txContactsDao;
    }
}
