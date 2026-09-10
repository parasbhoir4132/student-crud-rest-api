package com.studentcrud.studentcrud.exception;

import com.studentcrud.studentcrud.dto.ExceptionRespDTO;
import com.studentcrud.studentcrud.dto.ValidationExceptionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionRespDTO> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest httpServletRequest){

        ExceptionRespDTO exceptionRespDTO = new ExceptionRespDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                httpServletRequest.getRequestURI()
        );


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionRespDTO);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ExceptionRespDTO> handleDuplicateResourceException(DuplicateResourceException ex, HttpServletRequest httpServletRequest){

        ExceptionRespDTO exceptionRespDTO = new ExceptionRespDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionRespDTO);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest) {

        Map<String , String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach( error -> fieldErrors.put(error.getField() ,error.getDefaultMessage()));

        ValidationExceptionDTO exceptionRespDTO = new ValidationExceptionDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                httpServletRequest.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionRespDTO);
    }


        @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionRespDTO> handleGenericException(Exception ex, HttpServletRequest httpServletRequest){

        ExceptionRespDTO exceptionRespDTO = new ExceptionRespDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionRespDTO);
    }

}
