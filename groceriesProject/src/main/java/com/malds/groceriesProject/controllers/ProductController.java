package com.malds.groceriesProject.controllers;

import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.services.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    public Product createProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }

    @RequestMapping("/{id}")
    public Product getProductByID(@PathVariable Integer productId) {
        Product foundProduct = productService.getProductByID(productId);
        return foundProduct;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteProductByID(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
    }
}
