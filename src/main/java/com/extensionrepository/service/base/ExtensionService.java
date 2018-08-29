package com.extensionrepository.service.base;

import com.extensionrepository.entity.Extension;

import java.util.List;

public interface ExtensionService {
    List<Extension> getAll();
    boolean save(Extension extension);
    boolean exists(int id);
    Extension getById(int id);
    void update(Extension extension);
    void delete(Extension extension);
    List<Extension> getPending();

}
