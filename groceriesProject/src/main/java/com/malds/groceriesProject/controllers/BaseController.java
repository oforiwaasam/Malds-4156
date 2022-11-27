package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

/**
 * The BaseController class implements common functionality for all
 * Controller classes. The <code>@ExceptionHandler</code> methods
 * provide a consistent response when Exceptions are thrown from
 * <code>@RequestMapping</code> annotated Controller methods.
 *
 */
@RestController
public class BaseController {
    /**
     * Handles all Exceptions not addressed by more specific
     * <code>@ExceptionHandler</code> methods.
     *
     * @param ex An Exception instance.
     * @return The detailed error message.
     */
    @ExceptionHandler({ResourceNotFoundException.class, Exception.class})
    public String handleException(final Exception ex) {
        return ex.getMessage();
    }
}
