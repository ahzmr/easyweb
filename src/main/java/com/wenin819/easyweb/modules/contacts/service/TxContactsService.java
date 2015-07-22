package com.wenin819.easyweb.modules.contacts.service;

import com.wenin819.easyweb.core.persistence.mybatis.Criteria;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.Configs;
import com.wenin819.easyweb.core.utils.StringUtils;
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
        return Configs.schameConfigplat() + ".tx_contacts";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MybatisBaseDao<TxContacts> getDao() {
        return txContactsDao;
    }

    @Override
    public CriteriaQuery genCriteriaQuery(TxContacts entity) {
        final CriteriaQuery query = super.genCriteriaQuery(entity);
        if(StringUtils.isNoneBlank(entity.getName())) {
            query.createAndCriteria().like(TxContacts.TE.name, "%" + entity.getName() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getAddress())) {
            query.createAndCriteria().like(TxContacts.TE.address, "%" + entity.getAddress() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getWorkAddr())) {
            query.createAndCriteria().like(TxContacts.TE.workAddr, "%" + entity.getWorkAddr() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getCompany())) {
            query.createAndCriteria().like(TxContacts.TE.company, "%" + entity.getCompany() + "%");
        }
        return query;
    }
}
