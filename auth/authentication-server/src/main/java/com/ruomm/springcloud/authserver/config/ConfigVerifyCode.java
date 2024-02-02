package com.ruomm.springcloud.authserver.config;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/2 22:02
 */
public class ConfigVerifyCode {
    // # 验证码所属应用名称
    private String appName;
    // # 验证码有效期
    private String validTime;
    // # 验证码再次发送间隔
    private String validSkip;
    // # 同一设备，验证码一天发送的次数限制
    private int limitByTerm;
    // # 同一用户，验证码一天发送的次数限制
    private int limitByUser;
    // # 同一发送目标，验证码一天发送的次数限制
    private int limitByToAddress;
}
