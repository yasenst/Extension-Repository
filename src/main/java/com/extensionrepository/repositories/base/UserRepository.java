package com.extensionrepository.repositories.base;


import com.extensionrepository.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User getById(int id);

    User findByUsername(String username);

    boolean registerUser(User user);

    //void changeStatus(int id);

    boolean isExistUsername(String username);

    void update(User user);
}
