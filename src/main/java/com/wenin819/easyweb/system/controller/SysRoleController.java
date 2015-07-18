package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.persistence.mybatis.Criteria;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.utils.StringUtils;
import com.wenin819.easyweb.core.utils.WebUtils;
import com.wenin819.easyweb.core.web.ActionType;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.system.model.SysRole;
import com.wenin819.easyweb.system.service.SysMenuService;
import com.wenin819.easyweb.system.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Resource
    private SysMenuService sysMenuService;

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

    @Override
    public String toForm(SysRole entry, Model model, HttpServletRequest request) {
        String toForm = super.toForm(entry, model, request);
        entry = (SysRole) model.asMap().get(WebUtils.ENTRY);

        entry.setMenuIds(sysRoleService.queryMenuIdsByRole(entry));
        model.addAttribute("menus", sysMenuService.queryAllMenu());
        return toForm;
    }

    @Override
    public String save(SysRole entity, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String save = super.save(entity, model, redirectAttributes, request);
        sysRoleService.saveRoleMenuRelations(entity, entity.getMenuIds());
        return save;
    }
}
