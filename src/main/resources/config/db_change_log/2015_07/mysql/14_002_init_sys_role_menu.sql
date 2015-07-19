CREATE TABLE
    sys_role_menu
    (
        role_id VARCHAR(64) NOT NULL COMMENT '角色编号',
        menu_id VARCHAR(64) NOT NULL COMMENT '菜单编号',
        create_by VARCHAR(64) COMMENT '创建者',
        create_date DATETIME COMMENT '创建时间',
        PRIMARY KEY (role_id, menu_id)
    ) DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';

INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '1', 'wenin819', '2015-07-19 10:14:03');
INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '11', 'wenin819', '2015-07-19 10:14:03');
INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '12', 'wenin819', '2015-07-19 10:14:03');
INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '2', 'wenin819', '2015-07-19 10:14:03');
INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '3', 'wenin819', '2015-07-19 10:14:03');
INSERT INTO sys_role_menu (role_id, menu_id, create_by, create_date) VALUES ('1', '4', 'wenin819', '2015-07-19 10:14:03');