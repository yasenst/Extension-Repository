package com.extensionrepository;

import com.extensionrepository.entity.Tag;
import com.extensionrepository.repository.base.TagRepository;
import com.extensionrepository.service.TagServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTests {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void getTagsFromString_ShouldReturnRelevantSetOfTags() {
        // Arrange
        Tag tag1 = new Tag();
        tag1.setName("Tag1");

        Tag tag2 = new Tag();
        tag2.setName("Tag2");

        Tag tag3 = new Tag();
        tag3.setName("Tag3");

        Set<Tag> tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        String tagString = "Tag1 Tag2 Tag3";

        Mockito.when(tagRepository.findByName("Tag1")).thenReturn(tag1);
        Mockito.when(tagRepository.findByName("Tag2")).thenReturn(tag2);
        Mockito.when(tagRepository.findByName("Tag3")).thenReturn(null);
        Mockito.when(tagRepository.save(any(Tag.class))).thenReturn(tag3);

        // Act
        Set<Tag> actualTags = tagService.getTagsFromString(tagString);

        // Assert
        Assert.assertEquals(tags, actualTags);
        Assert.assertEquals(3, actualTags.size());
    }

    @Test
    public void findByName_shouldReturnTag_whenExistingNameGiven() {
        // Arrange
        Tag tag = new Tag();
        tag.setName("Tag");

        Mockito.when(tagRepository.findByName("Tag")).thenReturn(tag);

        // Act
        Tag actualTag = tagService.findByName("Tag");

        // Assert
        Assert.assertEquals(tag, actualTag);
    }
}
