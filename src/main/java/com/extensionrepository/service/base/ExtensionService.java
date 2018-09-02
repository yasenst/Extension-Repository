package com.extensionrepository.service.base;

import com.extensionrepository.entity.Extension;

import java.util.List;

public interface ExtensionService extends FieldValueExists {
    List<Extension> getAll();
    List<Extension> getPending();
    List<Extension> getNewest();
    List<Extension> getPopular();
    List<Extension> getFeatured();
    List<Extension> filter(String name, String sortBy);
    Extension getById(int id);
    boolean save(Extension extension);
    boolean exists(int id);
    void update(Extension extension);
    void delete(Extension extension);
    void changeStatus(int id);

}
