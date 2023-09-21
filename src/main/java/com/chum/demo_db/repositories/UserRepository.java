package com.chum.demo_db.repositories;

import com.chum.demo_db.domains.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
