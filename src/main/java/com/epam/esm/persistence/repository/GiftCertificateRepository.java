package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.util.GiftCertificateQueryParameter;

import java.util.List;

/**
 * An interface that inherits the CRD and other the methods required for the gift certificate
 *
 * @author Sergei klimovich
 */
public interface GiftCertificateRepository {
    /**
     * Get all entities from database
     *
     * @return List of found entities
     */
    List<GiftCertificate> readAll(int offset, int limit);

    /**
     * Get entity
     *
     * @param id of the entity we want to get from database
     * @return the entity on this id
     */
    GiftCertificate read(final Integer id);

    /**
     * Create a new entity
     *
     * @param entity exemplar
     * @return the entity on this id
     */
    GiftCertificate create(final GiftCertificate entity);

    /**
     * Delete entity by id
     *
     * @param id of the entity we want to delete
     */
    void delete(final Integer id);

    /**
     * Update entity
     *
     * @param entity modified
     * @return giftCertificate entity
     */

    GiftCertificate update(final GiftCertificate entity);

    /**
     * Get entities from database that meet the search criteria.
     *
     * @param parameter - GiftCertificateQueryParameter object containing params for request
     * @return List of matched GiftCertificate entities from database.
     */
    List<GiftCertificate> readAll(GiftCertificateQueryParameter parameter, int page, int size);

    /**
     * Get count of exist gift certificates.
     *
     * @return number of exist gift certificates
     */
    long getCountOfEntities();

    /**
     * Get count of exist gift certificates which correspond to the parameters.
     *
     * @return number of exist gift certificates which correspond to the parameters.
     */
    long getCountOfEntities(GiftCertificateQueryParameter parameter);


}
