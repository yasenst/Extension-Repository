package com.extensionrepository.service;

import com.extensionrepository.entity.Tag;
import com.extensionrepository.repositories.base.TagRepository;
import com.extensionrepository.service.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     *
     * @param tagString is space separated strings
     * split by whitespace and collect to String[]
     * check whether each String is a valid Tag
     * and either create it or directly add it to result
     * @return
     */
    @Override
    public Set<Tag> getTagsFromString(String tagString) {
        Set<Tag> tagSet = new HashSet<>();
        String[] tagStringArray = tagString.split("\\s+");

        for (String tagAsString : tagStringArray) {
            Tag tag = tagRepository.findByName(tagAsString);

            if (tag == null) {
                tag = new Tag(tagAsString);
                tagRepository.save(tag);
            }

            tagSet.add(tag);
        }

        return tagSet;
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }
}
