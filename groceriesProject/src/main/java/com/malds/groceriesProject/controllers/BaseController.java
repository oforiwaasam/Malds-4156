package com.malds.groceriesProject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

/**
 * The BaseController class implements common functionality for all
 * Controller classes. The <code>@ExceptionHandler</code> methods
 * provide a consistent response when Exceptions are thrown from
 * <code>@RequestMapping</code> annotated Controller methods.
 *
 */
@RestController
@RestControllerAdvice
public class BaseController {
    /**
     * Handles all Exceptions not addressed by more specific
     * <code>@ExceptionHandler</code> methods.
     *
     * @param ex An Exception instance.
     * @return The detailed error message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ResourceNotFoundException.class, Exception.class})
    public String handleException(final Exception ex) {
        System.out.println(ex.getMessage());
        return ex.getMessage();
    }
}
