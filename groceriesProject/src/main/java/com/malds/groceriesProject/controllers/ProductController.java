package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.malds.groceriesProject.repositories.ProductRepository;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
}