package com.extensionrepository.repository.base;


import com.extensionrepository.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User getById(int id);

    User findByUsername(String username);

    User save(User user);

    //void changeStatus(int id);

    boolean isExistUsername(String username);

    void update(User user);
}
