package com.extensionrepository.repository.base;

import com.extensionrepository.entity.Tag;

public interface TagRepository {
    Tag findByName(String name);
    Tag save(Tag tag);
}
