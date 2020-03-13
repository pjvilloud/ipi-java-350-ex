//package com.ipiecoles.java.java350.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.persistence.EntityNotFoundException;
//
//    @RestControllerAdvice ("com.ipiecoles.java.java350.controller")
//    public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
//
//        @ExceptionHandler(EntityNotFoundException.class)
//        @ResponseStatus(HttpStatus.NOT_FOUND)
//        public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
//            return entityNotFoundException.getMessage();
//        }
//}
