package com.malds.groceriesProject.IntegrationTestsAssessment;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.services.ProductService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductIntegTest {
    @Autowired
    private ProductService productService;

    @Test
    public void createProductsInteg() throws Exception  {
        Product newProduct = new Product();
        for(int i = 0; i < 10; ++i){
            newProduct.setProductID("product_integ_"+i);
            newProduct.setProductName("newTestProduct_" +i);
            newProduct.setVendorID("5");
            newProduct.setPrice(String.valueOf(Math.ceil(Math.random()*10)));
            newProduct.setQuantity(String.valueOf(Math.ceil(Math.random()*100)));
            productService.addNewProduct(newProduct);
        }

        //Test that the shopping list exists
        Assert.assertEquals(10, productService.getProductsByVendorID("5").size());

    }

    @Test
    public void deleteProductInteg() throws Exception  {

        productService.deleteProductById("product_integ_7");
        //Test that the shopping list exists
        
        Assert.assertThrows(Exception.class,
                ()->{productService.getProductByID("product_integ_7");} );
        
    }
}
