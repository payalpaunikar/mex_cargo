package com.mexcorgo.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleUserNotFoundException(UserNotFoundException exception){
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }


    @ExceptionHandler(UnauthorizedDepartmentAccessException.class)
    public ResponseEntity<Map<String,Object>> handleUnauthorizedDepartmentAccessException(UnauthorizedDepartmentAccessException unauthorizedDepartmentAccessException){
     return buildResponse(HttpStatus.UNAUTHORIZED,unauthorizedDepartmentAccessException.getMessage());
    }

    @ExceptionHandler(HeadAlredyExitException.class)
    public ResponseEntity<Map<String,Object>> handleHeadAlredyExitException(HeadAlredyExitException headAlredyExitException){
        return buildResponse(HttpStatus.CONFLICT, headAlredyExitException.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String,Object>> handleUnauthorizedException(UnauthorizedException unauthorizedException){
        return buildResponse(HttpStatus.UNAUTHORIZED,unauthorizedException.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException){
        return buildResponse(HttpStatus.UNAUTHORIZED,resourceNotFoundException.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String,Object>> handleCustomException(CustomException exception){
        return buildResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,Object>> handleDuplicateEmail(DataIntegrityViolationException dataIntegrityViolationException){

        if(dataIntegrityViolationException.getMessage().contains("Duplicate entry")){
            return buildResponse(HttpStatus.BAD_REQUEST,"Email alredy exit, please use another email");
        }

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,"An error occured, Please try again.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){

        Map<String,String> errorMessages = new HashMap<>();

        for(FieldError error: methodArgumentNotValidException.getBindingResult().getFieldErrors()){
            errorMessages.put(error.getField(),error.getDefaultMessage());
        }

        return new ResponseEntity<>(errorMessages,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMemberSizeException.class)
     public ResponseEntity<Map<String,Object>> handleInvalidMemberException(InvalidMemberSizeException exception){
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
     }


     @ExceptionHandler(InvalidLeadStatusException.class)
     public ResponseEntity<Map<String,Object>> handleInvalidLeadStatusException(InvalidLeadStatusException exception){
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
     }

    public ResponseEntity<Map<String,Object>> buildResponse(HttpStatus httpStatus, String message){
        Map<String,Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status",httpStatus.value());
        response.put("error",httpStatus.getReasonPhrase());
        response.put("message",message);
        return new ResponseEntity<>(response,httpStatus);
    }
}
