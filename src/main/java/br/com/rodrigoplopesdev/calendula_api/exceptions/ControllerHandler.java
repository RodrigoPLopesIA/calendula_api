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

    @ExceptionHandler(DuplicatedTitleException.class)
    public ResponseEntity<ErrorMessageDTO> duplicatedTitleException(DuplicatedTitleException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest()
                .body(new ErrorMessageDTO(request.getServletPath(), ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> methodArgumentNotValidException(MethodArgumentNotValidException ex,
            HttpServletRequest request, BindingResult results) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(request.getServletPath(), "Arguments invalid!", HttpStatus.BAD_REQUEST,
                        results));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> entityNotFoundException(EntityNotFoundException ex,
            HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessageDTO(request.getServletPath(), ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorMessageDTO> businessException(BusinessException ex, HttpServletRequest request) {

        return ResponseEntity.badRequest()
                .body(new ErrorMessageDTO(request.getServletPath(), ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
