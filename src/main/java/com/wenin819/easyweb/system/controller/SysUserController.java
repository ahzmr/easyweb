package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.utils.SecurityUtils;
import com.wenin819.easyweb.core.utils.StringUtils;
import com.wenin819.easyweb.core.utils.WebUtils;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.system.model.SysUser;

import com.wenin819.easyweb.system.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wenin819@gmail.com on 2014-10-08.
 */
@Controller
@RequestMapping(SysUserController.BASE_URL)
public class SysUserController extends BaseCrudController<SysUser> {

    public static final String BASE_URL = "/system/SysUser/";

    @Resource
    private SysUserService sysUserService;

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    protected String getBasePagePath() {
        return "system/SysUser";
    }

    @Override
    protected SysUserService getService() {
        return sysUserService;
    }

    @Override
    protected CriteriaQuery genCriteriaes(SysUser entity, HttpServletRequest request, Model model) {
        CriteriaQuery query = super.genCriteriaes(entity, request, model);
        if(StringUtils.isNoneBlank(entity.getLoginName())) {
            query.createAndCriteria().like(SysUser.TE.loginName, "%" + entity.getLoginName() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getNo())) {
            query.createAndCriteria().like(SysUser.TE.no, "%" + entity.getNo() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getName())) {
            query.createAndCriteria().like(SysUser.TE.name, "%" + entity.getName() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getMobile())) {
            query.createAndCriteria().like(SysUser.TE.mobile, "%" + entity.getMobile() + "%");
        }
        query.addOrder(SysUser.TE.no, true);
        return query;
    }

    @RequiresAuthentication
    @RequestMapping(value = "modifyPwd.html")
    public String modifyPwd(String loginName, String password, Model model) {
        model.addAttribute("user", SecurityUtils.getCurrentUser());
        if(StringUtils.isNotBlank(password)) {
            try {
                getService().modifyPwd(loginName, password);
                model.addAttribute(WebUtils.MSG, "修改用户名和密码成功");
                return WebUtils.MSG_PAGE;
            } catch (Exception e) {
                model.addAttribute(WebUtils.MSG, "修改用户名和密码失败，失败原因为：" + e.getMessage());
            }
        }
        return getBasePagePath() + "ModifyPwd";
    }
}
