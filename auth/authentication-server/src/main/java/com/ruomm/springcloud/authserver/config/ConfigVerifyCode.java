package com.ruomm.springcloud.authserver.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/2 22:02
 */
@Getter
@Setter
@ToString
public class ConfigVerifyCode {
    // # 验证码所属应用名称
    private String appName;
    // # 验证码有效期(单位秒)
    private String validTime;
    // # 再次发送间隔(单位秒)
    private int repeatSkipTime;
    // # 同一设备，一天发送的次数限制
    private int limitByTerm;
    // # 同一用户，一天发送的次数限制
    private int limitByUser;
    // # 同一发送目标，一天发送的次数限制
    private int limitByAddr;
}
