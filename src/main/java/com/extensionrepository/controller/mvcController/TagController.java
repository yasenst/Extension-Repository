package com.extensionrepository.controller.mvcController;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.Tag;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class TagController {

    private TagService tagService;

    private ExtensionService extensionService;

    @Autowired
    public TagController(TagService tagService, ExtensionService extensionService) {
        this.tagService = tagService;
        this.extensionService = extensionService;
    }

    @GetMapping("/extension/tag/{name}")
    public String listExtensionsByTag(@PathVariable String name, Model model) {
        Tag tag = tagService.findByName(name);
        Set<Extension> extensions = tag.getExtensions();

        model.addAttribute("tag", tag);
        model.addAttribute("extensions", extensions);
        model.addAttribute("view", "extension/taglist");
        return "base-layout";
    }
}
