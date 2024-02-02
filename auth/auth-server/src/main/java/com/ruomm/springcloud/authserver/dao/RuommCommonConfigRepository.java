package com.ruomm.springcloud.authserver.dao;

import com.ruomm.springcloud.authserver.entry.RuommCommonConfig;
import com.ruomm.springcloud.authserver.entry.RuommCommonConfigId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuommCommonConfigRepository extends JpaRepository<RuommCommonConfig, RuommCommonConfigId> {
}