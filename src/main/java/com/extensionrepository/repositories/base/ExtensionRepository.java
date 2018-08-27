package com.extensionrepository.repositories.base;

import com.extensionrepository.entity.Extension;

import java.util.List;

public interface ExtensionRepository {
    List<Extension> getAll();
    boolean save(Extension extension);
    Extension getById(int id);
}
