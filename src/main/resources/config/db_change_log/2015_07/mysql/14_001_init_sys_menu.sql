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