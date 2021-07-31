package com.epam.esm.service.util;

import com.epam.esm.service.exception.InvalidDataException;

public class Page {
    private int page;
    private int size;
    private long countEntity;

    public Page(int page, int size, long countEntity) throws InvalidDataException {
        validateData(page, size, countEntity);
        this.page = page;
        this.size = size;
        this.countEntity = countEntity;
    }

    public int getOffset() {
        return (page - 1) * size;
    }

    public int getLimit() {
        return size;
    }

    private boolean validateData(int page, int size, long countEntity) {
        if (page <= 0) {
            throw new InvalidDataException("The page number starts with 1.");
        }
        if (size < 0) {
            throw new InvalidDataException("The number of output entities must be greater than 0.");
        }

        return true;
    }
}
