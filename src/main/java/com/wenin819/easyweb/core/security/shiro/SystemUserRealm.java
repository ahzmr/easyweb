package com.wenin819.easyweb.core.security.shiro;

import com.wenin819.easyweb.core.utils.Configs;
import com.wenin819.easyweb.core.utils.SecurityUtils;
import com.wenin819.easyweb.system.model.SysMenu;
import com.wenin819.easyweb.system.model.SysUser;
import com.wenin819.easyweb.system.service.SysUserService;
import com.wenin819.easyweb.utils.LoginErrCntUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.List;

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
        info.addStringPermission("contacts:edit:" + user.getNo());

        // 处理超级管理员权限
        if(Configs.systemSuperAdminUserIds().contains(user.getId())) {
            info.addStringPermission("*");
            info.addRole(SecurityUtils.SUPER_ADMIN_NAME);
        } else {
            List<SysMenu> allMenu = SecurityUtils.getAllMenu();
            for (SysMenu sysMenu : allMenu) {
                if(null != sysMenu.getPermission()) {
                    info.addStringPermission(sysMenu.getPermission());
                }
            }
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String errMsg = LoginErrCntUtils.checkIsMaxLoginErrCnt(upToken.getUsername());
        if(null != errMsg) {
            throw new AuthenticationException(errMsg);
        }
        final SysUser sysUser = sysUserService.queryByLoginName(upToken.getUsername());
        if(null == sysUser) {
            throw new UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(),
                ByteSource.Util.bytes(sysUser.getLoginName()), getName());
    }
}
