package com.ruomm.springcloud.authserver.controller;

import com.ruomm.javax.corex.StringUtils;
import com.ruomm.javax.encryptx.CRC32Util;
import com.ruomm.springcloud.authserver.dal.request.CommonConfigRequest;
import com.ruomm.springcloud.authserver.dal.request.WifiServerRequest;
import com.ruomm.springcloud.authserver.dal.response.CommonConfigResponse;
import com.ruomm.springcloud.authserver.dal.response.WifiServerResponse;
import com.ruomm.springcloud.authserver.dao.RuommCommonConfigRepository;
import com.ruomm.springcloud.authserver.entry.RuommCommonConfig;
import com.ruomm.springcloud.authserver.entry.RuommCommonConfigId;
import com.ruomm.springcloud.authserver.utils.AppHttpUtils;
import com.ruomm.springcloud.authserver.utils.WifiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/11 15:51
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private RuommCommonConfigRepository ruommCommonConfigRepository;

    @PostMapping(value = "getWifiServer")
    public WifiServerResponse postGetWifiServer(@RequestBody WifiServerRequest wifiServerRequest) {
        if (null == wifiServerRequest || StringUtils.isEmpty(wifiServerRequest.getPartnerId()) || StringUtils.isEmpty(wifiServerRequest.getWifiIp()) || null == wifiServerRequest.getDebug()) {
            return AppHttpUtils.toNackCore(WifiServerResponse.class, "请求参数错误");
        }
        if (wifiServerRequest.getPartnerId().contains("_")) {
            return AppHttpUtils.toNackCore(WifiServerResponse.class, "partnerId合作者机构号不能含有下划线(_)");
        }
        String configKey = WifiUtils.parseKey(wifiServerRequest.getPartnerId());
        String configEnv = WifiUtils.parseEnvResponse(wifiServerRequest);
        if (StringUtils.isEmpty(configEnv)) {
            return AppHttpUtils.toNackCore(WifiServerResponse.class, "env环境变量解析失败");
        }
        String responseDeviceId = null;
        if (StringUtils.isNotBlank(wifiServerRequest.getDeviceId())) {
            String configEnvCRC = CRC32Util.encodingString(configEnv).toLowerCase();
            if (StringUtils.isEmpty(configEnvCRC)) {
                return AppHttpUtils.toNackCore(WifiServerResponse.class, "env环境变量CRC32运算失败");
            }
            responseDeviceId = wifiServerRequest.getDeviceId() + configEnvCRC;
        }
        String serverUrl;
        if (StringUtils.isEmpty(wifiServerRequest.getUdpServerUrl())) {
            RuommCommonConfigId queryObj = new RuommCommonConfigId();
            // queryObj.setConfigId(new RuommCommonConfigId());
            queryObj.setConfigKey(configKey);
            queryObj.setConfigEnv(WifiUtils.parseEnvDbKey(wifiServerRequest));
            Optional<RuommCommonConfig> resultObj = ruommCommonConfigRepository.findById(queryObj);
            if (null == resultObj || null == resultObj.get().getConfigStatus() || 1 != resultObj.get().getConfigStatus() || StringUtils.isEmpty(resultObj.get().getConfigVal())) {
                return AppHttpUtils.toNackCore(WifiServerResponse.class, "TCP方式无法获取服务器地址，等待UDP方式方式获取服务器地址");
            }
            serverUrl = resultObj.get().getConfigVal();
        } else {
            serverUrl = wifiServerRequest.getUdpServerUrl();
        }
        WifiServerResponse mResponse = new WifiServerResponse();

        String hostPortString = null;
        if (serverUrl.toLowerCase().startsWith("http://")) {
            String tmp = serverUrl.substring("http://".length());
            int index = tmp.indexOf("/");
            if (index >= 0) {
                hostPortString = tmp.substring(0, index);
            } else {
                hostPortString = tmp;
            }
        } else if (serverUrl.toLowerCase().startsWith("https://")) {
            String tmp = serverUrl.substring("https://".length());
            int index = tmp.indexOf("/");
            if (index >= 0) {
                hostPortString = tmp.substring(0, index);
            } else {
                hostPortString = tmp;
            }
        }
        if (StringUtils.isEmpty(hostPortString)) {
            return AppHttpUtils.toNackCore(WifiServerResponse.class, "TCP方式获取服务器地址不正确，等待UDP方式方式获取服务器地址");
        }
        String[] hostports = hostPortString.split(":");
        if (null == hostports || hostports.length < 1 || hostports.length > 2 || StringUtils.isEmpty(hostports[0])) {
            return AppHttpUtils.toNackCore(WifiServerResponse.class, "TCP方式获取服务器地址不正确，等待UDP方式方式获取服务器地址");
        }
        String host = hostports[0];
        int port = 80;
        if (hostports.length == 2 && !StringUtils.isEmpty(hostports[1])) {
            port = Integer.parseInt(hostports[1]);
        }
        mResponse.setServerUrl(serverUrl);
        mResponse.setHost(host);
        mResponse.setPort(port);
        mResponse.setEnv(configEnv);
        mResponse.setDeviceId(responseDeviceId);
        AppHttpUtils.toAck(mResponse);
        return mResponse;
    }

    @PostMapping(value = "postConfigVal")
    public CommonConfigResponse postConfigVal(@RequestBody CommonConfigRequest commonConfigRequest) {
        if (null == commonConfigRequest || StringUtils.isEmpty(commonConfigRequest.getKey()) || StringUtils.isEmpty(commonConfigRequest.getEnv())) {
            return AppHttpUtils.toNackCore(CommonConfigResponse.class, "请求参数错误");
        }
        RuommCommonConfigId queryObj = new RuommCommonConfigId();
        // queryObj.setConfigId(new RuommCommonConfigId());
        queryObj.setConfigKey(commonConfigRequest.getKey());
        queryObj.setConfigEnv(commonConfigRequest.getEnv());
        Optional<RuommCommonConfig> resultObj = ruommCommonConfigRepository.findById(queryObj);
        if (null == resultObj || null == resultObj.get().getConfigStatus() || 1 != resultObj.get().getConfigStatus() || StringUtils.isEmpty(resultObj.get().getConfigVal())) {
            return AppHttpUtils.toNackCore(CommonConfigResponse.class, "无法找到服务器地址");
        }
        CommonConfigResponse mResponse = new CommonConfigResponse();
        mResponse.setVal(resultObj.get().getConfigVal());
        AppHttpUtils.toAck(mResponse);
        return mResponse;
    }

    @GetMapping(value = "getConfigVal/{key}/{env}")
    public CommonConfigResponse getConfigVal(@PathVariable String key, @PathVariable String env) {
        CommonConfigRequest commonConfigRequest = new CommonConfigRequest();
        commonConfigRequest.setKey(key);
        commonConfigRequest.setEnv(env);
        if (null == commonConfigRequest || StringUtils.isEmpty(commonConfigRequest.getKey()) || StringUtils.isEmpty(commonConfigRequest.getEnv())) {
            return AppHttpUtils.toNackCore(CommonConfigResponse.class, "请求参数错误");
        }
        RuommCommonConfigId queryObj = new RuommCommonConfigId();
        // queryObj.setConfigId(new RuommCommonConfigId());
        queryObj.setConfigKey(commonConfigRequest.getKey());
        queryObj.setConfigEnv(commonConfigRequest.getEnv());
        Optional<RuommCommonConfig> resultObj = ruommCommonConfigRepository.findById(queryObj);
        if (null == resultObj || null == resultObj.get().getConfigStatus() || 1 != resultObj.get().getConfigStatus() || StringUtils.isEmpty(resultObj.get().getConfigVal())) {
            return AppHttpUtils.toNackCore(CommonConfigResponse.class, "无法找到服务器地址");
        }
        CommonConfigResponse mResponse = new CommonConfigResponse();
        mResponse.setVal(resultObj.get().getConfigVal());
        AppHttpUtils.toAck(mResponse);
        return mResponse;
    }

}
