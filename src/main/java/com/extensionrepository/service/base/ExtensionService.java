package com.extensionrepository.service.base;

import com.extensionrepository.entity.Extension;

import java.util.List;

public interface ExtensionService {
    List<Extension> getAll();
    boolean save(Extension extension);
    Extension getById(int id);
    void update(Extension extension);
}
