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

 Date: 02/02/2024 08:43:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_message
-- ----------------------------
CREATE TABLE `tbl_msg_content` (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `alias_id` varchar(64) NOT NULL COMMENT '主键别名。需要返回短信ID时候，建议返回别名ID',
  `tpl_key` varchar(64) NOT NULL COMMENT '短信模板主键KEY',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `msg_type` varchar(16) NOT NULL COMMENT '信息类型，mobile、email、weixin、qq等',
  `msg_addr` varchar(64) NOT NULL COMMENT '信息送达地址，没有user_id时候必须填写',
  `msg_bind_addr` varchar(64) NULL DEFAULT NULL COMMENT '信息关联目标地址。如修改手机号，填写待修改的手机号',
  `msg_content` varchar(1024) NOT NULL COMMENT '信息内容',
  `verify_code` varchar(16) NULL DEFAULT NULL COMMENT '验证码',
  `valid_time` int(0) NULL DEFAULT NULL COMMENT '验证码有效期(单位秒)',
  `check_count` int(0) NOT NULL COMMENT '验证码已核验次数，超过一定核验次数验证码失效',
  `status` int(0) NOT NULL COMMENT '状态。1.发送成功；2.验证成功；3.验证失败；9.失效',
  `client_id` varchar(64) NULL DEFAULT NULL COMMENT '客户端编号',
  `client_host` varchar(64) NULL DEFAULT NULL COMMENT '客户端host地址',
  `client_ip` varchar(64) NULL DEFAULT NULL COMMENT '客户端IP地址',
  `version` int(0) NOT NULL COMMENT '乐观锁',
  `created_at` timestamp(6) NOT NULL COMMENT '创建时间',
  `updated_at` timestamp(6) NOT NULL COMMENT '更新时间',
  `deleted_at` timestamp(6) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT '信息内容表';
