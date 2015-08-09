ALTER TABLE
    easyweb.sys_login_log ADD (session_id VARCHAR(64) COMMENT '会话编号');
ALTER TABLE
    easyweb.sys_login_log ADD (logout_date DATETIME COMMENT '退出时间');
ALTER TABLE
    easyweb.sys_login_log ADD INDEX sys_login_log_session_id (session_id)