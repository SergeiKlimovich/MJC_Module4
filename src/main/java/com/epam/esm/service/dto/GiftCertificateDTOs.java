package com.epam.esm.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Relation(itemRelation = "certificate", collectionRelation = "certificates")
public class GiftCertificateDTOs extends RepresentationModel<GiftCertificateDTOs> {
    private int id;

    @Size(min = 2, max = 90, message = "Name should be between 2 and 90 characters")
    private String name;

    @Size(min = 2, max = 90, message = "Description should be between 2 and 90 characters")
    private String description;

    @Digits(integer = 8, fraction = 2, message = "No more than 2 decimal places")
    @Min(value = 1, message = "Min value of price must be more than 1")
    @Max(value = 100000, message = "Max value of price must be less than 100000")
    private Double price;

    @Min(value = 1, message = "Min value of duration must be more than 1")
    @Max(value = 365, message = "Max value of duration must be less than 365")
    private Integer duration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TagDto> tags;

}
