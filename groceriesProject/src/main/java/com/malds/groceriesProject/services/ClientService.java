package com.malds.groceriesProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malds.groceriesProject.repositories.clientRepository;

@Service
public class ClientService {
    @Autowired
    private clientRepository clientRepo;
}