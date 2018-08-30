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
                new Extension("Cool Extension1", "test", "test", new User(), "test", "test", "test"),
                new Extension("Extension2", "test", "test", new User(), "test", "test", "test"),
                new Extension("Cool Extension2", "test", "test", new User(), "test", "test", "test"),
                new Extension("Extension4", "test", "test", new User(), "test", "test", "test"),
                new Extension("Cool Extension3", "test", "test", new User(), "test", "test", "test")

        );

        for (Extension e : extensions) {
            e.setPending(false);
        }

        Mockito.when(extensionRepository.filter(EXTENSION_SEARCH_PARAM,null))

                .thenReturn(extensions.stream().filter(extension -> extension.getName().contains(EXTENSION_SEARCH_PARAM)).collect(Collectors.toList()));

        List<Extension> result = extensionService.filter(EXTENSION_SEARCH_PARAM,null);

        Assert.assertEquals(result.size(),3);
    }
}
