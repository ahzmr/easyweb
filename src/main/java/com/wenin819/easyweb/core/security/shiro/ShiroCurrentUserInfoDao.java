package com.wenin819.easyweb.core.security.shiro;

import com.wenin819.easyweb.core.persistence.CurrentUserInfoDao;
import com.wenin819.easyweb.core.utils.SecurityUtils;

/**
 * @author wenin819@gmail.com
 * @date 2015-07-12.
 */
public class ShiroCurrentUserInfoDao implements CurrentUserInfoDao {

    @Override
    public String getId() {
        try {
            return SecurityUtils.getCurrentUser().getId();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getName() {
        try {
            return SecurityUtils.getCurrentUser().getName();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getLoginName() {
        try {
            return SecurityUtils.getCurrentUser().getLoginName();
        } catch (Exception e) {
            return null;
        }
    }
}
