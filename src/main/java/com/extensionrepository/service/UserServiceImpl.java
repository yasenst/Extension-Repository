package com.extensionrepository.service;

import com.extensionrepository.entity.User;
import com.extensionrepository.repositories.base.UserRepository;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean registerUser(User user) {
        return userRepository.registerUser(user);
    }
}
