package com.extensionrepository.repositories.base;


import com.extensionrepository.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository {
    User findByUsername(String username);
}
