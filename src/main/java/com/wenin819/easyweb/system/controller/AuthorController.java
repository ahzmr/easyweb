package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.utils.SecurityUtils;
import com.wenin819.easyweb.core.utils.WebUtils;
import com.wenin819.easyweb.core.web.BaseController;

import com.wenin819.easyweb.system.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Controller
@RequestMapping("/")
public class AuthorController extends BaseController {

    public static String secucessUrl = "/";
    @Resource
    private SysUserService sysUserService;

    @RequestMapping(value = "login.html", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String login() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:" + secucessUrl;
        }
        return "system/login";
    }

    @RequestMapping(value = "login.html", method = RequestMethod.POST)
    public String login(String username, String password, boolean rememberMe, Model model, RedirectAttributes attributes) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            SecurityUtils.getSubject().login(token);
            if (logger.isInfoEnabled()) {
                logger.info("用户[" + username + "]成功登陆，登陆IP为:" + WebUtils.getRealRemoteAddr());
            }

            sysUserService.updateUserLoginInfo(username);
            return "redirect:" + secucessUrl;
        } catch (AuthenticationException e) {
            if (logger.isWarnEnabled()) {
                logger.warn("用户[" + username + "]登陆失败，登陆IP为:" + WebUtils.getRealRemoteAddr());
            }
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
