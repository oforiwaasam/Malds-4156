package com.malds.groceriesProject.controllers;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.services.ProductService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController extends BaseController {

    /**
     * Product Controller. Processes the requests received by interacting
     * with the product service layer.
     */
    @Autowired
    private ProductService productService;

    /**
     * Adds new product into the Product table in DynamoDB
     * and returns the added product.
     * Throws Exception if productId already exists in database.
     *
     * @param product
     * @return the newly created and added product
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public Product createProduct(
            @RequestBody final Product product) throws Exception {
        try {
            productService.checkValidInput(product);
            return productService.addNewProduct(product);
        } catch (Exception e) {
            /*
            throw new Exception(
                    "ERROR: check input values;"
                    + " product ID must not already exist in DB");
            */
            throw new Exception("Error: " + e);

        }
    }

    /**
     * Updates existing product in the Products table in dynamoDB
     * and returns the updated client in a list.
     *
     * @param product
     * @return List containing the updated client
     * @throws Exception
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public List<Product> updateProduct(
            @RequestBody final Product product) throws Exception {
        try {
            productService.checkValidInput(product);
            return productService.updateProduct(product);
        } catch (Exception e) {
            /*
            String message = "ERROR: " + e
                    + "; be sure to include productID that exists";
            throw new Exception(message);
            */
            throw new Exception("Error: " + e);
        }
    }

    /**
     * searches the Product table in DynamoDB and returns the product with
     * given productId.
     * Throws ResourceNotFoundException if productId does not exist.
     *
     * @param productId
     * @return List containing the product with specified productId
     * @throws ResourceNotFoundException
     */
    @RequestMapping(path = "get_product_by_id/{id}",
            method = RequestMethod.GET)
    public List<Product> getProductByID(
            @PathVariable("id") final String productId)
            throws ResourceNotFoundException {
        List<Product> foundProduct = productService.getProductByID(productId);
        return foundProduct;
    }

    /**
     * searches the Product table in DynamoDB and returns the product with
     * given productName.
     * @param industryName
     * @param productName
     * @return List containing the product with specified productName
     * @throws ResourceNotFoundException
     */
    @RequestMapping(path = "get_product_by_name/{industry}/{name}",
            method = RequestMethod.GET)
    public List<Product> getProductByIndustryByName(
            @PathVariable("industry") final String industryName,
            @PathVariable("name") final String productName)
            throws ResourceNotFoundException {
        List<Product> foundProducts =
                productService.getProductByIndustryByName(
                        industryName, productName);
        return foundProducts;
    }

    /**
     * Searches the Product table in DynamoDB and returns
     * the products with the given vendorID.
     * @param vendorID
     * @return List containing the products with the
     * specified vendorID
     */
    @RequestMapping(path = "get_product_by_vendor_id/{id}",
            method = RequestMethod.GET)
    public List<Product> getProductsByVendorID(
            @PathVariable("id") final String vendorID) {
        List<Product> productsFromVendorID = productService
                .getProductsByVendorID(vendorID);
        return productsFromVendorID;
    }

    /**
     * Deletes product in the Product table in DynamoDB
     * Throws ResourceNotFoundException if productId does not exist.
     *
     * @param productId
     * @throws ResourceNotFoundException
     */
    @RequestMapping(path = "/{id}",
            method = RequestMethod.DELETE)
    public void deleteProductByID(@PathVariable("id") final String productId)
            throws ResourceNotFoundException {
        try {
            productService.deleteProductById(productId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(
                    "ERROR: " + e);
        }
    }
}
