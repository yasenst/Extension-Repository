package com.extensionrepository.dto;

import com.extensionrepository.service.UserServiceImpl;
import com.extensionrepository.validation.IsPasswordMatching;
import com.extensionrepository.validation.IsFieldUnique;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@IsPasswordMatching
public class UserDto {
    @NotEmpty
    @Size(min = 3, message = "Username must be at least 3 characters")
    @IsFieldUnique(service = UserServiceImpl.class, fieldName = "username", message = "Username already exists")
    private String username;

    @NotEmpty
    private String fullName;

    @NotEmpty
    @Size(min = 5, message = "Password must be at least 5 characters")
    private String password;

    @NotEmpty
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username;
    }
}