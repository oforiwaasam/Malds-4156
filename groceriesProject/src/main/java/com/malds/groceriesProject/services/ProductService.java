package com.malds.groceriesProject.services;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
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
    public List<Product> getProductByID(String productID) throws ResourceNotFoundException {
        if (productRepository.existsByID(productID)) {
            return productRepository.findProductById(productID);
        } else {
            throw new ResourceNotFoundException("Product ID not found");
        }
    }

    // find product by Name, fix this to be in order?
    public List<Product> getProductByName(String productName) throws ResourceNotFoundException {
        return productRepository.findProductByName(productName);
    }

    // update product
    public List<Product> updateProduct(Product product) throws ResourceNotFoundException {
        if (productRepository.existsByID(product.getProductID())) {
            return productRepository.updateProduct(product);
        } else {
            throw new ResourceNotFoundException("Product ID not found");
        }
    }

    public Product addNewProduct(Product product) throws Exception {
        if (productRepository.existsByID(product.getProductID())) {
            throw new Exception("product ID already exists - must use unique productID");
        } else {
            try {
                checkValidInput(product);
                return productRepository.addProduct(product);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    public void deleteProductById(String productId) throws ResourceNotFoundException {
        if (productRepository.existsByID(productId)) {
            productRepository.deleteProductByID(productId);
        } else {
            throw new ResourceNotFoundException("Product ID not found");
        }
    }

    public void checkValidInput(Product product) throws Exception {
        if (product.getProductID() == null || product.getProductName() == null
                || product.getVendorID() == null || product.getPrice() == null
                || product.getQuantity() == null) {

            throw new Exception("Value cannot be null");
        }


        try {
            Float.parseFloat(product.getPrice());
            product.getPrice().toString();
        } catch (Exception e) {
            throw new Exception("Price is invalid");
        }

        try {
            Integer.parseInt(product.getQuantity());
            product.getQuantity().toString();
        } catch (Exception e) {
            throw new Exception("Quantity is invalid");
        }

    }

}
