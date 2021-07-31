package com.epam.esm.web.controller;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Controller that handles requests related to the tag
 *
 * @author Sergei Klimovich
 */
@RestController
@RequestMapping("/api/tags")
public class TagController extends HATEOASController<TagDto> {

    private final TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    /**
     * Get all tags
     *
     * @return List of found tags
     */
    @GetMapping()
    public Collection<TagDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        List<TagDto> tags = service.readAll(page, size);
        addLinksToListTag(tags);
        return addPagination(tags, page, size, service.getCountOfEntities());
    }

    /**
     * Get tag by id
     *
     * @param id of the tag we want to get
     * @return the tag on this id
     */
    @GetMapping("/{id}")
    public TagDto read(@PathVariable int id) {
        return addLinksToTag(service.read(id));
    }

    /**
     * Create a new tag
     *
     * @param tagDto we want to create
     * @return created tag with its id
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public TagDto create(@Valid @RequestBody TagDto tagDto) {
        return service.create(tagDto);
    }

    /**
     * Delete a tag by id
     *
     * @param id of the tag we want to delete
     */

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get mostUsedTag
     *
     * @return tagDTO
     */

    @GetMapping("/mostUsedTag")
    @PreAuthorize("hasRole('ADMIN')")
    public TagDto getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders() {
        return service.getMostWidelyUsedTagFromUserWithHighestCostOfAllOrders();
    }
}
