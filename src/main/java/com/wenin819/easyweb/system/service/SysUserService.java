package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.db.BaseService;
import com.wenin819.easyweb.system.model.SysUser;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
public interface SysUserService extends BaseService<SysUser> {

    SysUser queryByLoginName(String username);

    /**
     * 检查登录名是否重复
     * @param loginName 登录名
     * @return 重复返回true
     */
    boolean checkLoginNameExists(String loginName);

    void modifyPwd(String loginName, String password) throws Exception;
}
