package com.epam.esm.service;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.exception.DuplicateNameException;
import com.epam.esm.persistence.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.InvalidDataException;
import com.epam.esm.service.exception.NotExistEntityException;
import com.epam.esm.service.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    @Mock
    private TagRepositoryImpl tagRepositoryImpl;

    @Mock
    private ModelMapper modelMapper;

    private Tag tag;
    private TagDto tagDto;
    private List<Tag> tagList = new ArrayList<>();
    private List<TagDto> tagDtoList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setId(1);
        tag.setName("tag");

        tagDto = new TagDto();
        tagDto.setId(1);
        tagDto.setName("user");

        tagList.add(tag);
        tagDtoList.add(tagDto);
    }

    @Test
    @DisplayName("Getting the list of TagDto")
    void readAll_returnsTheExpectedResult_test() {
        //when
        when(tagRepositoryImpl.getCountOfEntities()).thenReturn(1L);
        when(tagRepositoryImpl.readAll(0, 4)).thenReturn(tagList);
        when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        //then
        assertEquals(tagDtoList, tagServiceImpl.readAll(1, 4));
    }

    @Test
    @DisplayName("The method should return the TagDto")
    void read_returnsTheExpectedResult_test() {
        //when
        when(tagRepositoryImpl.read(1)).thenReturn(tag);
        when(modelMapper.map(tag, TagDto.class)).thenReturn(tagDto);
        //then
        assertEquals(tagDto, tagServiceImpl.read(1));
    }

    @Test
    @DisplayName("A Tag with such an ID does not exist, an appropriate exception must be thrown")
    void read_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(tagRepositoryImpl.read(2)).thenReturn(null);
        //then
        assertThrows(NotExistEntityException.class, () -> tagServiceImpl.read(2));
    }

    @Test
    @DisplayName("When creating a tag with invalid data, an exception is thrown InvalidDataException")
    void create_invalidNullName_thrownInvalidDataException_test() {
        assertThrows(InvalidDataException.class, () -> tagServiceImpl.create(new TagDto()));
    }

    @Test
    @DisplayName("When trying to create a tag with a name that already exists, an exception is thrown DuplicateNameException")
    void create_duplicateTagName_thrownDuplicateNameException_test() {
        //when
        when(tagRepositoryImpl.readTagByName(tagDto.getName())).thenReturn(Optional.of(tag));
        //then
        assertThrows(DuplicateNameException.class, () -> tagServiceImpl.create(tagDto));
    }

    @Test
    @DisplayName("The successful establishment of the tag, the method should return TagDto with id")
    void create_returnsTheExpectedResult_test() {
        //given
        TagDto tagDto = new TagDto();
        tagDto.setName("newTagName");

        TagDto createdTagDto = new TagDto();
        createdTagDto.setId(2);
        createdTagDto.setName("newTagName");

        Tag tag = new Tag();
        tag.setName("newTagName");

        Tag createdTag = new Tag();
        createdTag.setId(2);
        createdTag.setName("newTagName");
        //when
        when(tagRepositoryImpl.readTagByName(tagDto.getName())).thenReturn(Optional.empty());
        when(modelMapper.map(tagDto, Tag.class)).thenReturn(tag);
        when(tagRepositoryImpl.create(tag)).thenReturn(createdTag);
        when(modelMapper.map(createdTag, TagDto.class)).thenReturn(createdTagDto);
        //then
        assertEquals(createdTagDto, tagServiceImpl.create(tagDto));
    }

    @Test
    @DisplayName("If you try to delete a tag with a non-existent ID, will be thrown NotExistIdEntityException")
    void delete_notExistId_thrownNotExistIdEntityException_test() {
        //when
        when(tagRepositoryImpl.read(123)).thenReturn(null);
        //then
        assertThrows(NotExistEntityException.class, () -> tagServiceImpl.delete(123));
    }

    @Test
    @DisplayName("Successful remove tag, not thrown away no exception")
    void delete_theEntityWasRemovedFromTheDatabase_test() {
        //when
        when(tagRepositoryImpl.read(1)).thenReturn(tag);
        //then
        assertDoesNotThrow(() -> tagServiceImpl.delete(1));
    }

    @Test
    @DisplayName("Getting the number of existing tags")
    void getCountOfEntities_returnsTheExpectedResult_test() {
        long count = 2L;
        //when
        when(tagRepositoryImpl.getCountOfEntities()).thenReturn(count);
        //then
        assertEquals(count, tagServiceImpl.getCountOfEntities());
    }

}
