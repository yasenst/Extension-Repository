package com.extensionrepository.service.base;

import com.extensionrepository.entity.Tag;

import java.util.Set;

public interface TagService {
    Set<Tag> getTagsFromString(String tagString);
}
