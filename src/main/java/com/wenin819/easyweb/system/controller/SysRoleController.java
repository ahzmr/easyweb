package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.persistence.mybatis.Criteria;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.web.ActionType;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.system.model.SysRole;
import com.wenin819.easyweb.system.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wenin819@gmail.com
 */
@Controller
@RequestMapping(SysRoleController.BASE_URL)
public class SysRoleController extends BaseCrudController<SysRole> {

    public static final String BASE_URL = "/system/SysRole/";
    private static final String BASE_PATH = "system/SysRole";

    @Resource
    private SysRoleService sysRoleService;

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    protected String getBasePagePath() {
        return BASE_PATH;
    }

    @Override
    protected String getBasePermission() {
        return "system:SysRole";
    }

    @Override
    protected MybatisBaseService<SysRole> getService() {
        return sysRoleService;
    }

    @Override
    protected CriteriaQuery genCriteriaes(SysRole entity, HttpServletRequest request, Model model) {
        CriteriaQuery query = super.genCriteriaes(entity, request, model);
        query.createAndCriteria().equalTo(SysRole.TE.delFlag, ConfigEnum.DEL_FLAG_NORMAL);
        return query;
    }
}
