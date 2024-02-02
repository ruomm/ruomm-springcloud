package com.ruomm.springcloud.authserver.dal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/1/29 22:00
 */
@Getter
@Setter
@ToString
public class CommonFieldError {
    private String field;
    private String message;
}
