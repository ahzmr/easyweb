INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('8', '1', '0,1,', '字典管理', 90, 'system/SysDict/', null, '1', 'system:SysDict:view', null, 'wenin819', '2015-07-20 22:15:52', 'wenin819', '2015-07-20 22:15:52', '0');
INSERT INTO sys_menu (id, parent_id, parent_ids, name, sort, href, icon, is_show, permission, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('9', '8', '0,1,9,', '修改', 10, null, null, null, 'system:SysDict:edit', null, 'wenin819', '2015-07-20 22:18:24', 'wenin819', '2015-07-20 22:18:24', '0');

update sys_menu set is_show = '0' where is_show is null;

INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '8', 'wenin819', '2015-07-20 23:03:12');
INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '9', 'wenin819', '2015-07-20 23:03:12');