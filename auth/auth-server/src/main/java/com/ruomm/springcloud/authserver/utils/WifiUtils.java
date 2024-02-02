package com.ruomm.springcloud.authserver.utils;

import com.ruomm.springcloud.authserver.dal.request.WifiServerRequest;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/5/9 13:53
 */
public class WifiUtils {
    public static String parseKey(String partnerId) {
        return "serverConfig" + "_" + partnerId;
    }

    public static String parseEnvResponse(WifiServerRequest wifiServerRequest) {
        if (null != wifiServerRequest.getDebug() && wifiServerRequest.getDebug().booleanValue()) {
            return wifiServerRequest.getPartnerId() + "_test";
        } else {
            return wifiServerRequest.getPartnerId() + "_prod";
        }
    }

    public static String parseEnvDbKey(WifiServerRequest wifiServerRequest) {
        if (null != wifiServerRequest.getDebug() && wifiServerRequest.getDebug().booleanValue()) {
            String versionLower = null == wifiServerRequest.getAppVersion() ? "" : wifiServerRequest.getAppVersion();
            if (versionLower.endsWith("dev") || versionLower.endsWith("beta") || versionLower.endsWith("alpha")) {
                return wifiServerRequest.getPartnerId() + "_dev";
            } else {
                return wifiServerRequest.getPartnerId() + "_test";
            }
        } else {
            return wifiServerRequest.getPartnerId() + "_prod";
        }
    }
}
