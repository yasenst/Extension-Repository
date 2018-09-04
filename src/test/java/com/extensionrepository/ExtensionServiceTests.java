package com.extensionrepository;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.User;
import com.extensionrepository.repositories.base.ExtensionRepository;
import com.extensionrepository.service.ExtensionServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.print.DocFlavor;
import java.lang.reflect.Array;
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

    /*@Test
    public void createExtension_whenExtensionSave_shouldReturnTrue(){
        //Arrange
        Extension extension = new Extension();

        Mockito.when(extensionRepository.save(extension)).thenReturn(extension.isPending());

        //Act
        boolean isCreated = extensionService.save(extension);

        //Assert
        Assert.assertTrue("true", isCreated);
    }*/
}
