//package com.example.SS.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//@ControllerAdvice
//@RestController
//public class GlobalExceptionHandler {
//
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleNotFound(Exception ex) {
//        return "Invalid endpoint. The URL you entered is incorrect or does not exist. Please check and try again.";
//    }
//}
