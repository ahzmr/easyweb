package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.web.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    @RequestMapping({"", "index.html"})
    public String index() {
        return "system/index";
    }
}
