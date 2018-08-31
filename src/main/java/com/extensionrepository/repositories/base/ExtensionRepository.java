package com.extensionrepository.repositories.base;

import com.extensionrepository.entity.Extension;

import java.util.List;

public interface ExtensionRepository {
    List<Extension> getAll();
    List<Extension> getPending();
    List<Extension> filter(String name, String criteria);
    List<Extension> filterByName(String name);
    List<Extension> filterByDate(String name);
    List<Extension> filterByDownloads(String name);
    List<Extension> getNewest();
    List<Extension> getPopular();
    List<Extension> getFeatured();
    Extension getById(int id);
    boolean exists(int id);
    boolean save(Extension extension);
    void update(Extension extension);
    void delete(Extension extension);
    void changeStatus(int id);



}
