package com.chum.demo_db.repositories.master;

import com.chum.demo_db.domains.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterWriteRepository extends JpaRepository<UserEntity, Long> {
}
