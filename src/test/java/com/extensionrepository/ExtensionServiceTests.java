package com.extensionrepository;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.User;
import com.extensionrepository.repository.base.ExtensionRepository;
import com.extensionrepository.service.ExtensionServiceImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class ExtensionServiceTests {
    @Mock
    private ExtensionRepository extensionRepository;

    @InjectMocks
    private ExtensionServiceImpl extensionService;

    private static final String EXTENSION_SEARCH_PARAM = "Cool";

    @Test
    public void filter_shouldReturnMatchingNames_whenNameSpecified() {
        List<Extension> extensions = Arrays.asList(
                new Extension("Cool Extension1", "test", "test", new User(), "test",  "test", new HashSet<>()),
                new Extension("Extension2", "test", "test", new User(), "test",  "test", new HashSet<>()),
                new Extension("Cool Extension2", "test", "test", new User(),  "test", "test", new HashSet<>()),
                new Extension("Extension4", "test", "test", new User(),  "test", "test", new HashSet<>()),
                new Extension("Cool Extension3", "test", "test", new User(),  "test", "test", new HashSet<>())

        );

        for (Extension e : extensions) {
            e.setPending(false);
            extensionRepository.save(e);
        }



        Mockito.when(extensionRepository.filterByName(EXTENSION_SEARCH_PARAM))
                .thenReturn(extensions.stream().filter(extension -> extension.getName().contains(EXTENSION_SEARCH_PARAM)).collect(Collectors.toList()));

        List<Extension> result = extensionService.filter(EXTENSION_SEARCH_PARAM,null);


        Assert.assertEquals(result.size(),3);
    }

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

}
