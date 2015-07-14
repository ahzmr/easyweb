package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.persistence.Page;
import com.wenin819.easyweb.core.persistence.mybatis.Criteria;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.utils.WebUtils;
import com.wenin819.easyweb.core.utils.tree.ITreeNodeAdapter;
import com.wenin819.easyweb.core.utils.tree.TreeSortUtils;
import com.wenin819.easyweb.core.web.ActionType;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.system.model.SysMenu;
import com.wenin819.easyweb.system.service.SysMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wenin819@gmail.com
 */
@Controller
@RequestMapping(SysMenuController.BASE_URL)
public class SysMenuController extends BaseCrudController<SysMenu> {

    public static final String BASE_URL = "/system/SysMenu/";
    private static final String BASE_PATH = "system/SysMenu";

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
        return "system:SysMenu";
    }

    @Override
    protected MybatisBaseService<SysMenu> getService() {
        return sysMenuService;
    }

    @Override
    protected CriteriaQuery genCriteriaes(SysMenu entity, HttpServletRequest request, Model model) {
        CriteriaQuery query = super.genCriteriaes(entity, request, model);
        query.createAndCriteria().equalTo(SysMenu.TE.delFlag, ConfigEnum.DEL_FLAG_NORMAL);
        query.addOrder(SysMenu.TE.sort, true);
        return query;
    }

    @Override
    public String toList(Page<SysMenu> page, SysMenu entity, Model model, HttpServletRequest request) {
        String view = super.toList(page, entity, model, request);
        List<SysMenu> sysMenus = TreeSortUtils.sort4Tree(page, null, new ITreeNodeAdapter<SysMenu>() {
            @Override
            public String getId(SysMenu node) {
                return null == node ? null : node.getId();
            }

            @Override
            public String getPid(SysMenu node) {
                return null == node ? null : node.getParentId();
            }
        });
        page.clear();
        page.addAll(sysMenus);
        return view;
    }
}
