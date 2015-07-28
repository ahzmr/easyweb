package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.exception.DataException;
import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.*;
import com.wenin819.easyweb.system.dao.SysUserDao;
import com.wenin819.easyweb.system.model.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by wenin819@gmail.com on 2014-09-23.
 */
@Service
public class SysUserService extends MybatisBaseService<SysUser> {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public String getTableName() {
        return Configs.schameConfigplat() + ".sys_user";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MybatisBaseDao<SysUser> getDao() {
        return sysUserDao;
    }

    @Override
    public CriteriaQuery genCriteriaQuery(SysUser entity) {
        CriteriaQuery query = super.genCriteriaQuery(entity);
        if(StringUtils.isNoneBlank(entity.getLoginName())) {
            query.createAndCriteria().like(SysUser.TE.loginName, "%" + entity.getLoginName() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getNo())) {
            query.createAndCriteria().like(SysUser.TE.no, "%" + entity.getNo() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getName())) {
            query.createAndCriteria().like(SysUser.TE.name, "%" + entity.getName() + "%");
        }
        if(StringUtils.isNoneBlank(entity.getMobile())) {
            query.createAndCriteria().like(SysUser.TE.mobile, "%" + entity.getMobile() + "%");
        }
        query.addOrder(SysUser.TE.no, true);
        return query;
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

    @Override
    public String validate(SysUser entity) {
        CriteriaQuery query = new CriteriaQuery();
        if(null != entity.getId()) {
            query.createAndCriteria().notEqualTo(SysUser.TE.id, entity.getId());
        }
        String msg;
        if(null != entity.getLoginName()) {
            query.createAndCriteria().equalTo(SysUser.TE.loginName, entity.getLoginName());
            msg = "登录名";
        } else if(null != entity.getNo()) {
            query.createAndCriteria().equalTo(SysUser.TE.no, entity.getNo());
            msg = "工号";
        } else {
            return "登录名不能为空";
        }
        if(sysUserDao.countByCriteria(query) > 0) {
            return msg + "重复";
        }
        List<String> roleIds = entity.getRoleIds();
        if(null != roleIds) {
            Set<String> roleIdSet = SecurityUtils.getAllRoleIdSet();
            for (String roleId : roleIds) {
                if(!roleIdSet.contains(roleId)) {
                    return "保存的角色列表，超过您的权限范围，请重新选择";
                }
            }
        }
        return super.validate(entity);
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
