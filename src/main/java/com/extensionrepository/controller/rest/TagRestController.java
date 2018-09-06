package com.extensionrepository.controller.rest;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.Tag;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TagRestController {

    private TagService tagService;

    private ExtensionService extensionService;

    @Autowired
    public TagRestController(TagService tagService, ExtensionService extensionService) {
        this.tagService = tagService;
        this.extensionService = extensionService;
    }

    @GetMapping("/api/extension/tag/{name}")
    private Set<Extension> extensionsByTag(@PathVariable String name){
        Tag tag = tagService.findByName(name);
        Set<Extension> extensions = tag.getExtensions();

        return extensions;
    }
}
