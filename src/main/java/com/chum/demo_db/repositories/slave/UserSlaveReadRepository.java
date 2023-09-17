package com.chum.demo_db.repositories.slave;

import com.chum.demo_db.domains.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSlaveReadRepository extends JpaRepository<UserEntity, Long> {
}
