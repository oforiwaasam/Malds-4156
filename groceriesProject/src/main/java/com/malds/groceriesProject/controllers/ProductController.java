package com.malds.groceriesProject.controllers;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST) 
    public Product createProduct(@RequestBody Product product) throws Exception {
        return productService.addNewProduct(product);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public List<Product> updateProduct(@RequestBody Product product) throws Exception{
        try {
            productService.checkValidInput(product);
            return productService.updateProduct(product);
        } catch (Exception e){
            String message = "ERROR: " + e + "; be sure to include productID that exists";
            throw new Exception(message);
        } 
    }

    @RequestMapping(path="get_product_by_id/{id}", method = RequestMethod.GET)
    public List<Product> getProductByID(@PathVariable("id") String productId) throws ResourceNotFoundException{
        List<Product> foundProduct = productService.getProductByID(productId);
        return foundProduct;
    }

    @RequestMapping(path="get_product_by_name/{name}", method = RequestMethod.GET)
    public List<Product> getProductByName(@PathVariable("name") String productName) throws ResourceNotFoundException{
        List<Product> foundProducts = productService.getProductByName(productName);
        return foundProducts;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteProductByID(@PathVariable("id") String productId) throws ResourceNotFoundException{
        try{
            productService.deleteProductById(productId);
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check productId value");
        }
    }
}
