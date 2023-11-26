package com.clone.exception;

import com.clone.config.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
@SuppressWarnings("unused")
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> MessageExceptionHandler(MessageException e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetail> noHandlerFoundException(NoHandlerFoundException ne, WebRequest request) {
        ErrorDetail err = new ErrorDetail("Endpoint not found", ne.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherErrorHandler(Exception e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

}
