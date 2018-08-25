package com.extensionrepository.service.base;

import com.extensionrepository.entity.User;

public interface UserService {

    User findByUsername(String username);

    boolean registerUser(User user);
}
