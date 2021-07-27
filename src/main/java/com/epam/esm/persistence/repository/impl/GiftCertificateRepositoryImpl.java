package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.util.GiftCertificateQueryParameter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.epam.esm.persistence.util.GiftCertificateCriteriaBuilder.build;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> readAll(int offset, int limit) {
        CriteriaQuery<GiftCertificate> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<GiftCertificate> readAll(GiftCertificateQueryParameter parameter, int offset, int limit) {
        CriteriaQuery<GiftCertificate> criteriaQuery = build(entityManager, parameter);
        return entityManager.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(limit)
                .getResultList();
    }

    @Override
    public GiftCertificate read(final Integer id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void delete(final Integer id) {
        entityManager.remove(read(id));
    }

    @Override
    public long getCountOfEntities(GiftCertificateQueryParameter parameter) {
        CriteriaQuery<GiftCertificate> criteriaQuery = build(entityManager, parameter);
        return entityManager.createQuery(criteriaQuery).getResultList().size();

    }

    @Override
    public long getCountOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(GiftCertificate.class)));
        return entityManager.createQuery(query).getSingleResult();
    }

}
