CREATE DATABASE IF NOT EXISTS `clamav_guard` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `clamav_guard`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '项目描述',
  `user_id` bigint(20) NOT NULL COMMENT '关联用户ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目表';

DROP TABLE IF EXISTS `api_key`;

CREATE TABLE `api_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `api_key` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'API密钥',
  `user_id` bigint(20) NOT NULL COMMENT '关联用户ID',
  `status` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Active' COMMENT '状态 (Active/Revoked)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_api_key` (`api_key`),
  KEY `idx_user_id_api_key` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='API密钥表';

DROP TABLE IF EXISTS `scan_history`;

CREATE TABLE `scan_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `file_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件ID',
  `filename` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名',
  `status` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '扫描状态 (Clean/Infected)',
  `virus_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '病毒名称',
  `user_id` bigint(20) NOT NULL COMMENT '关联用户ID',
  `scanned_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '扫描时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_scan` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='扫描历史表';

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`) VALUES
(1, 'admin', '{bcrypt}$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');

--
-- Dumping data for table `api_key`
--

INSERT INTO `api_key` (`id`, `api_key`, `user_id`, `status`) VALUES
(1, 'd8f8a7-a8d9f-a9f8a-d8f8a7', 1, 'Active'),
(2, 'a9f8a-d8f8a7-a8d9f-a9f8a', 1, 'Active');

--
-- Dumping data for table `scan_history`
--

INSERT INTO `scan_history` (`id`, `file_id`, `filename`, `status`, `virus_name`, `user_id`) VALUES
(1, '1', 'file1.txt', 'Clean', NULL, 1),
(2, '2', 'file2.txt', 'Infected', 'Win.Test.EICAR_HDB-1', 1),
(3, '3', 'file3.txt', 'Clean', NULL, 1);