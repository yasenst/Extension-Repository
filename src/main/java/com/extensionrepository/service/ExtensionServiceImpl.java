package com.extensionrepository.service;

import com.extensionrepository.constant.Constants;
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
    public List<Extension> filter(String name, String sortBy) {
        if (sortBy != null) {
            switch (sortBy) {
                case Constants.SORT_BY_NAME:
                    return extensionRepository.filterByName(name);

                case Constants.SORT_BY_UPLOAD_DATE:
                    return extensionRepository.filterByDate(name);

                case Constants.SORT_BY_DOWNLOADS:
                    return extensionRepository.filterByDownloads(name);
            }
        }
        return extensionRepository.filterByName(name);
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

    @Override
    public List<Extension> getNewest() {
        return extensionRepository.getNewest();
    }

    @Override
    public List<Extension> getPopular() {
        return extensionRepository.getPopular();
    }

    @Override
    public List<Extension> getFeatured() {
        return extensionRepository.getFeatured();
    }

    @Override
    public void changeStatus(int id) {
        extensionRepository.changeStatus(id);
    }


}