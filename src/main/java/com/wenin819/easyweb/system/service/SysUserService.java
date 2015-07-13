package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.exception.DataException;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.*;
import com.wenin819.easyweb.system.dao.SysUserDao;
import com.wenin819.easyweb.system.model.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Service
public class SysUserService extends MybatisBaseService<SysUser> {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public String getTableName() {
        return ConfigUtils.get().getValue(ConfigName.SCHAME_CONFIGPLAT) + ".sys_user";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MybatisBaseDao<SysUser> getDao() {
        return sysUserDao;
    }

    /**
     * 通过登录名查询
     * @param username 登录名
     * @return
     */
    public SysUser queryByLoginName(String username) {
        if(StringUtils.isBlank(username)) {
            return null;
        }
        CriteriaQuery query = new CriteriaQuery();
        query.createAndCriteria().equalTo(SysUser.TE.loginName, username);
        final List<SysUser> sysUsers = sysUserDao.queryByCriteria(query);
        if(sysUsers.size() == 1) {
            return sysUsers.get(0);
        } else {
            return null;
        }
    }

    /**
     * 判断登录名是否已经存在
     * @param loginName 登录名
     * @return
     */
    public boolean checkLoginNameExists(String loginName) {
        if(StringUtils.isBlank(loginName)) {
            return true;
        }
        CriteriaQuery query = new CriteriaQuery();
        query.createAndCriteria().equalTo(SysUser.TE.loginName, loginName);
        return getDao().countByCriteria(query) > 0;
    }

    /**
     * 修改登录密码
     * @param loginName 登录名
     * @param password 登录密码
     */
    public void modifyPwd(String loginName, String password) {
        final SysUser currentUser = SecurityUtils.getCurrentUser();
        if(null == currentUser || StringUtils.isBlank(currentUser.getId())) {
            throw new DataException("当前用户无法识别，请重新登陆后再试");
        }
        if(StringUtils.isNotBlank(password)) {
            if(StringUtils.isNotBlank(loginName) && !loginName.equals(currentUser.getLoginName())) {
                if(checkLoginNameExists(loginName)) {
                    throw new DataException("登录名已存在");
                }
                currentUser.setLoginName(loginName);
            }
            currentUser.setPassword(SecurityUtils.genFinalPasswd(password, currentUser.getLoginName()));
            save(currentUser);
        }
    }

    /**
     * 更新最后访问时间和访问IP
     * @param loginName 登录名称
     */
    public void updateUserLoginInfo(String loginName) {
        SysUser user = queryByLoginName(loginName);
        user.setLoginIp(WebUtils.getRealRemoteAddr());
        user.setLoginDate(new Date());
        save(user);
    }

}
