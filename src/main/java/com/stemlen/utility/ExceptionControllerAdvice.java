package com.stemlen.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import com.stemlen.exception.PortalException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private Environment environment;

    @ExceptionHandler(PortalException.class)
    public ResponseEntity<ErrorInfo> handlePortalException(PortalException exception) {
        String errorMessage = environment.getProperty(exception.getMessage(), "An unknown error occurred.");
        ErrorInfo error = new ErrorInfo(
            errorMessage,
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalException(Exception exception) {
        String errorMessage = environment.getProperty(exception.getMessage(), "An unknown error occurred.");
        ErrorInfo error = new ErrorInfo(
            errorMessage,
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception) {
        String msg = "";
        if (exception instanceof MethodArgumentNotValidException manvException) {
            msg = manvException.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        } else if (exception instanceof ConstraintViolationException cvException) {
            msg = cvException.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
        }

        ErrorInfo error = new ErrorInfo(
            msg,
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}