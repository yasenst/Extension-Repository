package com.extensionrepository.service.base;

import com.extensionrepository.entity.Role;

public interface RoleService {

    Role findByName(String name);
}
