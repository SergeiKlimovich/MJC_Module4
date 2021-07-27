package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.exception.DuplicateNameException;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.NotExistEntityException;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TagDto> readAll(int page, int size) {
        Page tagPage = new Page(page, size, tagRepository.getCountOfEntities());
        return tagRepository.readAll(tagPage.getOffset(), tagPage.getLimit()).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto read(final Integer tagId) {
        Tag readTag = tagRepository.read(tagId);
        if (readTag == null) {
            throw new NotExistEntityException("There is no tag with id = " + tagId + " in Database");
        } else
            return modelMapper.map(readTag, TagDto.class);
    }

    @Override
    public TagDto create(TagDto tagDto) {
        if (tagRepository.readTagByName(tagDto.getName()).isPresent()) {
            throw new DuplicateNameException("A tag with name = " + tagDto.getName() + " already exists");
        } else {
            Tag addedTag = tagRepository.create(modelMapper.map(tagDto, Tag.class));
            return modelMapper.map(addedTag, TagDto.class);
        }
    }

    @Override
    public void delete(final Integer tagId) {
        Tag read = tagRepository.read(tagId);
        if (read == null) {
            throw new NotExistEntityException("There is no tag with id = " + tagId + " in Database");
        } else
            tagRepository.delete(tagId);
    }

    @Override
    public long getCountOfEntities() {
        return tagRepository.getCountOfEntities();
    }

    @Override
    public TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders() {
        return modelMapper.map(tagRepository.getMostWidelyUsedTagFromUser(), TagDto.class);
    }
}
