/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.system.model;

import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.IFiledEnum;
import com.wenin819.easyweb.core.utils.ConfigUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 * @author wenin819@gmail.com
 */
public class SysUser extends BaseEntity<SysUser.TE> implements Serializable {

    private static final long serialVersionUID = 3421251191328239924L;
    /**
     * 编号
     */
    private String id;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 工号
     */
    private String no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 角色标识列表
     */
    private List<String> roleIds;
    /**
     * 最后登陆IP
     */
    private String loginIp;
    /**
     * 最后登陆时间
     */
    private Date loginDate;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 删除标记
     */
    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getLoginIp() {
        return loginIp;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
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
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public static enum TE implements IFiledEnum {
        id("id"),  // 编号
        loginName("login_name"),  // 登录名
        password("password"),  // 密码
        no("no"),  // 工号
        name("name"),  // 姓名
        email("email"),  // 邮箱
        phone("phone"),  // 电话
        mobile("mobile"),  // 手机
        loginIp("login_ip"),  // 最后登陆IP
        loginDate("login_date"),  // 最后登陆时间
        createBy("create_by"),  // 创建者
        createDate("create_date"),  // 创建时间
        updateBy("update_by"),  // 更新者
        updateDate("update_date"),  // 更新时间
        remarks("remarks"),  // 备注信息
        delFlag("del_flag"),  // 删除标记
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
            return "sys_user";
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
