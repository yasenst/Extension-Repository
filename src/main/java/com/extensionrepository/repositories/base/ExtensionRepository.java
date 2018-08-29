package com.extensionrepository.repositories.base;

import com.extensionrepository.entity.Extension;

import java.util.List;

public interface ExtensionRepository {
    List<Extension> getAll();
    List<Extension> getPending();
    List<Extension> searchByName(String name);
    Extension getById(int id);
    boolean exists(int id);
    boolean save(Extension extension);
    void update(Extension extension);
    void delete(Extension extension);

}
