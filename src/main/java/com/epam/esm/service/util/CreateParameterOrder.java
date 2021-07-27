package com.epam.esm.service.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateParameterOrder {
    private int userID;

    @NotEmpty(message = "List of giftCertificates should not be empty")
    private List<Integer> giftsId;
}
