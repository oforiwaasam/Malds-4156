package com.malds.groceriesProject.controllers;

import static org.mockito.Mockito.when;
import java.nio.charset.Charset;
import java.util.List;
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

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void testGetById_good() throws Exception {
        Product product1 = new Product();
        product1.setProductID("123456");
        product1.setProductName("TestProduct1");
        product1.setVendorID("54321");
        product1.setPrice("4.19");
        product1.setQuantity("1");

        when(productService.getProductByID("123456")).thenReturn(List.of(product1));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/get_product_by_id/123456"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

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

        when(productService.getProductByID("123456")).thenReturn(List.of(product1));
        when(productService.getProductByID("123454")).thenThrow(new ResourceNotFoundException("Exception:Product ID not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/get_product_by_id/123454"))
        .andExpect(MockMvcResultMatchers.status().isOk())
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
        when(productService.updateProduct(product1)).thenReturn(List.of(product2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/products/123456").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
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

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(product3);

        when(productService.updateProduct(product3)).thenThrow(new ResourceNotFoundException("Exception:Product ID not found"));


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/products/123454").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        //.andExpect(MockMvcResultMatchers.jsonPath(“$.productName”).value("TestProduct1"));

        Mockito.verify(productService).updateProduct(product3);
        Assert.assertTrue(content.contains("Exception"));}

}