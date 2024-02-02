package com.ruomm.springcloud.authserver.dao;

import com.ruomm.springcloud.authserver.entry.RuommCommonConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RuommCommonConfigMapper extends Mapper<RuommCommonConfig> {
}