CREATE TABLE
    sys_role
    (
        id VARCHAR(64) NOT NULL COMMENT '编号',
        name VARCHAR(100) NOT NULL COMMENT '名称',
        code VARCHAR(100) NOT NULL COMMENT '编码',
        create_by VARCHAR(64) COMMENT '创建者',
        create_date DATETIME COMMENT '创建时间',
        update_by VARCHAR(64) COMMENT '更新者',
        update_date DATETIME COMMENT '更新时间',
        remarks VARCHAR(255) COMMENT '备注信息',
        del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
        PRIMARY KEY (id),
        INDEX sys_role_update_date (update_date),
        INDEX sys_role_del_flag (del_flag)
    ) DEFAULT CHARSET=utf8 COMMENT='角色表';

INSERT INTO sys_role (id, name, code, create_by, create_date, update_by, update_date, remarks, del_flag) VALUES ('1', '管理员', 'admin', 'wenin819', '2015-07-14 20:54:43', 'wenin819', '2015-07-19 10:14:03', '系统管理员', '0');