package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.BaseSpringTest;
import com.wenin819.easyweb.core.db.CriteriaQuery;
import com.wenin819.easyweb.core.security.shiro.CodeUtils;
import com.wenin819.easyweb.core.util.SecurityUtils;
import com.wenin819.easyweb.system.model.SysUser;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.util.List;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

public class SysUserServiceTest extends BaseSpringTest {

    @Resource
    private SysUserService sysUserService;

    @Test
    public void testCheckLoginNameExists() throws Exception {
        final boolean exists = sysUserService.checkLoginNameExists("0707011003");
        assertTrue(exists);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    public void testModifyPwd() throws Exception {
        final SysUser sysUser = sysUserService.queryByLoginName("0707011003");
        modifyPwd(sysUser);
    }

    protected void modifyPwd(SysUser sysUser) {
        final String hash = SecurityUtils.genHMacSha256(sysUser.getLoginName(), sysUser.getLoginName());
        loggerJson(hash);
        sysUser.setPassword(SecurityUtils.genFinalPasswd(hash, sysUser.getLoginName()));
        sysUserService.update(sysUser);
        loggerJson(sysUser);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testBatchModifyPwd() throws Exception {
        final List<SysUser> sysUsers = sysUserService.queryByCriteria(new CriteriaQuery());
        if(null != sysUsers) {
            for (SysUser sysUser : sysUsers) {
                modifyPwd(sysUser);
            }
        }
    }
}
