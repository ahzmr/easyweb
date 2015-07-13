CREATE TABLE
    table_name
    (
        id VARCHAR(64) NOT NULL COMMENT '编号',
        name VARCHAR(100) NOT NULL COMMENT '名称',
        create_by VARCHAR(64) COMMENT '创建者',
        create_date DATETIME COMMENT '创建时间',
        update_by VARCHAR(64) COMMENT '更新者',
        update_date DATETIME COMMENT '更新时间',
        remarks VARCHAR(255) COMMENT '备注信息',
        del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
        PRIMARY KEY (id),
        INDEX table_name_update_date (update_date),
        INDEX table_name_del_flag (del_flag)
    ) DEFAULT CHARSET=utf8 COMMENT='表名';