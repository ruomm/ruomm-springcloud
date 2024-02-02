package com.ruomm.springcloud.authserver.service;

import com.ruomm.springcloud.authserver.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/2/2 0:54
 */
@Service
public class MessageService {
    @Autowired
    UserMapper userMapper;
}
