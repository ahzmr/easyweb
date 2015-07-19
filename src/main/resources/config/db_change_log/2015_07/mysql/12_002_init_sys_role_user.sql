CREATE TABLE
    sys_role_user
    (
        role_id VARCHAR(64) NOT NULL COMMENT '角色编号',
        user_id VARCHAR(64) NOT NULL COMMENT '用户编号',
        create_by VARCHAR(64) COMMENT '创建者',
        create_date DATETIME COMMENT '创建时间',
        PRIMARY KEY (role_id, user_id)
    ) DEFAULT CHARSET=utf8 COMMENT='角色用户关系表';

INSERT INTO sys_role_user (role_id, user_id, create_by, create_date) VALUES ('1', '1', 'wenin819', '2015-07-18 19:40:46');