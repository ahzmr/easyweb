CREATE TABLE
    sys_menu
    (
        id VARCHAR(64) NOT NULL COMMENT '编号',
        parent_id VARCHAR(64) NOT NULL COMMENT '父编号',
        parent_ids VARCHAR(2047) NOT NULL COMMENT '所有父编号',
        name VARCHAR(100) NOT NULL COMMENT '名称',
        sort decimal(10,0) NOT NULL COMMENT '排序',
        href VARCHAR(2047) COMMENT '链接',
        icon VARCHAR(100) COMMENT '图标',
        is_show CHAR (1) COMMENT '是否显示',
        permission VARCHAR(200) COMMENT '权限标识',
        remarks VARCHAR(255) COMMENT '备注信息',
        create_by VARCHAR(64) COMMENT '创建者',
        create_date DATETIME COMMENT '创建时间',
        update_by VARCHAR(64) COMMENT '更新者',
        update_date DATETIME COMMENT '更新时间',
        del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
        PRIMARY KEY (id),
        INDEX sys_role_update_date (update_date),
        INDEX sys_role_del_flag (del_flag)
    ) DEFAULT CHARSET=utf8 COMMENT='菜单表';

INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('1', '0', '0,', '系统管理', 90, null, null, null, null, '系统管理', 'wenin819', '2015-07-17 15:24:40', 'wenin819', '2015-07-19 10:11:30', '0');
INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('11', '0', '0,', '通讯录', 50, null, null, null, null, null, 'wenin819', '2015-07-18 12:18:35', 'wenin819', '2015-07-19 10:12:09', '0');
INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('12', '11', '0,11,', '通讯录', 10, 'contacts/', null, '1', null, null, 'wenin819', '2015-07-18 14:52:48', 'wenin819', '2015-07-19 10:13:16', '0');
INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('2', '1', '0,1,', '角色管理', 10, 'system/SysRole/', null, '1', 'system:SysRole:view', null, 'wenin819', '2015-07-17 16:32:02', 'wenin819', '2015-07-19 10:13:24', '0');
INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('3', '1', '0,1,', '用户管理', 20, 'system/SysUser/', null, '1', 'system:SysUser:view', null, 'wenin819', '2015-07-17 16:32:49', 'wenin819', '2015-07-19 10:13:29', '0');
INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('4', '1', '0,1,', '菜单管理', 80, 'system/SysMenu/', null, '1', null, null, 'wenin819', '2015-07-18 15:11:06', 'wenin819', '2015-07-19 10:13:33', '0');
