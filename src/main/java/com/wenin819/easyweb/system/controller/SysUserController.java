package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.db.BaseService;
import com.wenin819.easyweb.core.util.SecurityUtils;
import com.wenin819.easyweb.core.util.StringUtils;
import com.wenin819.easyweb.core.util.WebUtils;
import com.wenin819.easyweb.core.web.BaseEntityController;
import com.wenin819.easyweb.system.model.SysUser;
import com.wenin819.easyweb.system.service.SysUserService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wenin819@gmail.com on 2014-10-08.
 */
@Controller("/sys/user/")
public class SysUserController extends BaseEntityController<SysUser> {

    @Resource
    private SysUserService sysUserService;

    @Override
    protected String getBasePagePath() {
        return "system/user";
    }

    @Override
    protected SysUserService getService() {
        return sysUserService;
    }

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "/modifyPwd.html")
    public String modifyPwd(String loginName, String password, Model model) {
        if(StringUtils.isNotBlank(password)) {
            try {
                getService().modifyPwd(loginName, password);
                model.addAttribute(WebUtils.MSG, "修改用户名和密码成功");
            } catch (Exception e) {
                model.addAttribute(WebUtils.MSG, "修改用户名和密码失败，失败原因为：" + e.getMessage());
            }
        }
        return getBasePagePath() + "ModifyPwd";
    }
}
