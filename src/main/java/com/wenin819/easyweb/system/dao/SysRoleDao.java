/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.system.dao;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.system.model.SysRole;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * 删除角色对应的所有菜单关系
     * @param role 角色
     * @return
     */
    int deleteMenuRelationByRole(SysRole role);

    /**
     * 新增角色与菜单关系
     * @param roleId 角色标识
     * @param menuId 菜单标识
     * @return
     */
    int insertMenuRoleRelation(@Param("roleId") String roleId, @Param("menuId")String menuId);

}
