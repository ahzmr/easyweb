/**
* There are <a href="https://github.com/wenin819/easyweb">EasyWeb</a> code generation
*/
package com.wenin819.easyweb.system.dao;

import com.wenin819.easyweb.core.persistence.mybatis.MybatisBaseDao;
import com.wenin819.easyweb.system.model.SysLoginLog;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author wenin819@gmail.com
 */
@Repository
public interface SysLoginLogDao extends MybatisBaseDao<SysLoginLog> {

    /**
     * 更新退出日期，如果退出日期不为空时，不更新
     * @param id 日志标识
     * @param logoutDate 退出时间
     * @return
     */
    int updateLogoutDate(@Param("id") String id, @Param("logoutDate") Date logoutDate);
}
