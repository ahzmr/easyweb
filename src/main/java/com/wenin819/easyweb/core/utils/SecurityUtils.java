package com.wenin819.easyweb.core.utils;

import com.wenin819.easyweb.core.exception.DataException;
import com.wenin819.easyweb.core.security.CodeUtils;
import com.wenin819.easyweb.system.model.SysMenu;
import com.wenin819.easyweb.system.model.SysRole;
import com.wenin819.easyweb.system.model.SysUser;

import com.wenin819.easyweb.system.service.SysMenuService;
import com.wenin819.easyweb.system.service.SysRoleService;
import org.apache.shiro.crypto.hash.Sha512Hash;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

/**
 * 安全工具类
 * Created by wenin819@gmail.com on 2014-10-08.
 */
public class SecurityUtils extends org.apache.shiro.SecurityUtils {

    public static final int HASH_ITERATIONS = 1024;
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * 获得当前用户
     * @return
     */
    public static SysUser getCurrentUser() {
        return (SysUser) getSubject().getPrincipal();
    }

    /**
     * 生成保存到数据库的最终密码
     * @param pwd 密码
     * @param salt 盐值
     * @return
     */
    public static String genFinalPasswd(String pwd, String salt) {
        return new Sha512Hash(pwd, salt, HASH_ITERATIONS).toBase64();
    }

    /**
     * 生成HMacSha256编码
     * @param data 数据
     * @param key key
     * @return
     */
    public static String genHMacSha256(String data, String key) {
        SecretKeySpec signingKey = new SecretKeySpec(CodeUtils.toBytes(key), HMAC_SHA256_ALGORITHM);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            return CodeUtils.toBase64String(mac.doFinal(CodeUtils.toBytes(data)));
        } catch (Exception e) {
            throw new DataException("生成HMacSha256编码失败", e);
        }
    }

    /**
     * 判断用户是否为超级管理员用户
     * @param userId 用户标识
     * @return
     */
    public static boolean isSuperAdmin(String userId) {
        return Configs.systemSuperAdminUserIds().contains(userId);
    }

    /**
     * 判断当前用户是否为超级管理员用户
     * @return
     */
    public static boolean isSuperAdmin() {
        SysUser currentUser = getCurrentUser();
        return null != currentUser && isSuperAdmin(currentUser.getId());
    }

    /**
     * 得到当前用户所有菜单
     * @return
     */
    public static List<SysMenu> getAllMenu() {
        if(isSuperAdmin()) {
            return getSysMenuService().queryAllMenus();
        } else {
            return getSysMenuService().queryMyMenus(getCurrentUser());
        }
    }

    /**
     * 得到当前用户所有角色
     * @return
     */
    public static List<SysRole> getAllRole() {
        if(isSuperAdmin()) {
            return getSysRoleService().queryAllRoles();
        } else {
            return getSysRoleService().queryMyRoles(getCurrentUser());
        }
    }

    private static SysRoleService sysRoleService;
    private static SysRoleService getSysRoleService() {
        if(null == sysRoleService) {
            sysRoleService = SpringContextUtils.getBean(SysRoleService.class);
        }
        return sysRoleService;
    }
    private static SysMenuService sysMenuService;
    private static SysMenuService getSysMenuService() {
        if(null == sysMenuService) {
            sysMenuService = SpringContextUtils.getBean(SysMenuService.class);
        }
        return sysMenuService;
    }
}
