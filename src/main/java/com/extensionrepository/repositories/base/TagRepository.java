package com.extensionrepository.repositories.base;

import com.extensionrepository.entity.Tag;

import java.util.Set;

public interface TagRepository {
    Tag findByName(String name);
    void save(Tag tag);
}
