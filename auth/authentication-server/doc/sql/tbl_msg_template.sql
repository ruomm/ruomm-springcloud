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
    `tpl_key` varchar(64) NOT NULL COMMENT '短信模板主键KEY',
    `tpl_name` varchar(64) NOT NULL COMMENT '短信模板名称',
    `bind_tpl_key` varchar(64) DEFAULT NULL COMMENT '关联短信模板主键KEY。如是有关联主键KEY，则短信发送前需要检查前一条关联短信是否验证通过。',
    `template` varchar(1024) NOT NULL COMMENT '短信模板内容。按照短信模板填充短信内容，通用替换字段：${verifycode},${validtime},${appname},${year},${month},${day},${time},${datetime},${userId},${userName}',
    `auth_type` int NOT NULL COMMENT '短信发送授权控制。0.不需要授权；1.需要授权；2.需要授权并且是账户绑定的通讯方式；3.需要校验账户；4.需要校验账户并且是账户绑定的通讯方式',
    `user_status` int NOT NULL COMMENT '发送短信时候的用户状态。1.正常；2.密码过期；3.登录限制；9.停用',
    `auth_uri` varchar(64) DEFAULT NULL COMMENT '短信授权URI。URI为空则是通用短信，不为空则是特定URI用途的短信。',
    `valid_time` int DEFAULT NULL COMMENT '验证码有效期(单位秒)',
    `repeat_skip_time` int DEFAULT NULL COMMENT '再次发送间隔(单位秒)',
    `limit_by_term` int DEFAULT NULL COMMENT '同一设备，一天发送的次数限制',
    `limit_by_user` int DEFAULT NULL COMMENT '同一用户，一天发送的次数限制',
    `limit_by_addr` int DEFAULT NULL COMMENT '同一发送目标，一天发送的次数限制',
    `status` int NOT NULL COMMENT '状态。0.停用；1.启用',
    `version` int NOT NULL COMMENT '乐观锁',
    `created_at` timestamp(6) NOT NULL COMMENT '创建时间',
    `updated_at` timestamp(6) NOT NULL COMMENT '更新时间',
    `deleted_at` timestamp(6) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`tpl_key`) USING BTREE
) ENGINE = InnoDB COMMENT = '信息模板表';
