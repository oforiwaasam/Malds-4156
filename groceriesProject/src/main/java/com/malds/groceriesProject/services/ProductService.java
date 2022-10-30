package com.malds.groceriesProject.services;

import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // find product by ID
    public Product getProductByID(String productID) {
        return productRepository.findProductById(productID);

    }

    public Product addNewProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    public void deleteProductById(String productId) {
        productRepository.deleteProductByID(productId);
    }

}
