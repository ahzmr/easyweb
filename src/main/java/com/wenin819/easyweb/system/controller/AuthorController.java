package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Controller
@RequestMapping("/")
public class AuthorController extends BaseController {

    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.HEAD})
    public String login() {
        return "system/login";
    }


}
