package com.wenin819.easyweb.modules.contacts.controller;

import com.wenin819.easyweb.core.persistence.mybatis.Criteria;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.web.ActionType;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.modules.contacts.model.TxContacts;
import com.wenin819.easyweb.modules.contacts.service.TxContactsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wenin819@gmail.com on 2014-09-02.
 */
@Controller
@RequestMapping(TxContactsController.BASE_URL)
public class TxContactsController extends BaseCrudController<TxContacts> {

    public static final String BASE_URL = "/contacts/";

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    protected TxContacts updateEntity(TxContacts entity, ActionType type, HttpServletRequest request, Model model) {
        return super.updateEntity(entity, type, request, model);
    }

    @Override
    protected CriteriaQuery genCriteriaes(TxContacts entity, HttpServletRequest request, Model model) {
        String viewType = request.getParameter("viewType");
        final CriteriaQuery criteriaQuery = super.genCriteriaes(entity, request, model);
        final Criteria criteria = criteriaQuery.createAndCriteria();
        if(null == viewType) {
            viewType = "1";
        }
        switch (Integer.parseInt(viewType)) {
            case 1:
                criteria.isNotNull(TxContacts.TE.cellphone);
                break;
            case 0:
                criteria.isNull(TxContacts.TE.cellphone);
                break;
            default:
                break;
        }
        model.addAttribute("viewType", viewType);
        return criteriaQuery;
    }

    @Resource
    private TxContactsService txContactsService;

    @Override
    protected String getBasePagePath() {
        return "modules/contacts/contact";
    }

    @Override
    protected MybatisBaseService<TxContacts> getService() {
        return txContactsService;
    }
}
