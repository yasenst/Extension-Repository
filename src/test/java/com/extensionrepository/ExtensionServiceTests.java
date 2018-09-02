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
                new Extension("Cool Extension1", "test", "test", new User(), "test", "test", "test", new HashSet<>()),
                new Extension("Extension2", "test", "test", new User(), "test", "test", "test", new HashSet<>()),
                new Extension("Cool Extension2", "test", "test", new User(), "test", "test", "test", new HashSet<>()),
                new Extension("Extension4", "test", "test", new User(), "test", "test", "test", new HashSet<>()),
                new Extension("Cool Extension3", "test", "test", new User(), "test", "test", "test", new HashSet<>())

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
}
