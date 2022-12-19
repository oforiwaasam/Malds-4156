
package com.malds.groceriesProject.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.services.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductService productService;

    @MockBean
    private ProductRepository productRepo;

    /* 
    @MockBean
    private VendorRepository vendorRepo;
    */

    @Test
    public void testAddProduct() throws Exception {

        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct");
        product.setVendorID("54321");
        product.setPrice("4.19");
        product.setQuantity("1");
        product.setIndustry("Grocery");

        Mockito.when(productRepo.addProduct(product)).thenReturn(product);

        Assertions.assertEquals(productService.addNewProduct(product).getProductID(), "123456");
    }

    // Duplicate Add Product
    @Test
    public void testDupliicateAddProductID() throws Exception {

        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct");
        product.setVendorID("54321");
        product.setPrice("4.19");
        product.setQuantity("1");
        product.setIndustry("Grocery");

        Mockito.when(productRepo.existsByID("123456")).thenReturn(true);
        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            productService.addNewProduct(product);
        });

        Assertions.assertEquals("product ID already exists - must use unique productID",
                exception.getMessage());
    }

    /*
    @Test
    public void testAddProductIDVendorIDDoesNotExist() throws Exception {

        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct");
        product.setVendorID("54321");
        product.setPrice("4.19");
        product.setQuantity("1");

        Mockito.when(productRepo.existsByID("123456")).thenReturn(false);
        Mockito.when(vendorRepo.existsByID("54321")).thenReturn(false);
        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            productService.addNewProduct(product);
        });

        Assertions.assertEquals("java.lang.Exception: VendorID does not exist",
                exception.getMessage());
    }
    */
    
    @Test
    public void testUpdateProduct() throws Exception {

        Product newProduct = new Product();
        newProduct.setProductID("123456");
        newProduct.setProductName("newTestProduct");
        newProduct.setVendorID("54321");
        newProduct.setPrice("6.90");
        newProduct.setQuantity("1");
        newProduct.setIndustry("Grocery");

        Mockito.when(productRepo.existsByID("123456")).thenReturn(true);
        Mockito.when(productRepo.updateProduct(newProduct)).thenReturn(List.of(newProduct));

        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getProductID(),
                "123456");
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getProductName(),
                "newTestProduct");
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getVendorID(),
                "54321");
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getPrice(), "6.90");
        Assertions.assertEquals(productService.updateProduct(newProduct).get(0).getQuantity(), "1");

    }

    // No ProductID Update
    @Test
    public void testNoProductIDUpdateProduct() throws Exception {
        Product newProduct = new Product();
        newProduct.setProductID("123456");
        newProduct.setProductName("newTestProduct");
        newProduct.setVendorID("54321");
        newProduct.setPrice("6.90");
        newProduct.setQuantity("1");
        newProduct.setIndustry("Grocery");

        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            productService.updateProduct(newProduct);
        });

        Assertions.assertEquals(
                "Product ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testFindProductById() throws Exception {

        Product returnProduct = new Product();
        returnProduct.setProductID("123456");
        returnProduct.setProductName("TestProduct");
        returnProduct.setVendorID("54321");
        returnProduct.setPrice("4.19");
        returnProduct.setQuantity("1");
        returnProduct.setIndustry("Grocery");

        Mockito.when(productRepo.existsByID("123456")).thenReturn(true);
        Mockito.when(productRepo.findProductById("123456")).thenReturn(List.of(returnProduct));

        Assertions.assertEquals(productService.getProductByID("123456"), List.of(returnProduct));
    }

    // No ProductID Find
    @Test
    public void testNoProductIDFindProductByID() throws Exception {

        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            productService.getProductByID("123456");
        });

        Assertions.assertEquals(
                "Product ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testFindProductByName() throws Exception {

        Product returnProduct = new Product();
        returnProduct.setProductID("123456");
        returnProduct.setProductName("TestProduct");
        returnProduct.setVendorID("54321");
        returnProduct.setPrice("4.19");
        returnProduct.setQuantity("1");
        returnProduct.setIndustry("Grocery");

        Product returnProduct1 = new Product();
        returnProduct1.setProductID("3432");
        returnProduct1.setProductName("TestProduct");
        returnProduct1.setVendorID("54321");
        returnProduct1.setPrice("4.59");
        returnProduct1.setQuantity("1");
        returnProduct1.setIndustry("Grocery");


        Mockito.when(productRepo.existsByName("TestProduct")).thenReturn(true);
        Mockito.when(productRepo.findProductByIndustryByName("Grocery","TestProduct"))
                .thenReturn(List.of(returnProduct, returnProduct1));

        Assertions.assertEquals(productService.getProductByIndustryByName("Grocery","TestProduct"),
                List.of(returnProduct, returnProduct1));
    }

    @Test
    public void testFindProductByNameNotExist() throws Exception {

        Mockito.when(productRepo.existsByName("TestProduct")).thenReturn(false);
        Mockito.when(productRepo.findProductByIndustryByName("Grocery","TestProduct"))
                .thenReturn(new ArrayList<Product>());

        Assertions.assertEquals(productService.getProductByIndustryByName("Grocery","TestProduct"), new ArrayList<Product>());
    }

    @Test
    public void testDeleteProductById() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");


        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setProductName("TestProduct2");
        product2.setVendorID("98765");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        product2.setIndustry("Grocery");

        Mockito.when(productRepo.existsByID("123456")).thenReturn(true);
        productService.deleteProductById("123456");
        Mockito.verify(productRepo).deleteProductByID("123456");
    }

    // No ProductID Delete
    @Test
    public void testNoProductIDDeleteProductById() throws Exception {

        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            productService.deleteProductById("123456");
        });

        Assertions.assertEquals(
                "Product ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testFindAllProducts() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setProductName("TestProduct2");
        product2.setVendorID("98765");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        product2.setIndustry("Grocery");

        Mockito.when(productRepo.findAllProducts()).thenReturn(List.of(product1, product2));
        Assertions.assertEquals(productService.getAllProducts().size(), 2);
    }

    // Invalid Input Tests

    @Test
    public void testCorrectInputs() throws Exception {
        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct1");
        product.setVendorID("54321");
        product.setPrice("2.00");
        product.setQuantity("1");
        product.setIndustry("Grocery");

        productService.checkValidInput(product);
    }

    @Test
    public void testInvalidPrice() throws Exception {
        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct1");
        product.setVendorID("54321");
        product.setPrice("price");
        product.setQuantity("1");
        product.setIndustry("Grocery");

        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            productService.checkValidInput(product);
        });

        Assertions.assertEquals("Price is invalid", exception.getMessage());
    }

    @Test
    public void testInvalidQuantity() throws Exception {
        Product product = new Product();
        product.setProductID("123456");
        product.setProductName("TestProduct1");
        product.setVendorID("54321");
        product.setPrice("4.23");
        product.setQuantity("number");
        product.setIndustry("Grocery");

        Throwable exception = Assertions.assertThrows(Exception.class, () -> {
            productService.checkValidInput(product);
        });

        Assertions.assertEquals("Quantity is invalid", exception.getMessage());

    }

    @Test
    public void testNullProductName() throws Exception {
        Product product = new Product();
        product.setProductID("123456");
        product.setProductName(null);
        product.setVendorID("54321");
        product.setPrice("4.23");
        product.setQuantity("2");
        product.setIndustry("Grocery");

        Throwable exception = assertThrows(Exception.class, () -> {
            productService.checkValidInput(product);
        });
        Assertions.assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testNullVendorID() throws Exception {
        Product product = new Product();
        product.setProductID("1");
        product.setProductName("Oranges");
        product.setVendorID(null);
        product.setPrice("4.23");
        product.setQuantity("2");
        product.setIndustry("Grocery");

        Throwable exception = assertThrows(Exception.class, () -> {
            productService.checkValidInput(product);
        });
        Assertions.assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testNullPrice() throws Exception {
        Product product = new Product();
        product.setProductID("1");
        product.setProductName("Oranges");
        product.setVendorID("12345");
        product.setPrice(null);
        product.setQuantity("2");
        product.setIndustry("Grocery");

        Throwable exception = assertThrows(Exception.class, () -> {
            productService.checkValidInput(product);
        });
        Assertions.assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testNullQuantity() throws Exception {
        Product product = new Product();
        product.setProductID("1");
        product.setProductName("Oranges");
        product.setVendorID("12345");
        product.setPrice("2");
        product.setQuantity(null);
        product.setIndustry("Grocery");

        Throwable exception = assertThrows(Exception.class, () -> {
            productService.checkValidInput(product);
        });
        Assertions.assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testGetProductsByVendorID() {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setProductName("TestProduct2");
        product2.setVendorID("98765");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        product2.setIndustry("Grocery");

        Mockito.when(productRepo.findAllProducts()).thenReturn(List.of(product1, product2));
        Mockito.when(productRepo.getProductsByVendorID("98765")).thenReturn(List.of(product2));
        assertEquals(productService.getProductsByVendorID("98765").get(0), product2);
    }

    @Test
    public void testGetMultipleProductsByVendorID() {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setProductName("TestProduct2");
        product2.setVendorID("54321");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        product2.setIndustry("Grocery");

        Product product3 = new Product();
        product3.setProductID("24680");
        product3.setProductName("TestProduct3");
        product3.setVendorID("54321");
        product3.setPrice("4.33");
        product3.setQuantity("3");
        product3.setIndustry("Grocery");

        Mockito.when(productRepo.findAllProducts()).thenReturn(List.of(product1, product2, product3));
        Mockito.when(productRepo.getProductsByVendorID("54321")).thenReturn(List.of(product1, product2, product3));
        assertEquals(productService.getProductsByVendorID("54321"), (List.of(product1, product2, product3)));
    }


    @Test
    public void testGetProductsByInvalidVendorID() {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        Product product2 = new Product();
        product2.setProductID("56789");
        product2.setProductName("TestProduct2");
        product2.setVendorID("98765");
        product2.setPrice("2.29");
        product2.setQuantity("1");
        product2.setIndustry("Grocery");

        Mockito.when(productRepo.findAllProducts()).thenReturn(List.of(product1, product2));
        Mockito.when(productRepo.getProductsByVendorID("98")).thenReturn(new ArrayList<Product>());
        assertEquals(productService.getProductsByVendorID("98"), new ArrayList<Product>());
    }
}
