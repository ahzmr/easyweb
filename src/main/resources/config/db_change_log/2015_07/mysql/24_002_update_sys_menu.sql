INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('20', '0', '0,', '日志查询', 90, null, null, '1', null, null, 'wenin819', '2015-07-24 21:40:09', 'wenin819', '2015-07-24 21:40:09', '0');
INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('21', '20', '0,20,', '登陆日志', 10, 'system/SysLoginLog/', null, '1', 'system:SysLoginLog:view', null, 'wenin819', '2015-07-24 21:41:18', 'wenin819', '2015-07-24 21:41:18', '0');

INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '20', 'wenin819', '2015-07-24 21:42:01');
INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '21', 'wenin819', '2015-07-24 21:42:01');
