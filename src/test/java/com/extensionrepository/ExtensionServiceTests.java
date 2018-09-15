package com.extensionrepository;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.User;
import com.extensionrepository.repository.base.ExtensionRepository;
import com.extensionrepository.service.ExtensionServiceImpl;
import com.extensionrepository.util.Constants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExtensionServiceTests {
    @Mock
    private ExtensionRepository extensionRepository;

    @InjectMocks
    private ExtensionServiceImpl extensionService;

    private static final String EXTENSION_SEARCH_PARAM = "Cool";


    @Test
    public void getAllExtensions_shouldReturnCorrectNumberOfExtensions(){
        //Arrange
        List<Extension> extensions = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            Extension extension = new Extension();
            extension.setName("test extension " + i);
            extensions.add(extension);
        }

        Mockito.when(extensionRepository.getAll()).thenReturn(extensions);

        //Act
        List<Extension> actualExtensions = extensionService.getAll();

        //Assert
        Assert.assertEquals(actualExtensions.size(), 4);
    }

    @Test
    public void getExtensionById_shouldReturnExtensionWithRequestedId(){
        //Arrange
        Extension extension = new Extension();
        extension.setId(1);

        Mockito.when(extensionRepository.getById(extension.getId())).thenReturn(extension);

        //Act
        Extension actualExtension = extensionService.getById(1);

        //Assert
        Assert.assertEquals(1, actualExtension.getId());

    }

    @Test
    public void createExtension_whenExtensionSave_shouldReturnTrue(){
        //Arrange
        Extension extension = new Extension();

        Mockito.when(extensionRepository.save(extension)).thenReturn(extension.isPending());

        //Act
        boolean isCreated = extensionService.save(extension);

        //Assert
        Assert.assertTrue(isCreated);
    }

    @Test
    public void exists_shouldReturnTrue_whenExtensionExistsByNameOrId() {
        // Arrange
        Extension extension1 = new Extension();
        extension1.setId(1);

        Extension extension2 = new Extension();
        extension2.setName("Extension2");

        Extension extension3 = new Extension();
        extension1.setId(3);

        Extension extension4 = new Extension();
        extension2.setName("Extension4");

        Mockito.when(extensionRepository.getById(extension1.getId())).thenReturn(extension1);
        Mockito.when(extensionRepository.getByName(extension2.getName())).thenReturn(extension2);
        Mockito.when(extensionRepository.getById(extension3.getId())).thenReturn(null);
        Mockito.when(extensionRepository.getByName(extension4.getName())).thenReturn(null);

        // Act
        boolean isExtension1Exist = extensionService.exists(extension1.getId());
        boolean isExtension2Exist = extensionService.exists(extension2.getName());
        boolean isExtension3Exist = extensionService.exists(extension3.getId());
        boolean isExtension4Exist = extensionService.exists(extension4.getName());

        // Assert
        Assert.assertTrue(isExtension1Exist);
        Assert.assertTrue(isExtension2Exist);
        Assert.assertFalse(isExtension3Exist);
        Assert.assertFalse(isExtension4Exist);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void fieldValueExists_shoudlThrowUnsupportedOperationException_whenFieldNameNotName() {
        extensionService.fieldValueExists(new Object(), "not name");
    }

    @Test
    public void fieldValueExists_shouldReturnFalse_whenObjectIsNull() {
        // Act
        boolean result = extensionService.fieldValueExists(null, "name");

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void fieldValueExists_shouldReturnFalse_whenValuePresent() {
        // Arrange
        Extension extension = new Extension();

        // Act
        boolean result = extensionService.fieldValueExists(extension, "name");

        // Assert
        Assert.assertFalse(result);
    }

    /**
     * check if methods are correctly called
     */

    @Test
    public void filter_shouldCallCorrectRepositoryMethod() {
        // Arrange
        List<Extension> extensions = Arrays.asList(
                new Extension(),
                new Extension(),
                new Extension()
        );

        final String extensionName = "name";

        Mockito.when(extensionRepository.filterByName(extensionName)).thenReturn(extensions);
        Mockito.when(extensionRepository.filterByNameAndByDate(extensionName)).thenReturn(extensions);
        Mockito.when(extensionRepository.filterByNameAndByDownloads(extensionName)).thenReturn(extensions);
        Mockito.when(extensionRepository.filterByNameAndByLastCommit(extensionName)).thenReturn(extensions);

        Mockito.when(extensionRepository.filterByName()).thenReturn(extensions);
        Mockito.when(extensionRepository.getByDate()).thenReturn(extensions);
        Mockito.when(extensionRepository.getByDownloads()).thenReturn(extensions);
        Mockito.when(extensionRepository.getByLastCommit()).thenReturn(extensions);

        // Act
        List<Extension> extensionsByNameSpecified = extensionService.filter(extensionName, Constants.SORT_BY_NAME);
        List<Extension> extensionsByNameAndByDate = extensionService.filter(extensionName, Constants.SORT_BY_UPLOAD_DATE);
        List<Extension> extensionsByNameAndByDownloads = extensionService.filter(extensionName, Constants.SORT_BY_DOWNLOADS);
        List<Extension> extensionsByNameAndByLastCommit = extensionService.filter(extensionName, Constants.SORT_BY_LAST_COMMIT);

        List<Extension> allExtensionsByName = extensionService.filter(null, Constants.SORT_BY_NAME);
        List<Extension> allExtensionsDate = extensionService.filter(null, Constants.SORT_BY_UPLOAD_DATE);
        List<Extension> allExtensionsByDownloads = extensionService.filter(null, Constants.SORT_BY_DOWNLOADS);
        List<Extension> allExtensionsByLastCommit = extensionService.filter(null, Constants.SORT_BY_LAST_COMMIT);

        // Assert
        Assert.assertEquals(3, extensionsByNameSpecified.size());
        Assert.assertEquals(3, extensionsByNameAndByDate.size());
        Assert.assertEquals(3, extensionsByNameAndByDownloads.size());
        Assert.assertEquals(3, extensionsByNameAndByLastCommit.size());

        Assert.assertEquals(3, allExtensionsByName.size());
        Assert.assertEquals(3, allExtensionsDate.size());
        Assert.assertEquals(3, allExtensionsByDownloads.size());
        Assert.assertEquals(3, allExtensionsByLastCommit.size());
    }

    @Test
    public void increaseDownloads_shouldCorrectlyIncrementDownloads() {
        // Arrange
        Extension extension = new Extension();
        extension.setId(1);
        extension.setNumberOfDownloads(0);

        Mockito.when(extensionRepository.getById(1)).thenReturn(extension);

        // Act
        extension = extensionService.increaseDownloads(1);

        // Assert
        Assert.assertEquals(1,extension.getNumberOfDownloads());
    }

    @Test
    public void changeStatus_shouldToggleUserEnabledStatus() {
        // Arrange
       Extension extension1 = new Extension();
       extension1.setId(1);
       extension1.setFeatured(true);

        Extension extension2 = new Extension();
        extension2.setId(2);
        extension2.setFeatured(false);

        when(extensionRepository.getById(1)).thenReturn(extension1);
        when(extensionRepository.getById(2)).thenReturn(extension2);

        // Act
        extensionService.changeStatus(1);
        extensionService.changeStatus(2);

        // Assert
        Assert.assertFalse(extension1.isFeatured());
        Assert.assertTrue(extension2.isFeatured());
    }

    @Test
    public void update_shouldReturnExtension_ifUpdateSuccessful() {
        // Arrange
        Extension extension = new Extension();
        Extension extension1 = new Extension();

        when(extensionRepository.update(extension)).thenReturn(extension);
        when(extensionRepository.update(extension1)).thenReturn(null);

        // Act
        boolean isUpdateSuccessful = extensionService.update(extension);
        boolean updateFalse = extensionService.update(extension1);

        // Assert
        Assert.assertTrue(isUpdateSuccessful);
        Assert.assertFalse(updateFalse);
    }

    @Test
    public void delete_shouldReturnExtension_ifUpdateSuccessful() {
        // Arrange
        Extension extension = new Extension();
        Extension extension1 = new Extension();


        when(extensionRepository.delete(extension)).thenReturn(extension);
        when(extensionRepository.delete(extension1)).thenReturn(null);

        // Act
        boolean isDeleteSuccessful = extensionService.delete(extension);
        boolean failedDelete = extensionService.delete(extension1);

        // Assert
        Assert.assertTrue(isDeleteSuccessful);
        Assert.assertFalse(failedDelete);
    }
}
