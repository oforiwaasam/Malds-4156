
package com.malds.groceriesProject.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.services.ProductService;

@SpringBootTest
public class ProductRepositoryTests {
    
    @Autowired
    ProductService productService;

    @MockBean
    private ProductRepository productRepo;


    @Test
    public void testAddProduct(){

        Product product = new Product();
        product.setProductID("123456");
        product.setVendorID("54321");
        product.setPrice("4.19");
        product.setQuantity("1");
        
        Mockito.when(productRepo.addProduct(product)).thenReturn(product);
        
        Assertions.assertEquals(productService.addNewProduct(product).getProductID(), "123456");
    }

    @Test
    public void testFindProductById() {

        Product returnProduct = new Product();
        returnProduct.setProductID("123456");
        returnProduct.setVendorID("54321");
        returnProduct.setPrice("4.19");
        returnProduct.setQuantity("1");

        
        Mockito.when(productRepo.findProductById("123456")).thenReturn(returnProduct);
    
        // productService.addNewProduct(returnProduct);
        Assertions.assertEquals(productService.getProductByID("123456"), returnProduct);
    }



}
