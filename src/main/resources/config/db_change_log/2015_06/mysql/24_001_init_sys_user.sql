CREATE TABLE
    sys_user
    (
        id VARCHAR(64) NOT NULL COMMENT '编号',
        login_name VARCHAR(100) NOT NULL COMMENT '登录名',
        password VARCHAR(100) NOT NULL COMMENT '密码',
        no VARCHAR(100) COMMENT '工号',
        name VARCHAR(100) NOT NULL COMMENT '姓名',
        email VARCHAR(200) COMMENT '邮箱',
        phone VARCHAR(200) COMMENT '电话',
        mobile VARCHAR(200) COMMENT '手机',
        login_ip VARCHAR(100) COMMENT '最后登陆IP',
        login_date DATETIME COMMENT '最后登陆时间',
        create_by VARCHAR(64) COMMENT '创建者',
        create_date DATETIME COMMENT '创建时间',
        update_by VARCHAR(64) COMMENT '更新者',
        update_date DATETIME COMMENT '更新时间',
        remarks VARCHAR(255) COMMENT '备注信息',
        del_flag CHAR(1) DEFAULT '0' NOT NULL COMMENT '删除标记',
        PRIMARY KEY (id),
        INDEX sys_user_login_name (login_name),
        INDEX sys_user_del_flag (del_flag)
    )
    DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO sys_user (id, login_name, password, no, name, email, phone, mobile, login_ip, login_date, create_by, create_date, update_by, update_date, remarks, del_flag) VALUES ('1', 'wenin819', 'iGUP3hXhDS6oWsGvF74cfowbX4PCFsLXCGMVx/b2PLgg3LXUbLjU5Bwfp2lmHa9hHLrHLqE9V/H8SFFCLFfE0Q==', '0707011003', '李超', 'wenin819@gmail.com', null, null, null, null, '1', null, '1', null, '超级管理员', '0');
