package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigEnum;
import com.wenin819.easyweb.core.utils.StringUtils;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.system.model.SysDict;
import com.wenin819.easyweb.system.service.SysDictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wenin819@gmail.com
 */
@Controller
@RequestMapping(SysDictController.BASE_URL)
public class SysDictController extends BaseCrudController<SysDict> {

    public static final String BASE_URL = "/system/SysDict/";
    private static final String BASE_PATH = "system/SysDict";

    @Resource
    private SysDictService sysDictService;

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
        return "system:SysDict";
    }

    @Override
    protected MybatisBaseService<SysDict> getService() {
        return sysDictService;
    }

}
