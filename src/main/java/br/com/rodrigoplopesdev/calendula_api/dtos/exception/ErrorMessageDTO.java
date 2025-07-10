package br.com.rodrigoplopesdev.calendula_api.dtos.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public class ErrorMessageDTO {

    private String message;
    private HttpStatus status;
    private Map<String, String> errors = new HashMap<>();

    public ErrorMessageDTO(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ErrorMessageDTO(String message, HttpStatus status, BindingResult result) {
        this(message, status);
        this.addErrors(result);
    }

    private void addErrors(BindingResult result) {
        result.getFieldErrors().forEach(error -> {
            this.errors.put(error.getField(), error.getDefaultMessage());
        });
    }

}
