package com.extensionrepository.service;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.repositories.base.ExtensionRepository;
import com.extensionrepository.service.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtensionServiceImpl implements ExtensionService {

    private ExtensionRepository extensionRepository;

    @Autowired
    public ExtensionServiceImpl(ExtensionRepository extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    @Override
    public List<Extension> getAll() {
        return extensionRepository.getAll();
    }

    @Override
    public boolean save(Extension extension) {
        return extensionRepository.save(extension);
    }

    @Override
    public boolean exists(int id) {
        return extensionRepository.exists(id);
    }

    @Override
    public List<Extension> searchByName(String name) {
        return extensionRepository.searchByName(name);
    }

    @Override
    public Extension getById(int id) {
        return extensionRepository.getById(id);
    }

    @Override
    public void update(Extension extension) {
        extensionRepository.update(extension);
    }

    @Override
    public void delete(Extension extension) {
        extensionRepository.delete(extension);
    }

    @Override
    public List<Extension> getPending() {
        return extensionRepository.getPending();
    }
}