/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.system.dao;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.system.model.SysRole;

import com.wenin819.easyweb.system.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author wenin819@gmail.com
 */
@Repository
public interface SysRoleDao extends MybatisBaseDao<SysRole> {

    /**
     * 通过角色查关联菜单ID列表
     * @param role 角色
     * @return
     */
    List<String> queryMenuIdsByRole(SysRole role);

    /**
     * 通过用户查询角色Id列表
     * @param user 用户
     * @return
     */
    List<String> queryRoleIdsByUser(SysUser user);

    /**
     * 删除角色对应的所有菜单关系
     * @param role 角色
     * @return
     */
    int deleteMenuRelationByRole(SysRole role);

    /**
     * 新增角色与菜单关系
     * @param relation 菜单标识
     * @return
     */
    int insertRoleMenuRelation(RoleMenuRelation relation);

    /**删除角色对应的所有用户关系
     *
     * @param role 角色
     * @return
     */
    int deleteUserRelationByRole(SysRole role);

    /**
     * 删除用户对应的所有角色关系
     * @param user 用户
     * @return
     */
    int deleteUserRelationByUser(SysUser user);

    /**
     * 新增角色与用户关系
     * @param relation 菜单标识
     * @return
     */
    int insertRoleUserRelation(RoleUserRelation relation);

    /**
     * 角色菜单关系
     */
    public static class RoleMenuRelation {
        public RoleMenuRelation(String roleId, String menuId) {
            this.roleId = roleId;
            this.menuId = menuId;
        }
        private String roleId;   // 角色ID
        private String menuId;   // 菜单ID
        private String createBy; // 创建用户
        private Date createDate; //  创建时间

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
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
    }

    /**
     * 角色用户关系
     */
    public static class RoleUserRelation {
        public RoleUserRelation(String roleId, String userId) {
            this.roleId = roleId;
            this.userId = userId;
        }
        private String roleId;   // 角色ID
        private String userId;   // 菜单ID
        private String createBy; // 创建用户
        private Date createDate; //  创建时间

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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
    }
}
