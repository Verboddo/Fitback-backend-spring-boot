package com.smeekens.fitback.fitback.fitback.exceptions;

import java.io.Serial;

public class RecordNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(Long id) {
        super("Cannot find record: " + id);
    }

    public RecordNotFoundException() {
        super("record not found");
    }
}
