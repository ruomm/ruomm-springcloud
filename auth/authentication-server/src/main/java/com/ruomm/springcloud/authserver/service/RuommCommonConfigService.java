package com.ruomm.springcloud.authserver.service;

import com.ruomm.springcloud.authserver.dao.RuommCommonConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright wanruome@163.com
 * @create 2022/4/11 14:47
 */
@Service
public class RuommCommonConfigService {
    @Autowired
    private RuommCommonConfigMapper ruommCommonConfigMapper;
}
