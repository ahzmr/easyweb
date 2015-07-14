/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.system.model;

import com.wenin819.easyweb.core.persistence.BaseEntity;
import com.wenin819.easyweb.core.persistence.IFiledEnum;
import com.wenin819.easyweb.core.utils.ConfigUtils;
import java.util.Date;

/**
 * 角色表
 * @author wenin819@gmail.com
 */
public class SysRole extends BaseEntity<SysRole.TE> {

    /**
     * 编号
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        name("name"),  // 名称
        code("code"),  // 编码
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
            return "sys_role";
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
