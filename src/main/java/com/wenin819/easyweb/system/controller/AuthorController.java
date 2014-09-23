package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.web.BaseController;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Controller
@RequestMapping("/sys/author/")
public class AuthorController extends BaseController {

    @RequestMapping(value = "login.html", method = RequestMethod.GET)
    public String login() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/";
        }
        return "system/login";
    }

    @RequestMapping(value = "login.html", method = RequestMethod.POST)
    public String login(String username, String password, Model model, RedirectAttributes attributes) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            SecurityUtils.getSubject().login(token);
            return "redirect:/";
        } catch (AuthenticationException e) {
            model.addAttribute("message", "用户名或密码错误");
            return "system/login";
        }
    }

    @RequestMapping("logout.html")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login.html";
    }
}
