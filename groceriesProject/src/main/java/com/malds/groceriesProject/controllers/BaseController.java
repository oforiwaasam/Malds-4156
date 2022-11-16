package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

@RestController
public class BaseController {


    @ExceptionHandler({ResourceNotFoundException.class, Exception.class})
    public String handleException(Exception ex) {
        return ex.getMessage();
    }
}
