package com.extensionrepository.service.base;

import com.extensionrepository.entity.Extension;

import java.util.List;

public interface ExtensionService {
    List<Extension> getAll();
    boolean save(Extension extension);
    boolean exists(int id);
    List<Extension> filter(String name, String criteria);
    Extension getById(int id);
    void update(Extension extension);
    void delete(Extension extension);
    List<Extension> getPending();
    List<Extension> getNewest();
    List<Extension> getPopular();

}
