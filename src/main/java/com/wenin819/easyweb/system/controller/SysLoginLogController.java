package com.wenin819.easyweb.system.controller;

import com.wenin819.easyweb.core.persistence.mybatis.Criteria;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.web.ActionType;
import com.wenin819.easyweb.core.web.BaseCrudController;
import com.wenin819.easyweb.system.model.SysLoginLog;
import com.wenin819.easyweb.system.service.SysLoginLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author wenin819@gmail.com
 */
@Controller
@RequestMapping(SysLoginLogController.BASE_URL)
public class SysLoginLogController extends BaseCrudController<SysLoginLog> {

    public static final String BASE_URL = "/system/SysLoginLog/";
    private static final String BASE_PATH = "system/SysLoginLog";

    @Resource
    private SysLoginLogService sysLoginLogService;

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
        return "system:SysLoginLog";
    }

    @Override
    protected MybatisBaseService<SysLoginLog> getService() {
        return sysLoginLogService;
    }
}
