package com.epam.esm.service.service.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.util.GiftCertificateQueryParameter;
import com.epam.esm.service.dto.GiftCertificateDTOs;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.NotExistEntityException;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagDAO;
    private ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagDAO, ModelMapper modelMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagDAO = tagDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<GiftCertificateDto> readAll(int page, int size) {
        Page certificatePage = new Page(page, size, giftCertificateRepository.getCountOfEntities());
        return giftCertificateRepository.readAll(certificatePage.getOffset(), certificatePage.getLimit()).stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> readAll(GiftCertificateQueryParameter parameter, int page, int size) {
        if (parameter.isEmpty()) {
            return readAll(page, size);
        }
        Page certificatePage = new Page(page, size, giftCertificateRepository.getCountOfEntities(parameter));
        List<GiftCertificate> giftCertificates = giftCertificateRepository.readAll(parameter, certificatePage.getOffset(), certificatePage.getLimit());
        return giftCertificates.stream().map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getCountOfEntities(GiftCertificateQueryParameter parameter) {
        return giftCertificateRepository.getCountOfEntities(parameter);
    }

    @Override
    public GiftCertificateDto read(final Integer id) {
        GiftCertificate readGiftCertificate = giftCertificateRepository.read(id);
        if (readGiftCertificate == null) {
            throw new NotExistEntityException("There is no gift certificate with id = " + id + " in Database");
        } else {
            return modelMapper.map(readGiftCertificate, GiftCertificateDto.class);
        }
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate createdGiftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
        createAndSetTags(createdGiftCertificate);
        giftCertificateRepository.create(createdGiftCertificate);
        return modelMapper.map(createdGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto modifiedGiftCertificateDto) {
        GiftCertificate readGiftCertificate = giftCertificateRepository.read(modifiedGiftCertificateDto.getId());
        if (readGiftCertificate == null) {
            throw new NotExistEntityException("There is no gift certificate with ID = " + modifiedGiftCertificateDto.getId() + " in Database");
        }
        GiftCertificate modifiedGiftCertificate = modelMapper.map(modifiedGiftCertificateDto, GiftCertificate.class);
        updateGiftCertificateFields(readGiftCertificate, modifiedGiftCertificate);
        GiftCertificate updatedGiftCertificate = giftCertificateRepository.update(readGiftCertificate);
        updatedGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDTOs refresh(GiftCertificateDTOs modifiedGiftCertificateDTOs) {
        GiftCertificate readGiftCertificate = giftCertificateRepository.read(modifiedGiftCertificateDTOs.getId());
        if (readGiftCertificate == null) {
            throw new NotExistEntityException("There is no gift certificate with ID = " + modifiedGiftCertificateDTOs.getId() + " in Database");
        }
        GiftCertificate modifiedGiftCertificate = modelMapper.map(modifiedGiftCertificateDTOs, GiftCertificate.class);
        updateGiftCertificateFields(readGiftCertificate, modifiedGiftCertificate);
        GiftCertificate updatedGiftCertificate = giftCertificateRepository.update(readGiftCertificate);
        updatedGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        return modelMapper.map(updatedGiftCertificate, GiftCertificateDTOs.class);
    }


    @Override
    public void delete(final Integer id) {
        GiftCertificate readGiftCertificate = giftCertificateRepository.read(id);
        if (readGiftCertificate == null) {
            throw new NotExistEntityException("There is no gift certificate with ID = " + id + " in Database");
        } else
            giftCertificateRepository.delete(id);
    }

    private void createAndSetTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            List<Tag> prepared = new ArrayList<>();
            giftCertificate.setTags(prepared);

            tags.forEach(tag -> {
                Optional<Tag> readTagByName = tagDAO.readTagByName(tag.getName());
                if (!readTagByName.isPresent()) {
                    prepared.add(tag);
                } else {
                    Tag readTag = tagDAO.readTagByName(tag.getName()).get();
                    prepared.add(readTag);
                }
            });
        }
    }


    protected void updateGiftCertificateFields(GiftCertificate readGiftCertificate, GiftCertificate
            modifiedGiftCertificate) {
        if (Objects.nonNull((modifiedGiftCertificate.getName()))) {
            readGiftCertificate.setName(modifiedGiftCertificate.getName());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getDescription())) {
            readGiftCertificate.setDescription(modifiedGiftCertificate.getDescription());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getPrice())) {
            readGiftCertificate.setPrice(modifiedGiftCertificate.getPrice());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getDuration())) {
            readGiftCertificate.setDuration(modifiedGiftCertificate.getDuration());
        }

        if (Objects.nonNull(modifiedGiftCertificate.getTags())) {
            createAndSetTags(modifiedGiftCertificate);
            readGiftCertificate.setTags(modifiedGiftCertificate.getTags());
        }
    }
}
