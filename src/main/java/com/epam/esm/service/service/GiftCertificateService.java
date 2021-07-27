package com.epam.esm.service.service;

import com.epam.esm.persistence.util.GiftCertificateQueryParameter;
import com.epam.esm.service.dto.GiftCertificateDTOs;
import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the gift certificate
 *
 * @author Sergei Klimovich
 */
public interface GiftCertificateService {
    /**
     * Get all entities
     *
     * @return List of found entities
     */
    List<GiftCertificateDto> readAll(int page, int size);

    /**
     * Get entity by id
     *
     * @param id of the entity we want to get
     * @return the entity on this ID
     */
    GiftCertificateDto read(final Integer id);

    /**
     * Create a new entity
     *
     * @param entity generic exemplar
     * @return created entity with its ID
     */
    GiftCertificateDto create(final GiftCertificateDto entity);

    /**
     * Delete entity by ID
     *
     * @param id of the entity we want to delete
     */
    void delete(final Integer id);

    /**
     * Update entity
     *
     * @param entity modified
     * @return updated entity
     */
    GiftCertificateDto update(final GiftCertificateDto entity);

    GiftCertificateDTOs refresh(final GiftCertificateDTOs entity);

    /**
     * Get List of GiftCertificates that matches parameters
     *
     * @param parameter is GiftCertificateQueryParameter object with requested parameters
     * @return List of GiftCertificateDto objects with GiftCertificate data.
     */
    List<GiftCertificateDto> readAll(GiftCertificateQueryParameter parameter, int page, int size);

    /**
     * Get count of exist gift certificates.
     *
     * @return number of exist gift certificates
     */
    long getCountOfEntities(GiftCertificateQueryParameter parameter);

}
