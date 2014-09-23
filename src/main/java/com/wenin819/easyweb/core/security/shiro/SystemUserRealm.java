package com.wenin819.easyweb.core.security.shiro;

import com.wenin819.easyweb.system.model.SysUser;
import com.wenin819.easyweb.system.service.SysUserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Created by wenin819@gmail.com on 2014-09-22.
 */
public class SystemUserRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException();
        }
        final SysUser user = (SysUser) getAvailablePrincipal(principals);
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("contacts:edit:" + user.getLoginName());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        final SysUser sysUser = sysUserService.queryByLoginName(upToken.getUsername());
        if(null == sysUser) {
            throw new AuthenticationException();
        }
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), getName());
    }
}
