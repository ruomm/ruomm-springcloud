package com.ruomm.springcloud.authserver.controller;

import com.ruomm.springcloud.authserver.dal.CommonResponse;
import com.ruomm.springcloud.authserver.dal.request.UserCreateReq;
import com.ruomm.springcloud.authserver.dal.response.UserCreateResp;
import com.ruomm.springcloud.authserver.service.UserManagerService;
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

    @PostMapping(value = "create")
    public CommonResponse<UserCreateResp> createUser(@Valid @RequestBody UserCreateReq req) {

        return userManagerService.createUser(req);
    }
}
