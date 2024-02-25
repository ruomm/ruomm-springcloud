package com.ruomm.springcloud.authserver.controller;

import com.ruomm.javax.basex.IPUtils;
import com.ruomm.javax.corex.StringUtils;
import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.dal.request.MessageSendReq;
import com.ruomm.springcloud.authserver.service.MessageService;
import com.ruomm.springcloud.authserver.utils.AppUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/1 20:49
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping(value = "/send/{tpl_key}")
    public CommonResponse send(HttpServletRequest httpServletRequest, @PathVariable String tpl_key, @Valid @RequestBody MessageSendReq req) {
        String clientIp = IPUtils.getRequestIP(httpServletRequest);
        if (StringUtils.isEmpty(clientIp) || clientIp.equalsIgnoreCase("unknown")) {
            return AppUtils.toNackCore("获取客户端信息错误");
        }
        req.setClientIp(clientIp);
        if (StringUtils.isEmpty(tpl_key)) {
            return AppUtils.toNackParam("消息模板不存在，消息发送错误");
        }
        return messageService.send(tpl_key, req);
//        return AppUtils.toNackCore();
    }
}
