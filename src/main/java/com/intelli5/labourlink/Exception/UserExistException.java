package com.intelli5.labourlink.Exception;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserExistException extends RuntimeException{
    public UserExistException(String message){
        super(message);
    }
}
