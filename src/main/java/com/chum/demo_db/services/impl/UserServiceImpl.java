package com.chum.demo_db.services.impl;

import com.chum.demo_db.domains.dtos.UserDto;
import com.chum.demo_db.domains.entities.UserEntity;
import com.chum.demo_db.repositories.UserRepository;
import com.chum.demo_db.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public UserEntity createOrUpdateUser(UserDto userDto) {
        UserEntity userSave = new UserEntity();
        userSave.setUsername(userDto.getUsername());
        userSave.setEmail(userDto.getEmail());
        return userRepository.save(userSave);
    }

    @Override
    @Transactional(transactionManager = "slaveTransactionManager", readOnly = true)
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
