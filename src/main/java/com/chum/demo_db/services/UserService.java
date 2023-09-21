package com.chum.demo_db.services;

import com.chum.demo_db.domains.dtos.UserDto;
import com.chum.demo_db.domains.entities.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    UserEntity createOrUpdateUser(UserDto userDto);

    List<UserEntity> getAllUsers();
}

