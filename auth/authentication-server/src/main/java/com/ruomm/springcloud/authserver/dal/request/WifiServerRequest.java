package com.ruomm.springcloud.authserver.dal.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/11 15:49
 */
@Getter
@Setter
@ToString
public class WifiServerRequest {
    /**
     * 合作者机构号
     */
    private String partnerId;
    /**
     * 合作者IP编号
     */
    private String wifiIp;
    /**
     * 是否调试环境
     */
    private Boolean debug;

    /**
     * App版本，App后缀有"dev"、"beta"、"alpha"视为开发版本
     */
    private String appVersion;

    /**
     * 请求的deviceId
     */
    private String deviceId;

    /**
     * 通过广播获取到的serverUrl
     */
    private String udpServerUrl;
}
