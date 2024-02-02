package com.ruomm.springcloud.authserver.dal.request;

import com.ruomm.springcloud.authserver.dal.CommonRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/11 16:23
 */
@Getter
@Setter
@ToString
public class CommonConfigRequest extends CommonRequest {
    private String key;
    private String env;
}
