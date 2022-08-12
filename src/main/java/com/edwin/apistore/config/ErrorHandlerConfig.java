package com.edwin.apistore.config;

import com.edwin.apistore.exception.DataNotFoundException;
import com.edwin.apistore.exception.GeneralException;
import com.edwin.apistore.exception.RequestException;
import com.edwin.apistore.util.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Edwin Vargas
 * @version 1.0
 */
@Slf4j
@ControllerAdvice
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<String>> error(Exception e, WebRequest request){
        log.error(e.getMessage(), e);
        CustomResponse<String> response = new CustomResponse<>(false, "Internal Server Error", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<CustomResponse<String>> generalError(GeneralException e, WebRequest request){
        log.error(e.getMessage(), e);
        CustomResponse<String> response = new CustomResponse<>(false, "Internal Server Error", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<CustomResponse<String>> requestError(RequestException e, WebRequest request){
        CustomResponse<String> response = new CustomResponse<>(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<CustomResponse<String>> notFoundError(DataNotFoundException e, WebRequest request){
        CustomResponse<String> response = new CustomResponse(false, e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}