package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.malds.groceriesProject.repositories.ClientRepository;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepo;
}
