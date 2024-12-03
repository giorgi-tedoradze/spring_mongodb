package com.example.demo.controler;

import com.example.demo.exception.ErrorMessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor //>>?? ეს აქ რისთვის?
public class ExceptionController {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessageResponse> badCredentialsException(BadCredentialsException e) {
        return ErrorMessageResponse.createFromException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> exception(Exception e) {
        e.printStackTrace();
        return ErrorMessageResponse.createFromException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}