package com.extensionrepository.repository.base;

import com.extensionrepository.entity.Role;

public interface RoleRepository {
    Role findByName(String name);
}
