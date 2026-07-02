package com.example.healthmanagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public Map<String,String> handleUserAlreadyExistException(){
        return Map.of("message","User already exist","time", LocalDateTime.now().toString());
    }


    @ExceptionHandler(UserNotFoudException.class)
    public Map<String,String> handleUserNotFoudException(){

        return Map.of("message","User not foud","time", LocalDateTime.now().toString());
    }

    @ExceptionHandler(Exception.class)
    public Map<String,String> handleException(Exception e){
        return Map.of("message",e.getMessage(),"time", LocalDateTime.now().toString());
    }



}
