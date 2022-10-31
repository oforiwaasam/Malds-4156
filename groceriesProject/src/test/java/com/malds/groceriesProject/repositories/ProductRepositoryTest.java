
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
public class ProductRepositoryTest {
    
    @Autowired
    ProductService productService;

    @MockBean
    private ProductRepository productRepo;

    @Test
    public void testAddProduct() throws Exception{

        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct");
        product.setVendorID("54321");
        product.setPrice("4.19");
        product.setQuantity("1");
        
        Mockito.when(productRepo.addProduct(product)).thenReturn(product);
        
        Assertions.assertEquals(productService.addNewProduct(product).getProductID(), "123456");
    }

    //Duplicate Add Product
    @Test
    public void testDupliicateAddProductID() throws Exception{

        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct");
        product.setVendorID("54321");
        product.setPrice("4.19");
        product.setQuantity("1");
        
        Mockito.when(productRepo.existsByID("123456")).thenReturn(true);
        Throwable exception = Assertions.assertThrows(Exception.class, ()->{productService.addNewProduct(product);} );

        Assertions.assertEquals("product ID already exists - must use unique productID", exception.getMessage());
    }

    @Test
    public void testUpdateProduct() throws Exception{

        Product newProduct = new Product();
        newProduct.setProductID("123456");
        newProduct.setProductName("newTestProduct");
        newProduct.setVendorID("54321");
        newProduct.setPrice("6.90");
        newProduct.setQuantity("1");
        
        Mockito.when(productRepo.existsByID("123456")).thenReturn(true);
        Mockito.when(productRepo.updateProduct(newProduct)).thenReturn(List.of(newProduct));
        
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getProductID(), "123456");
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getProductName(), "newTestProduct");
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getVendorID(), "54321");
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getPrice(), "6.90");
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getQuantity(), "1");

    }

    //No ProductID Update
    @Test
    public void testNoProductIDUpdateProduct() throws Exception{
        Product newProduct = new Product();
        newProduct.setProductID("123456");
        newProduct.setProductName("newTestProduct");
        newProduct.setVendorID("54321");
        newProduct.setPrice("6.90");
        newProduct.setQuantity("1");

        Throwable exception = Assertions.assertThrows(Exception.class, ()->{productService.updateProduct(newProduct);} );

        Assertions.assertEquals("Product ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)", exception.getMessage());
    }

    @Test
    public void testFindProductById() throws Exception{

        Product returnProduct = new Product();
        returnProduct.setProductID("123456");
        returnProduct.setProductName("TestProduct");
        returnProduct.setVendorID("54321");
        returnProduct.setPrice("4.19");
        returnProduct.setQuantity("1");
        
        Mockito.when(productRepo.existsByID("123456")).thenReturn(true);
        Mockito.when(productRepo.findProductById("123456")).thenReturn(List.of(returnProduct));
    
        Assertions.assertEquals(productService.getProductByID("123456"), List.of(returnProduct));
    }

    //No ProductID Find
    @Test
    public void testNoProductIDFindProductByID() throws Exception{

        Throwable exception = Assertions.assertThrows(Exception.class, ()->{productService.getProductByID("123456");} );

        Assertions.assertEquals("Product ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)", exception.getMessage());
    }

    @Test
    public void testFindProductByName() throws Exception{

        Product returnProduct = new Product();
        returnProduct.setProductID("123456");
        returnProduct.setProductName("TestProduct");
        returnProduct.setVendorID("54321");
        returnProduct.setPrice("4.19");
        returnProduct.setQuantity("1");
        
        Mockito.when(productRepo.existsByName("TestProduct")).thenReturn(true);
        Mockito.when(productRepo.findProductByName("TestProduct")).thenReturn(List.of(returnProduct));

        Assertions.assertEquals(productService.getProductByName("TestProduct"), List.of(returnProduct));
    }

    //No ProductName Find
    @Test
    public void testNoProductNameFindProductByName() throws Exception{

        Throwable exception = Assertions.assertThrows(Exception.class, ()->{productService.getProductByName("FakeTestProduct");} );

        Assertions.assertEquals("Product Name not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)", exception.getMessage());
    }

    @Test
    public void testDeleteProductById() throws Exception{
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        
        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setProductName("TestProduct2");
        product2.setVendorID("98765");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        
        Mockito.when(productRepo.existsByID("123456")).thenReturn(true);
        productService.deleteProductById("123456");
        Mockito.verify(productRepo).deleteProductByID("123456");
    }

    //No ProductID Delete
    @Test
    public void testNoProductIDDeleteProductById() throws Exception{

        Throwable exception = Assertions.assertThrows(Exception.class, ()->{productService.deleteProductById("123456");} );

        Assertions.assertEquals("Product ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)", exception.getMessage());
    }

    @Test
    public void testFindAllProducts() throws Exception{
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        
        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setProductName("TestProduct2");
        product2.setVendorID("98765");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        
        Mockito.when(productRepo.findAllProducts()).thenReturn(List.of(product1,product2));
        Assertions.assertEquals(productService.getAllProducts().size(), 2);
    }


    //Invalid Input Tests

    @Test
    public void testInvalidPrice() throws Exception{
        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct1");
        product.setVendorID("54321");
        product.setPrice("price");
        product.setQuantity("1");
        
        Throwable exception = Assertions.assertThrows(Exception.class, ()->{productService.addNewProduct(product);} );

        Assertions.assertEquals("java.lang.Exception: Price is invalid", exception.getMessage());
    }

    @Test
    public void testInvalidQuantity() throws Exception{
        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct1");
        product.setVendorID("54321");
        product.setPrice("4.23");
        product.setQuantity("number");

        Throwable exception = Assertions.assertThrows(Exception.class, ()->{productService.addNewProduct(product);} );
        
        Assertions.assertEquals("java.lang.Exception: Quantity is invalid", exception.getMessage());

    }
}
