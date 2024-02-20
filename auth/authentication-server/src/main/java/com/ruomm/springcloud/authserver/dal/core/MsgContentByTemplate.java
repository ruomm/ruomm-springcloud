package com.ruomm.springcloud.authserver.dal.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 牛牛-wanruome@126.com
 * @version 1.0
 * @copyright www.ruomm.com
 * @create 2024/2/20 11:48
 */
@Getter
@Setter
@ToString
public class MsgContentByTemplate {
    private String content;
    private String verifyCode;
    private Integer validTime;
    private String validTimeStr;
}
