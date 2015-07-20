CREATE TABLE
    sys_dict
    (
        id VARCHAR(64) NOT NULL COMMENT '编号',
        value VARCHAR(100) NOT NULL COMMENT '数据值',
        label VARCHAR(100) NOT NULL COMMENT '标签名',
        type VARCHAR(100) NOT NULL COMMENT '类型',
        description VARCHAR(100) NOT NULL COMMENT '描述',
        sort DECIMAL NOT NULL COMMENT '排序（升序）',
        remarks VARCHAR(255) COMMENT '备注信息',
        create_by VARCHAR(64) NOT NULL COMMENT '创建者',
        create_date DATETIME NOT NULL COMMENT '创建时间',
        update_by VARCHAR(64) NOT NULL COMMENT '更新者',
        update_date DATETIME NOT NULL COMMENT '更新时间',
        del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
        PRIMARY KEY (id),
        INDEX sys_dict_value (value),
        INDEX sys_dict_label (label),
        INDEX sys_dict_type (type),
        INDEX sys_dict_del_flag (del_flag)
    )
    DEFAULT CHARSET=utf8 COMMENT='字典表';

INSERT INTO sys_dict (id, value, label, type, description, sort, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('1', '1', '显示', 'sys_menu_is_show', '菜单是否显示', 10, null, 'wenin819', '2015-07-20 20:22:50', 'wenin819', '2015-07-20 20:22:49', '0');
INSERT INTO sys_dict (id, value, label, type, description, sort, remarks, create_by, create_date, update_by, update_date, del_flag) VALUES ('2', '0', '不显示', 'sys_menu_is_show', '菜单是否显示', 20, null, 'wenin819', '2015-07-20 20:27:04', 'wenin819', '2015-07-20 20:27:04', '0');
