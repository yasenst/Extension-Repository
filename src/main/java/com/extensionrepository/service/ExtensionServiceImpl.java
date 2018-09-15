package com.extensionrepository.service;

import com.extensionrepository.util.Constants;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.repository.base.ExtensionRepository;
import com.extensionrepository.service.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        Extension extension = extensionRepository.getById(id);

        if (extension == null) {
            return false;
        }

        return true;
    }

    @Override
    public boolean exists(String name) {
        Extension extension = extensionRepository.getByName(name);

        if (extension == null) {
            return false;
        }

        return true;
    }

    @Override
    public List<Extension> filter(String name, String sortBy) {
        if (name != null && sortBy != null) {
            switch (sortBy) {
                case Constants.SORT_BY_NAME:
                    return extensionRepository.filterByName(name);

                case Constants.SORT_BY_UPLOAD_DATE:
                    return extensionRepository.filterByNameAndByDate(name);

                case Constants.SORT_BY_DOWNLOADS:
                    return extensionRepository.filterByNameAndByDownloads(name);

                case Constants.SORT_BY_LAST_COMMIT:
                    return extensionRepository.filterByNameAndByLastCommit(name);
            }
        } else if (sortBy != null) {
            switch (sortBy) {
                case Constants.SORT_BY_NAME:
                    return extensionRepository.filterByName();

                case Constants.SORT_BY_UPLOAD_DATE:
                    return extensionRepository.getByDate();

                case Constants.SORT_BY_DOWNLOADS:
                    return extensionRepository.getByDownloads();

                case Constants.SORT_BY_LAST_COMMIT:
                    return extensionRepository.getByLastCommit();
            }
        }
        return extensionRepository.filterByName();
    }

    @Override
    public Extension getById(int id) {
        return extensionRepository.getById(id);
    }

    @Override
    public Extension increaseDownloads(int id) {
        Extension extension = extensionRepository.getById(id);

        int numberOfDownloads = extension.getNumberOfDownloads();
        int increasedNumberOfDownloads = numberOfDownloads + 1;
        extension.setNumberOfDownloads(increasedNumberOfDownloads);

        extensionRepository.update(extension);

        return extension;
    }

    @Override
    public boolean update(Extension extension) {
        if (extensionRepository.update(extension) != null) {
            return  true;
        }
        return false;
    }

    @Override
    public boolean delete(Extension extension) {
        if (extensionRepository.delete(extension) != null) {
            return  true;
        }
        return false;
    }

    @Override
    public List<Extension> getPending() {
        return extensionRepository.getPending();
    }

    @Override
    public List<Extension> getNewest() {
        return extensionRepository.getByDate();
    }

    @Override
    public List getPopular() {
        return extensionRepository.getByDownloads();
    }

    @Override
    public List<Extension> getFeatured() {
        return extensionRepository.getFeatured();
    }

    @Override
    public void changeStatus(int id) {
        Extension extension = extensionRepository.getById(id);

        if (extension.isFeatured()) {
            extension.setFeatured(false);
        } else {
            extension.setFeatured(true);
        }

        extensionRepository.update(extension);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        Assert.notNull(fieldName, "Extension name already exists");

        if (!fieldName.equals("name")) {
            throw new UnsupportedOperationException("Field name not supported");
        }

        if (value == null) {
            return false;
        }

        return exists(value.toString());
    }
}