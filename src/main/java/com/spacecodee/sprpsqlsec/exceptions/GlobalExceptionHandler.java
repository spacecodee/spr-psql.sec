package com.spacecodee.sprpsqlsec.exceptions;

import com.spacecodee.sprpsqlsec.data.pojo.ApiErrorPojo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ApiErrorPojo apiErrorPojo = new ApiErrorPojo();

    @ExceptionHandler(NoDisabledException.class)
    public ResponseEntity<ApiErrorPojo> noDisabledHException(NoDisabledException exception, HttpServletRequest request) {
        this.apiErrorPojo.setBackendMessage(exception.getCause().getLocalizedMessage());
        this.apiErrorPojo.setMessage(exception.getMessage());
        this.apiErrorPojo.setTimestamp(LocalDateTime.now());
        this.apiErrorPojo.setPath(request.getRequestURI());
        this.apiErrorPojo.setMethod(request.getMethod());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.apiErrorPojo);
    }

    @ExceptionHandler(NoUpdatedException.class)
    public ResponseEntity<ApiErrorPojo> noUpdatedHException(NoUpdatedException exception, HttpServletRequest request) {
        this.apiErrorPojo.setBackendMessage(exception.getCause().getLocalizedMessage());
        this.apiErrorPojo.setMessage(exception.getMessage());
        this.apiErrorPojo.setTimestamp(LocalDateTime.now());
        this.apiErrorPojo.setPath(request.getRequestURI());
        this.apiErrorPojo.setMethod(request.getMethod());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.apiErrorPojo);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ApiErrorPojo> notFoundHException(ObjectNotFoundException exception, HttpServletRequest request) {
        this.apiErrorPojo.setBackendMessage(exception.getCause().getLocalizedMessage());
        this.apiErrorPojo.setMessage(exception.getMessage());
        this.apiErrorPojo.setTimestamp(LocalDateTime.now());
        this.apiErrorPojo.setPath(request.getRequestURI());
        this.apiErrorPojo.setMethod(request.getMethod());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this.apiErrorPojo);
    }

    @ExceptionHandler(NoCreatedException.class)
    public ResponseEntity<ApiErrorPojo> noCreatedHException(NoCreatedException exception, HttpServletRequest request) {
        this.apiErrorPojo.setBackendMessage(exception.getCause().getLocalizedMessage());
        this.apiErrorPojo.setMessage(exception.getMessage());
        this.apiErrorPojo.setTimestamp(LocalDateTime.now());
        this.apiErrorPojo.setPath(request.getRequestURI());
        this.apiErrorPojo.setMethod(request.getMethod());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.apiErrorPojo);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorPojo> handlerGenericException(Exception exception, HttpServletRequest request) {
        this.apiErrorPojo.setBackendMessage(exception.getLocalizedMessage());
        this.apiErrorPojo.setMessage("An error occurred while processing your request");
        this.apiErrorPojo.setTimestamp(LocalDateTime.now());
        this.apiErrorPojo.setPath(request.getRequestURI());
        this.apiErrorPojo.setMethod(request.getMethod());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this.apiErrorPojo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorPojo> handlerMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
        this.apiErrorPojo.setBackendMessage(exception.getLocalizedMessage());
        this.apiErrorPojo.setMessage("An error occurred while we were trying to send your request");
        this.apiErrorPojo.setTimestamp(LocalDateTime.now());
        this.apiErrorPojo.setPath(request.getRequestURI());
        this.apiErrorPojo.setMethod(request.getMethod());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.apiErrorPojo);
    }
}
