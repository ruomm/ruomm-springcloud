package com.ruomm.springcloud.authserver.dao;

import com.ruomm.springcloud.authserver.entry.UserEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 牛牛-研发部-www.ruomm.com
 * @version 1.0
 * @copyright 像衍科技-idr.ai
 * @create 2024/1/30 0:16
 */
@Repository
public interface UserMapper extends Mapper<UserEntity> {
}
