
package com.extensionrepository.controller.rest;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.service.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExtensionRestController {

    private ExtensionService extensionService;

    @Autowired
    public ExtensionRestController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping("/api/extension/all")
    public List<Extension> getExtensions() {
        List<Extension> extensions = extensionService.getAll();

        return extensions;
    }

    @GetMapping("/api/extension/{id}")
    public Extension getExtensionById(@PathVariable int id) {
        return extensionService.getById(id);
    }

    @GetMapping("/api/extension/browse")
    public List<Extension> filterExtensions(@RequestParam(value="name", required = false) String name,
                                            @RequestParam(value="sortBy", required = false) String sortBy){
        List<Extension> extensions = extensionService.filter(name, sortBy);

        return extensions;
    }

    @GetMapping("/api/extension/featured")
    public List<Extension> getFeatured(){
        List<Extension> extensions = extensionService.getFeatured();

        return extensions;
    }
}
