-- 建表语句
-- 执行方式：mysql -uptms -pptms123456 ptms < db/schema.sql

-- 用户表
CREATE TABLE IF NOT EXISTS t_user (
    id         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    username   VARCHAR(50)  NOT NULL COMMENT '用户名',
    password   VARCHAR(100) NOT NULL COMMENT '密码，存的是 BCrypt 哈希串，绝不存明文',
    nickname   VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)      -- 唯一约束：数据库层面兜底，防重名
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- 任务表
CREATE TABLE IF NOT EXISTS t_task (
    id         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id    BIGINT       NOT NULL COMMENT '属于哪个用户',
    title      VARCHAR(100) NOT NULL COMMENT '标题',
    content    VARCHAR(500) DEFAULT NULL COMMENT '详情',
    status     TINYINT      NOT NULL DEFAULT 0 COMMENT '0=未完成 1=已完成',
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id)              -- 普通索引：查"我的任务"天天用
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='任务表';
