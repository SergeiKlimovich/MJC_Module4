package com.epam.esm.service.util;

import com.epam.esm.service.exception.InvalidDataException;

import java.util.List;

public final class OrderValidator {
    private OrderValidator() {
    }

    public static void validateForCreate(CreateParameterOrder createParameterOrder) {
        validateUserID(createParameterOrder.getUserID());
        validateCertificatesID(createParameterOrder.getGiftsId());
    }

    private static void validateUserID(int id) {
        if (id < 0) {
            throw new InvalidDataException("User ID must be greater than zero");
        }
    }

    private static void validateCertificatesID(List<Integer> certificatesID) {
        for (int id : certificatesID) {
            if (id < 0) {
                throw new InvalidDataException("Certificate ID must be greater than zero");
            }
        }
    }
}
