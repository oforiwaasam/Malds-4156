
package com.malds.groceriesProject.repositories;

import java.util.List;

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
    public void testAddProduct() throws Exception{

        Product product = new Product();
        product.setProductID("123456");
        product.setVendorID("54321");
        product.setPrice("4.19");
        product.setQuantity("1");
        
        Mockito.when(productRepo.addProduct(product)).thenReturn(product);
        
        Assertions.assertEquals(productService.addNewProduct(product).getProductID(), "123456");
    }

    @Test
    public void testFindProductById() throws Exception{

        Product returnProduct = new Product();
        returnProduct.setProductID("123456");
        returnProduct.setVendorID("54321");
        returnProduct.setPrice("4.19");
        returnProduct.setQuantity("1");
        
        Mockito.when(productRepo.findProductById("123456")).thenReturn(returnProduct);
    
        Assertions.assertEquals(productService.getProductByID("123456"), returnProduct);
    }

    @Test
    public void testDeleteProductById() throws Exception{
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        
        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setVendorID("98765");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        
        productService.deleteProductById("123456");
        Mockito.verify(productRepo).deleteProductByID("123456");
    }

    @Test
    public void testFindAllProducts() throws Exception{
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        
        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setVendorID("98765");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        
        Mockito.when(productRepo.findAllProducts()).thenReturn(List.of(product1,product2));
        Assertions.assertEquals(productService.getAllProducts().size(), 2);
    }

}
