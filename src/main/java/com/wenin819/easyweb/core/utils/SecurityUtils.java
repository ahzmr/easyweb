package com.wenin819.easyweb.core.utils;

import com.wenin819.easyweb.core.exception.DataException;
import com.wenin819.easyweb.core.security.CodeUtils;
import com.wenin819.easyweb.system.model.SysMenu;
import com.wenin819.easyweb.system.model.SysRole;
import com.wenin819.easyweb.system.model.SysUser;

import com.wenin819.easyweb.system.service.SysMenuService;
import com.wenin819.easyweb.system.service.SysRoleService;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 安全工具类
 * Created by wenin819@gmail.com on 2014-10-08.
 */
public class SecurityUtils extends org.apache.shiro.SecurityUtils {

    public static final int HASH_ITERATIONS = 1024;
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    public static final String SUPER_ADMIN_NAME = "superAdmin";    // 超级管理员虚拟角色
    public static final String ERR_LOGIN_CNT_CACHE_KEY = "errLoginCntCache";    // 错误登陆次数缓存
    public static final String SECURITY_CACHE = "securityCache";    // 权限相关缓存
    public static final String CUR_ROLE_LIST = "curRoleList";
    public static final String CUR_ROLE_ID_Set = "curRoleIdSet";
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

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
        Set<String> curRoleIds = getAllRoleIdSet();
        List<SysMenu> cache = CacheUtils.get(SECURITY_CACHE, curRoleIds, null);
        if(null != cache) {
            return cache;
        }
        synchronized (SECURITY_CACHE) {
            cache = CacheUtils.get(SECURITY_CACHE, curRoleIds, null);
            if(null != cache) {
                return cache;
            }
            if (isSuperAdmin()) {
                cache = getSysMenuService().queryAllMenus();
            } else {
                cache = getSysMenuService().queryMyMenus(getCurrentUser());
            }
            CacheUtils.put(SECURITY_CACHE, curRoleIds, cache);
            logger.debug("刷新菜单列表缓存：{}", curRoleIds);
            return cache;
        }
    }

    /**
     * 更新菜单缓存
     */
    public static void updateMenuCache() {
        CacheUtils.clear(SECURITY_CACHE);
    }

    /**
     * 得到当前用户所有角色
     * @return
     */
    public static List<SysRole> getAllRole() {
        List<SysRole> cache = getCache(CUR_ROLE_LIST);
        if(null != cache) {
            return cache;
        }
        synchronized (CUR_ROLE_LIST) {
            cache = getCache(CUR_ROLE_LIST);
            if(null != cache) {
                return cache;
            }
            if (isSuperAdmin()) {
                cache = getSysRoleService().queryAllRoles();
            } else {
                cache = getSysRoleService().queryMyRoles(getCurrentUser());
            }
            putCache(CUR_ROLE_LIST, cache);
            logger.debug("刷新角色列表缓存：{}", CUR_ROLE_LIST);
        }
        return cache;
    }

    /**
     * 更新角色缓存
     */
    public static void updateRoleCache() {
        removeCache(CUR_ROLE_LIST);
        removeCache(CUR_ROLE_ID_Set);
    }

    /**
     * 得到当前用户所有角色ID集合
     * @return
     */
    public static Set<String> getAllRoleIdSet() {
        Set<String> roleIdSet = getCache(CUR_ROLE_ID_Set);
        if(null != roleIdSet) {
            return roleIdSet;
        }
        synchronized (CUR_ROLE_ID_Set) {
            roleIdSet = getCache(CUR_ROLE_ID_Set);
            if(null != roleIdSet) {
                return roleIdSet;
            }
            List<SysRole> allRole = getAllRole();
            roleIdSet = new HashSet<String>(allRole.size());
            for (SysRole role : allRole) {
                roleIdSet.add(role.getId());
            }
            putCache(CUR_ROLE_ID_Set, roleIdSet);
            logger.debug("刷新角色ID集合缓存：{}", CUR_ROLE_ID_Set);
        }
        return roleIdSet;
    }

    /**
     * 获得当前会话缓存
     * @param key 缓存key
     * @param <T> 值类型
     * @return
     */
    public static <T> T getCache(String key) {
        Session session = getSubject().getSession();
        return null == session ? null : (T) session.getAttribute(key);
    }

    /**
     * 设置当前会话缓存值
     * @param key 缓存key
     * @param value 值
     * @param <T> 值类型
     */
    public static <T> void putCache(String key, T value) {
        Session session = getSubject().getSession(true);
        session.setAttribute(key, value);
    }

    /**
     * 删除当前会话缓存
     * @param key 缓存key
     * @param <T> 值类型
     * @return
     */
    public static <T> T removeCache(String key) {
        Session session = getSubject().getSession();
        return null == session ? null : (T) session.removeAttribute(key);
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
