package com.rso.Trips.error;

import com.rso.Trips.error.ErrorResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(
            Exception e
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(EmptyResultDataAccessException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(
            Exception e
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }

    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(
            Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace // specifying the stack trace in case of 500s
                ),
                status
        );
    }
}