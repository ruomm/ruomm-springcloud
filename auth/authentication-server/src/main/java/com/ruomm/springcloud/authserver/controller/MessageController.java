package com.ruomm.springcloud.authserver.controller;

import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.dal.request.MessageSendReq;
import com.ruomm.springcloud.authserver.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(value = "verify-code-send")
    public CommonResponse verifyCodeSend(@Valid @RequestBody MessageSendReq req){
        return messageService.verifyCodeSend(req);
//        return AppUtils.toNackCore();
    }
}
