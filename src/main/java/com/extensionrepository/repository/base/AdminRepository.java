package com.extensionrepository.repository.base;

import com.extensionrepository.entity.Extension;

public interface AdminRepository {

    void enableUser(String name);

    void disableUser(String name);

    void approveExtension(int id);

    void editExtension(int id);

    void deleteExtension(Extension extension);
}
