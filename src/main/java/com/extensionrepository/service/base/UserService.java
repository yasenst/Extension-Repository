package com.extensionrepository.service.base;

import com.extensionrepository.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User findByUsername(String username);

    boolean registerUser(User user);
}
