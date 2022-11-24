package com.malds.groceriesProject.services;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    /**
     * Product Service. Carries out the operations and
     * interacts with the persistence layer, product Repository,
     * to create, read, update, and delete from the database.
     */

    @Autowired
    private ProductRepository productRepository;

    /**
     * Returns Product by ProductID as a list of size 1. Throws Exception if
     * productID doesn't exists.
     *
     * @param productID
     * @return List containing the product.
     * @throws ResourceNotFoundException
     */
    public List<Product> getProductByID(
        final String productID) throws ResourceNotFoundException {
        if (productRepository.existsByID(productID)) {
            return productRepository.findProductById(productID);
        } else {
            throw new ResourceNotFoundException("Product ID not found");
        }
    }

    /**
     * Returns Product by productName as a list of size 1.
     *
     * @param productName
     * @return List containing the products containing productName.
     */
    public List<Product> getProductByName(final String productName) {
        return productRepository.findProductByName(productName);
    }


    /**
     * Updates product info and returns new product with updated info.
     * Throws Exception if productID doesn't exists.
     *
     * @param product
     * @return List containing the updated product.
     * @throws ResourceNotFoundException
     */
    public List<Product> updateProduct(
        final Product product) throws ResourceNotFoundException {
        if (productRepository.existsByID(product.getProductID())) {
            return productRepository.updateProduct(product);
        } else {
            throw new ResourceNotFoundException("Product ID not found");
        }
    }

    /**
     * Creates new product given product info and returns new product.
     * Throws Exception if input is invalid.
     *
     * @param product
     * @return product
     * @throws Exception
     */
    public Product addNewProduct(final Product product) throws Exception {
        if (productRepository.existsByID(product.getProductID())) {
            throw new Exception(
                "product ID already exists - must use unique productID");
        } else {
            try {
                checkValidInput(product);
                return productRepository.addProduct(product);
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    /**
     * Returns all products.
     *
     * @return list of product
     */
    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    /**
     * Deletes product given productId.
     * Throws ResourceNotFoundException productId doesn't exist.
     *
     * @param productId
     * @throws ResourceNotFoundException
     */
    public void deleteProductById(
        final String productId) throws ResourceNotFoundException {
        if (productRepository.existsByID(productId)) {
            productRepository.deleteProductByID(productId);
        } else {
            throw new ResourceNotFoundException("Product ID not found");
        }
    }

    /**
     * Checks that all input for product info is valid.
     * All info cannot be null, price must be a float,
     * and quantity must be an integer.
     * Throws Exception if any input is invalid.
     *
     * @param product
     * @throws Exception
     */
    public void checkValidInput(final Product product) throws Exception {
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
