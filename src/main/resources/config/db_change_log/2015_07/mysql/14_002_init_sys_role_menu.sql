CREATE TABLE
    sys_role_menu
    (
        role_id VARCHAR(64) NOT NULL COMMENT '角色编号',
        menu_id VARCHAR(64) NOT NULL COMMENT '菜单编号',
        create_by VARCHAR(64) COMMENT '创建者',
        create_date DATETIME COMMENT '创建时间',
        PRIMARY KEY (role_id, menu_id)
    ) DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';