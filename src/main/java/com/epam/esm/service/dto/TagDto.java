package com.epam.esm.service.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagDto extends RepresentationModel<TagDto> {
    private int id;
    @Pattern(regexp = "([A-Za-zА-Яа-яЁёЙй]{2,})", message = "Name should have include just letters")
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 90, message = "Please, enter the correct name (more than 2 and less than 90 letters)")

    private String name;
}
