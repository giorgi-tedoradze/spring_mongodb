package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {
    private String message;

    public static ResponseEntity<ErrorMessageResponse> createFromException(Exception exception, HttpStatus status) {
        ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse();
        errorMessageResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorMessageResponse, status);
    }
}
