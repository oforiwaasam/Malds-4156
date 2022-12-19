package com.malds.groceriesProject.controllers;

import static org.mockito.Mockito.when;
import java.nio.charset.Charset;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.malds.groceriesProject.models.Product;
import com.malds.groceriesProject.services.ProductService;
import org.junit.Assert;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    public static String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw";
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void testGetById_good() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        when(productService.getProductByID("123456")).thenReturn(List.of(product1));

        
        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/products/get_product_by_id/123456")
            .header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
        System.out.println(result.getRequest());
        System.out.println(result.getResponse());
        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).getProductByID("123456");

    }
    @Test
    public void testGetById_exception() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        when(productService.getProductByID("123456")).thenReturn(List.of(product1));
        when(productService.getProductByID("123454")).thenThrow(new ResourceNotFoundException("Exception:Product ID not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/get_product_by_id/123454")
        .header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).getProductByID("123454");
        Assert.assertTrue(content.contains("Exception"));
    }

    @Test
    public void testUpdateProduct_good() throws Exception{
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(product1);


        Product product2 = new Product();
        product2.setProductID("123456");
        product2.setProductName("TestProduct2");
        product2.setVendorID("54321");
        product2.setPrice("7.19");
        product2.setQuantity("2");
        product2.setIndustry("Grocery");
        when(productService.updateProduct(product1)).thenReturn(List.of(product2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/products/123456").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson).header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).updateProduct(product1);
    }

    @Test
    public void testUpdateProduct_bad() throws Exception{

        Product product3 = new Product();
        product3.setProductID("123454");
        product3.setProductName("TestProduct2");
        product3.setVendorID("54321");
        product3.setPrice("7.19");
        product3.setQuantity("2");
        product3.setIndustry("Grocery");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(product3);

        when(productService.updateProduct(product3)).thenThrow(new ResourceNotFoundException("Exception:Product ID not found"));


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/products/123454").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson).header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).updateProduct(product3);
        Assert.assertTrue(content.contains("Exception"));
    }

    @Test
    public void testCreateProduct_good() throws Exception{
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(product1);

        when(productService.addNewProduct(product1)).thenReturn(product1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson).header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).addNewProduct(product1);
    }

    @Test
    public void testCreateProduct_bad() throws Exception{
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(product1);

        when(productService.addNewProduct(product1)).thenThrow(new ResourceNotFoundException("Exception:Product ID already found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson).header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).addNewProduct(product1);
        Assert.assertTrue(content.contains("Exception"));
    }


    @Test
    public void testGetByVendorID_good() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        Product product2 = new Product();
        product1.setProductID("5555555");
        product1.setProductName("TestProduct2");
        product1.setVendorID("54321");
        product1.setPrice("8.50");
        product1.setQuantity("4");
        product1.setIndustry("Grocery");

        when(productService.getProductsByVendorID("54321")).thenReturn(List.of(product1,product2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/get_product_by_vendor_id/54321").header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).getProductsByVendorID("54321");
        Assert.assertTrue(content.contains("TestProduct2")); 

    }
    @Test
    public void testGetByVendorID_exception() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        when(productService.getProductsByVendorID("54321")).thenReturn(List.of(product1));
        when(productService.getProductsByVendorID("544444321")).thenThrow(new ResourceNotFoundException("Exception:Vendor ID not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/get_product_by_vendor_id/544444321").header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).getProductsByVendorID("544444321");
        Assert.assertTrue(content.contains("Exception"));
    }


    @Test
    public void testGetByName_good() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        product1.setIndustry("Grocery");

        Product product2 = new Product();
        product1.setProductID("5555555");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("8.50");
        product1.setQuantity("4");
        product1.setIndustry("Grocery");

        when(productService.getProductByIndustryByName("Grocery","TestProduct1")).thenReturn(List.of(product1,product2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/get_product_by_name/Grocery/TestProduct1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).getProductByIndustryByName("Grocery","TestProduct1");
        Assert.assertTrue(content.contains("8.50")); 

    }
    @Test
    public void testGetByName_exception() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");
        //product1.setIndustry("Grocery");

        when(productService.getProductByIndustryByName("Grocery","TestProduct1")).thenReturn(List.of(product1));
        when(productService.getProductByIndustryByName("Grocery","TestProduct2")).thenThrow(new ResourceNotFoundException("Exception:Product with Name: " +"TestProduct2"+ " not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/get_product_by_name/Grocery/TestProduct2").header("authorization", "Bearer " + token))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).getProductByIndustryByName("Grocery","TestProduct2");
        Assert.assertTrue(content.contains("Exception"));
    }
}