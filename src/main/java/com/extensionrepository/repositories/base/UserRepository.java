package com.extensionrepository.repositories.base;


import com.extensionrepository.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User findByUsername(String username);

    boolean registerUser(User u);
}
