package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.utils.JsonUtils;
import com.wenin819.easyweb.core.web.BaseController;

import com.wenin819.easyweb.system.service.SysMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    @Resource
    private SysMenuService sysMenuService;

    @RequestMapping(value = {"", "index.html"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(Model model) {
        model.addAttribute("menus", JsonUtils.toJsonString(sysMenuService.queryAllMenu()));
        return "system/index";
    }
}
