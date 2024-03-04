package com.blog.exceptions;

import com.blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//Controller Exception Handling
@RestControllerAdvice
public class GlobalExceptionHandler {
    //When ResourceNotFound Exception Occurs then this methode will call
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String msg = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(msg, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleMethodeArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String,String>resp=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error->{
            String fieldName = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            resp.put(fieldName,defaultMessage);
        }));
        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);

    }
}
