package com.ruomm.springcloud.authserver.dao;

import com.ruomm.springcloud.authserver.entry.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}