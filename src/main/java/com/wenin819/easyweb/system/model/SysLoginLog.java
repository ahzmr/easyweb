/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.system.model;

import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.IFiledEnum;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import java.util.Date;

/**
 * 登录日志表
 * @author wenin819@gmail.com
 */
public class SysLoginLog extends BaseEntity<SysLoginLog.TE> {

    /**
     * 编号
     */
    private String id;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 登录地点
     */
    private String loginLocation;
    /**
     * 用户代理标识
     */
    private String userAgent;
    /**
     * 操作系统
     */
    private String osName;
    /**
     * 操作系统版本
     */
    private String osVersion;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用类型
     */
    private String appType;
    /**
     * 应用版本
     */
    private String appVersion;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 开始创建时间
     */
    private Date createDateStart;
    /**
     * 结束创建时间
     */
    private Date createDateEnd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }
    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public static enum TE implements IFiledEnum {
        id("id"),  // 编号
        loginIp("login_ip"),  // 登录IP
        loginLocation("login_location"),  // 登录地点
        userAgent("user_agent"),  // 用户代理标识
        osName("os_name"),  // 操作系统
        osVersion("os_version"),  // 操作系统版本
        deviceName("device_name"),  // 设备名称
        deviceType("device_type"),  // 设备类型
        appName("app_name"),  // 应用名称
        appType("app_type"),  // 应用类型
        appVersion("app_version"),  // 应用版本
        remarks("remarks"),  // 备注信息
        createBy("create_by"),  // 创建者
        createDate("create_date"),  // 创建时间
        ;
        private String filedName;
        private TE(String filedName) {
            this.filedName = filedName;
        }
        @Override
        public String getFiledName() {
            return this.filedName;
        }
        @Override
        public String getTableName() {
            return "sys_login_log";
        }
        @Override
        public String getTableSchema() {
            return ConfigUtils.get().getValue("schema.configPlat");
        }
        @Override
        public String toString() {
            return this.filedName;
        }
    }
}
