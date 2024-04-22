package com.intelli5.labourlink.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CustomerRegistrationException extends Throwable {
    public CustomerRegistrationException(String ex) {
        super(ex);
    }
}
