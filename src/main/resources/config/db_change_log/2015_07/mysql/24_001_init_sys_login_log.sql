CREATE TABLE
    sys_login_log
    (
        id VARCHAR(64) NOT NULL COMMENT '编号',
        login_ip VARCHAR(64) COMMENT '登录IP',
        login_location VARCHAR(255) COMMENT '登录地点',
        user_agent VARCHAR(2047) COMMENT '用户代理标识',
        os_name VARCHAR(255) COMMENT '操作系统',
        os_version VARCHAR(255) COMMENT '操作系统版本',
        device_name VARCHAR(255) COMMENT '设备名称',
        device_type VARCHAR(255) COMMENT '设备类型',
        app_name VARCHAR(255) COMMENT '应用名称',
        app_type VARCHAR(255) COMMENT '应用类型',
        app_version VARCHAR(255) COMMENT '应用版本',
        remarks VARCHAR(255) COMMENT '备注信息',
        create_by VARCHAR(64) NOT NULL COMMENT '创建者',
        create_date DATETIME NOT NULL COMMENT '创建时间',
        PRIMARY KEY (id),
        INDEX sys_login_log_create_by (create_by),
        INDEX sys_login_log_create_date (create_date)
    )
    DEFAULT CHARSET=utf8 COMMENT='登录日志表';
