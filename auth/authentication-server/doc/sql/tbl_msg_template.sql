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
CREATE TABLE `tbl_msg_template`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `bind_id` varchar(64) NULL DEFAULT NULL COMMENT '关联主键ID。如是有关联主键ID，则短信发送前需要检查前一条关联短信是否验证通过。',
    `template` varchar(1024) NOT NULL COMMENT '短信模板。按照短信模板填充短信内容，通用替换字段：${verifycode},${year},${month},${day},${time},${datetime},${userId},${userName}',
    `auth_type` int(0) NOT NULL COMMENT '短信发送授权控制。0.不需要授权；1.需要授权；2.可授权可不授权',
    `auth_uri` varchar(64) NULL DEFAULT NULL COMMENT '短信授权URI。URI为空则是通用短信，不为空则是特定URI用途的短信。',
    `valid_time` int(0) NULL DEFAULT NULL COMMENT '验证码有效期，单位秒',
    `status` int(0) NOT NULL COMMENT '状态。0.停用；1.启用',
    `version` int(0) NOT NULL COMMENT '乐观锁',
    `created_at` timestamp(6) NOT NULL COMMENT '创建时间',
    `updated_at` timestamp(6) NOT NULL COMMENT '更新时间',
    `deleted_at` timestamp(6) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '信息模板表';
