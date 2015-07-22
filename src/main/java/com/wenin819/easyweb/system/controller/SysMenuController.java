package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.persistence.Page;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.tree.ITreeNodeAdapter;
import com.wenin819.easyweb.core.utils.tree.TreeSortUtils;
import com.wenin819.easyweb.core.utils.tree.support.TreeNodeWrapper;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.system.model.SysMenu;
import com.wenin819.easyweb.system.service.SysMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @RequestMapping("queryByParent")
    @ResponseBody
    public List<TreeNodeWrapper<SysMenu>> queryByParent(SysMenu entity) {
        List<SysMenu> sysMenus = sysMenuService.queryByParent(entity);
        List<TreeNodeWrapper<SysMenu>> list = new ArrayList<TreeNodeWrapper<SysMenu>>(sysMenus.size());
        ITreeNodeAdapter<SysMenu> adapter = sysMenuService.menuTreeNodeAdapter;
        for (SysMenu sysMenu : sysMenus) {
            list.add(new TreeNodeWrapper<SysMenu>(sysMenu, adapter));
        }
        return list;
    }

    @RequestMapping("saveSorts")
    public String saveSorts(String[] ids, Integer[] oldSorts, Integer[] sorts) {
        sysMenuService.batchUpdateSorts(ids, oldSorts, sorts);
        return redirect2ListPage;
    }
}
