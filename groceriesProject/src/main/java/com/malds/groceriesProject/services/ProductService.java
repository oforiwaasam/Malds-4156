package com.malds.groceriesProject.services;

import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // find product by ID
    public Product getProductByID(Integer productID) {
        return null;
    }

}
