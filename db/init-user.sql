
-- 建库 + 建一个给程序用的专用账号
-- 执行方式：sudo mysql < db/init-user.sql
--
-- 为什么不直接用 root 跑程序？
-- 因为 root 权限太大，程序一旦被攻破（比如 SQL 注入）就是整台数据库完了。
-- 真实项目里一律是"一个应用一个专用账号，只给需要的库的权限"。

CREATE DATABASE IF NOT EXISTS ptms
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

-- 密码必须有大小写 + 数字 + 特殊符号，否则会被密码强度插件（validate_password）拒掉
CREATE USER IF NOT EXISTS 'ptms'@'localhost' IDENTIFIED BY 'Ptms@2026';
GRANT ALL PRIVILEGES ON ptms.* TO 'ptms'@'localhost';
FLUSH PRIVILEGES;
