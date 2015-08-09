package com.wenin819.easyweb.system.service;

import com.wenin819.easyweb.core.persistence.mybatis.CriteriaQuery;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.service.mybatis.MybatisBaseService;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import com.wenin819.easyweb.core.utils.SecurityUtils;
import com.wenin819.easyweb.core.utils.WebUtils;
import com.wenin819.easyweb.system.dao.SysLoginLogDao;
import com.wenin819.easyweb.system.model.SysLoginLog;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 * @author wenin819@gmail.com
 */
@Service
public class SysLoginLogService extends MybatisBaseService<SysLoginLog> {

    public static final String LOGIN_LOG_ID_KEY = "loginLogId";

    @Resource
    private SysLoginLogDao sysLoginLogDao;

    @Override
    public String getTableName() {
        return ConfigUtils.get().getValue("schema.configPlat") + ".sys_login_log";
    }

    @Override
    public String getIdKey() {
        return "id";
    }

    @Override
    public MybatisBaseDao<SysLoginLog> getDao() {
        return sysLoginLogDao;
    }

    @Override
    public CriteriaQuery genCriteriaQuery(SysLoginLog entity) {
        CriteriaQuery query = super.genCriteriaQuery(entity);
        if(null != entity.getCreateBy()) {
            query.createAndCriteria().like(SysLoginLog.TE.createBy, "%" + entity.getCreateBy() + "%");
        }
        if(null != entity.getCreateDateStart()) {
            query.createAndCriteria().greaterThanOrEqualTo(SysLoginLog.TE.createDate, entity.getCreateDateStart());
        }
        if(null != entity.getCreateDateEnd()) {
            query.createAndCriteria().lessThan(SysLoginLog.TE.createDate, entity.getCreateDateEnd());
        }
        query.addOrder(SysLoginLog.TE.id, false);
        return query;
    }

    /**
     * 保存登录日志
     * @param username 用户名
     * @param realRemoteAddr 登录地址
     * @param userAgentStr 用户代理
     */
    public void saveLoginLog(String username, String realRemoteAddr, String userAgentStr) {
        SysLoginLog loginLog = new SysLoginLog();

        loginLog.setId(genId());
        loginLog.setCreateBy(username);
        loginLog.setCreateDate(new Date());
        loginLog.setUserAgent(userAgentStr);

        loginLog.setLoginIp(realRemoteAddr);
        loginLog.setLoginLocation(WebUtils.getLocationNameByIp(realRemoteAddr));

        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        OperatingSystem system = userAgent.getOperatingSystem();

        loginLog.setOsName(system.getGroup().getName());
        loginLog.setOsVersion(system.getName());
        loginLog.setDeviceName(system.getManufacturer().getName());
        loginLog.setDeviceType(system.getDeviceType().getName());

        Browser browser = userAgent.getBrowser();

        loginLog.setAppName(browser.getGroup().getName());
        loginLog.setAppType(browser.getBrowserType().getName());
        loginLog.setAppVersion(browser.getName());

        Session session = SecurityUtils.getSubject().getSession();
        loginLog.setSessionId((String) session.getId());

        sysLoginLogDao.insert(loginLog);

        session.setAttribute(LOGIN_LOG_ID_KEY, loginLog.getId());
    }

    /**
     * 更新退出时间
     * @param loginLogId 登录日志ID
     * @param logoutDate 退出时间
     */
    public void updateLogoutDate(String loginLogId, Date logoutDate) {
        sysLoginLogDao.updateLogoutDate(loginLogId, logoutDate);
    }
}
