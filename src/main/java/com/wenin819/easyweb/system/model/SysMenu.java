/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.system.model;

import com.wenin819.easyweb.core.persistence.IFiledEnum;
import com.wenin819.easyweb.core.persistence.TreeEntity;
import com.wenin819.easyweb.core.utils.ConfigUtils;

import java.util.Date;

/**
 * 菜单表
 * @author wenin819@gmail.com
 */
public class SysMenu extends TreeEntity<SysMenu.TE> {

    /**
     * 编号
     */
    private String id;
    /**
     * 父编号
     */
    private String parentId;
    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 所有父编号
     */
    private String parentIds;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 链接
     */
    private String href;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否显示
     */
    private String isShow;
    /**
     * 权限标识
     */
    private String permission;
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
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateDate;
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
    public String getParentId() {
        return parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public static enum TE implements IFiledEnum {
        id("id"),  // 编号
        parentId("parent_id"),  // 父编号
        parentIds("parent_ids"),  // 所有父编号
        name("name"),  // 名称
        sort("sort"),  // 排序
        href("href"),  // 链接
        icon("icon"),  // 图标
        isShow("is_show"),  // 是否显示
        permission("permission"),  // 权限标识
        remarks("remarks"),  // 备注信息
        createBy("create_by"),  // 创建者
        createDate("create_date"),  // 创建时间
        updateBy("update_by"),  // 更新者
        updateDate("update_date"),  // 更新时间
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
            return "sys_menu";
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
