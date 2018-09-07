package com.extensionrepository.service.base;

import com.extensionrepository.entity.User;

import java.util.List;

public interface UserService extends FieldValueExists {
    List<User> getAll();
    List<User> getAllNonAdminUsers();
    User findByUsername(String username);
    boolean isExistUsername(String username);
    boolean register(User user);
    void changeStatus(int id);
}
