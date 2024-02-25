package com.ruomm.springcloud.authserver.controller;

import com.ruomm.javax.basex.IPUtils;
import com.ruomm.javax.corex.StringUtils;
import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.dal.request.UserCreateReq;
import com.ruomm.springcloud.authserver.dal.request.UserLoginReq;
import com.ruomm.springcloud.authserver.dal.response.UserCreateResp;
import com.ruomm.springcloud.authserver.dal.response.UserLoginResp;
import com.ruomm.springcloud.authserver.service.MessageService;
import com.ruomm.springcloud.authserver.service.UserManagerService;
import com.ruomm.springcloud.authserver.utils.AppUtils;
import jakarta.servlet.http.HttpServletRequest;
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
 * @create 2024/1/30 0:16
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private MessageService messageService;

    @PostMapping(value = "create")
    public CommonResponse<UserCreateResp> createUser(HttpServletRequest httpServletRequest, @Valid @RequestBody UserCreateReq req) {
        String clientIp = IPUtils.getRequestIP(httpServletRequest);
        if (StringUtils.isEmpty(clientIp) || clientIp.equalsIgnoreCase("unknown")) {
            return AppUtils.toNackCore("获取客户端信息错误");
        }
        messageService.valid("user_register","mobile",req.getBindPhone(),null,req.getVerifyCode(),null,clientIp,null);
        return userManagerService.createUser(req);
    }

    @PostMapping(value = "login")
    public CommonResponse<UserLoginResp> loginUser(HttpServletRequest httpServletRequest, @Valid @RequestBody UserLoginReq req) {
        String clientIp = IPUtils.getRequestIP(httpServletRequest);
        if (StringUtils.isEmpty(clientIp) || clientIp.equalsIgnoreCase("unknown")) {
            return AppUtils.toNackCore("获取客户端信息错误");
        }
        return userManagerService.loginUser(req);
    }
}
