package br.com.rodrigoplopesdev.calendula_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.rodrigoplopesdev.calendula_api.dtos.exception.ErrorMessageDTO;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> method(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult results) {
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO("Arguments invalid!", HttpStatus.BAD_REQUEST, results));
    }
}
