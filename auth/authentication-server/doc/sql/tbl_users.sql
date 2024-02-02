/*
 Navicat MySQL Data Transfer

 Source Server         : mysql_localhost
 Source Server Type    : MySQL
 Source Server Version : 80300
 Source Host           : 127.0.0.1:3306
 Source Schema         : ruommdb

 Target Server Type    : MySQL
 Target Server Version : 80300
 File Encoding         : 65001

 Date: 02/02/2024 08:43:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_users
-- ----------------------------
CREATE TABLE `tbl_users` (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(32) NULL DEFAULT NULL COMMENT '用户名称',
  `nick_name` varchar(32) NULL DEFAULT NULL COMMENT '用户昵称',
  `user_type` int(0) NOT NULL COMMENT '用户类型',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `password_slat` varchar(32) NOT NULL COMMENT '密码BE盐值',
  `status` int(0) NOT NULL COMMENT '用户状态。1.正常；2.密码过期；3.登录限制；9.停用',
  `bind_weixin` varchar(64) NULL DEFAULT NULL COMMENT '绑定的微信号',
  `bind_qq` varchar(16) NULL DEFAULT NULL COMMENT '绑定的QQ号',
  `bind_phone` varchar(20) NULL DEFAULT NULL COMMENT '绑定的手机号',
  `bind_email` varchar(64) NULL DEFAULT NULL COMMENT '绑定的Email',
  `contact_phone` varchar(20) NULL DEFAULT NULL COMMENT '联系手机号',
  `contact_email` varchar(64) NULL DEFAULT NULL COMMENT '联系Email',
  `version` int(0) NOT NULL COMMENT '乐观锁',
  `created_at` timestamp(6) NOT NULL COMMENT '创建时间',
  `updated_at` timestamp(6) NOT NULL COMMENT '更新时间',
  `deleted_at` timestamp(6) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '用户表';
