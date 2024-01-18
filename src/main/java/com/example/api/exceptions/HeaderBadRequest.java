package com.example.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HeaderBadRequest extends RuntimeException{

    public HeaderBadRequest(String message) {
        super(message);
    }

}
