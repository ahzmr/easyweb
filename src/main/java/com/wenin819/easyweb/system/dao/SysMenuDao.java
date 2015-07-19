/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.system.dao;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.core.persistence.mybatis.MybatisTreeBaseDao;
import com.wenin819.easyweb.system.model.SysMenu;

import com.wenin819.easyweb.system.model.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wenin819@gmail.com
 */
@Repository
public interface SysMenuDao extends MybatisTreeBaseDao<SysMenu> {

    /**
     * 查询用户对应的所有菜单
     * @param user 用户
     * @return
     */
    List<SysMenu> queryMyMemus(SysUser user);

}
