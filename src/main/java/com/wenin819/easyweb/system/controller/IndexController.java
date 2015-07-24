package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.utils.JsonUtils;
import com.wenin819.easyweb.core.utils.SecurityUtils;
import com.wenin819.easyweb.core.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    @RequestMapping(value = {"", "index"}, method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(Model model) {
        model.addAttribute("menus", JsonUtils.objectToJson(SecurityUtils.getAllMenu()));
        return "system/index";
    }
}
