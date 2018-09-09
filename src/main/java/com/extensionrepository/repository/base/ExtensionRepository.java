package com.extensionrepository.repository.base;

import com.extensionrepository.entity.Extension;

import java.util.List;

public interface ExtensionRepository {
    List<Extension> getAll();
    List<Extension> getPending();
    List<Extension> getByDate();
    List<Extension> getByDownloads();
    List<Extension> getByLastCommit();
    List<Extension> getFeatured();
    List<Extension> filterByNameAndByLastCommit(String name);
    List<Extension> filterByNameAndByDate(String name);
    List<Extension> filterByNameAndByDownloads(String name);
    List<Extension> filterByName(String name);
    List<Extension> filterByName();
    Extension getById(int id);
    Extension getByName(String name);
    boolean save(Extension extension);
    Extension update(Extension extension);
    Extension delete(Extension extension);
}
