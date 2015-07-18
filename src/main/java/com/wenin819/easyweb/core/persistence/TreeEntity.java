package com.wenin819.easyweb.core.persistence;

import com.wenin819.easyweb.core.utils.ConfigUtils;

/**
 * 所有树形实体的基类
 * @author wenin819@gmail.com
 * @date 2015-07-17.
 */
public abstract class TreeEntity<TE extends IFiledEnum> extends BaseEntity<TE> {

    /**
     * 父编号
     */
    private String parentId;
    /**
     * 所有父编号
     */
    private String parentIds;

    public String getParentId() {
        return parentId;
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

    public static enum TE implements IFiledEnum {
        id("id"),  // 编号
        parentId("parent_id"),  // 父编号
        parentIds("parent_ids"),  // 所有父编号
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
