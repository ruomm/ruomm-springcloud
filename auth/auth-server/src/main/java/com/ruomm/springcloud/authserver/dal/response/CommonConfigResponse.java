package com.ruomm.springcloud.authserver.dal.response;

import com.ruomm.springcloud.authserver.dal.CommonResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/11 16:24
 */
@Getter
@Setter
@ToString(callSuper = true)
public class CommonConfigResponse extends CommonResponse {
    private String val;
}
