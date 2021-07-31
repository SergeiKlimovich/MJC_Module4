package com.epam.esm.web.controller;

import com.epam.esm.persistence.util.GiftCertificateQueryParameter;
import com.epam.esm.service.dto.GiftCertificateDTOs;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.web.exception.RemoveCertificateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Controller that handles requests related to the gift certificate
 *
 * @author Sergei Klimovich
 */
@RestController
@RequestMapping("/api/gift-certificates")
public class GiftCertificateController extends HATEOASController<GiftCertificateDto> {

    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    /**
     * Get List of all GiftCertificates that matches parameters
     *
     * @param parameter is GiftCertificateQueryParameter object with requested parameters
     * @return List of GiftCertificateDto objects with GiftCertificate data.
     */
    @GetMapping()
    public Collection<GiftCertificateDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size,
            GiftCertificateQueryParameter parameter) {
        List<GiftCertificateDto> certificateDtoList = service.readAll(parameter, page, size);
        addLinksToListCertificate(certificateDtoList);
        return addPagination(certificateDtoList, page, size, service.getCountOfEntities(parameter));
    }

    /**
     * Get gift certificate by id
     *
     * @param id of the gift certificate we want to get
     * @return the gift certificate on this id
     */
    @GetMapping("/{id}")
    public GiftCertificateDto read(@PathVariable int id) {
        return addLinksToCertificate(service.read(id));
    }

    /**
     * Create a new gift certificate
     *
     * @param giftCertificateDto we want to create
     * @return created gift certificate with its id
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateDto create(@Valid @RequestBody GiftCertificateDto giftCertificateDto) {

        return addLinkToCertificate(service.create(giftCertificateDto));
    }

    /**
     * Update gift certificate
     *
     * @param giftCertificateDto modified
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateDto update(@PathVariable int id, @Valid @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        return service.update(giftCertificateDto);
    }

    /**
     * Refresh gift certificate
     *
     * @param giftCertificateDTOs modified
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public GiftCertificateDTOs refresh(@PathVariable int id, @Valid @RequestBody GiftCertificateDTOs giftCertificateDTOs) {
        giftCertificateDTOs.setId(id);
        return service.refresh(giftCertificateDTOs);
    }

    /**
     * Delete gift certificate by id
     *
     * @param id of the gift certificate we want to delete
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            service.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new RemoveCertificateException("Can't delete a certificate that exists in an order", e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
