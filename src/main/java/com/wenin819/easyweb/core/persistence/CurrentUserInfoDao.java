package com.wenin819.easyweb.core.persistence;

/**
 * 当前操作用户信息访问接口，MybatisBaseService会自动调用此接口获取当前操作人，从而自动记录当前更新人和创建人
 * @author wenin819@gmail.com
 */
public interface CurrentUserInfoDao {

    /**
     * 获得当前用户ID
     * @return 当前用户标识
     */
    String getId();

    /**
     * 获得当前用户名称
     * @return 当前用户名称
     */
    String getName();

    /**
     * 获得当前用户登陆名称
     * @return 当前用户登陆名称
     */
    String getLoginName();
}
