package com.extensionrepository.service;

import com.extensionrepository.entity.User;
import com.extensionrepository.repository.base.UserRepository;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isExistUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        return true;
    }

    @Override
    public boolean register(User user) {
        if (userRepository.save(user) == null) {
            return false;
        }
        return true;
    }

    @Override
    public void changeStatus(int id) {
        User user = userRepository.getById(id);

        if (user.isEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }

        userRepository.update(user);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        Assert.notNull(fieldName, "Username already exists");

        if (!fieldName.equals("username")) {
            throw new UnsupportedOperationException("Field name not supported");
        }

        if (value == null) {
            return false;
        }

        return isExistUsername(value.toString());
    }
}