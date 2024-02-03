package com.ruomm.springcloud.authserver.dal.request.sub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/2 23:42
 */
@Getter
@Setter
@ToString
public class ReqKeyValuePair {
    private String key;
    private String value;
}
