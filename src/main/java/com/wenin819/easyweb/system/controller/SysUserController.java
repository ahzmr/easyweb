package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.utils.SecurityUtils;
import com.wenin819.easyweb.core.utils.StringUtils;
import com.wenin819.easyweb.core.utils.WebUtils;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.system.model.SysUser;

import com.wenin819.easyweb.system.service.SysMenuService;
import com.wenin819.easyweb.system.service.SysRoleService;
import com.wenin819.easyweb.system.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;

/**
 * Created by wenin819@gmail.com on 2014-10-08.
 */
@Controller
@RequestMapping(SysUserController.BASE_URL)
public class SysUserController extends BaseCrudController<SysUser> {

    public static final String BASE_URL = "/system/SysUser/";

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    protected String getBasePagePath() {
        return "system/SysUser";
    }

    @Override
    protected String getBasePermission() {
        return "system:SysUser";
    }

    @Override
    protected SysUserService getService() {
        return sysUserService;
    }

    @Override
    public String toForm(boolean autoQuery, SysUser entry, Model model, HttpServletRequest request) {
        model.addAttribute("roles", SecurityUtils.getAllRole());
        String toForm = super.toForm(autoQuery, entry, model, request);
        entry = (SysUser) model.asMap().get(WebUtils.ENTRY);
        entry.setRoleIds(sysRoleService.queryRoleIdsByUser(entry));
        return toForm;
    }

    @Override
    @Transient
    public String save(SysUser entity, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String save = super.save(entity, model, redirectAttributes, request);
        if(redirect2ListPage.equals(save)) {
            sysRoleService.saveRoleUserRelations(entity, entity.getRoleIds());
        }
        return save;
    }

    @RequiresAuthentication
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(String loginName, String password, Model model) {
        model.addAttribute("user", SecurityUtils.getCurrentUser());
        if(StringUtils.isNotBlank(password)) {
            try {
                getService().modifyPwd(loginName, password);
                addMessages(model, "修改用户名和密码成功");
                return WebUtils.MSG_PAGE;
            } catch (Exception e) {
                addMessages(model, "修改用户名和密码失败，失败原因为：" + e.getMessage());
            }
        }
        return getBasePagePath() + "ModifyPwd";
    }
}
