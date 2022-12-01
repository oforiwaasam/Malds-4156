package com.malds.groceriesProject.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.malds.groceriesProject.models.Vendor;
import com.malds.groceriesProject.services.VendorService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VendorController.class)
public class VendorControllerTest {

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void testGetVendorByID() throws Exception {
        final String EXPECTED_RESPONSE = "[{\"vendorID\":\"1\",\"email\":\"groceries_test@trader_joes.com\",\"companyName\":\"Trader Joe's\","
        + "\"industry\":\"Grocery\",\"zipcode\":\"11101\"}]";
        // create new vendor to be saved
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("groceries_test@trader_joes.com");
        vendor.setCompanyName("Trader Joe's");
        vendor.setIndustry("Grocery");
        vendor.setZipcode("11101");

        when(vendorService.getVendorByID("1")).thenReturn(List.of(vendor));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/vendors/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(vendorService).getVendorByID("1");
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testGetVendorByIDNotFound() throws Exception {

        final String EXPECTED_RESPONSE = "ERROR: check vendorID value (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";

        when(vendorService.getVendorByID("1")).thenThrow(new ResourceNotFoundException("Vendor ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/vendors/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(vendorService).getVendorByID("1");
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testSaveVendor() throws Exception{
        final String EXPECTED_RESPONSE = "[{\"vendorID\":\"1\",\"email\":\"groceries_test@trader_joes.com\",\"companyName\":\"Trader Joe's\","
        + "\"industry\":\"Grocery\",\"zipcode\":\"11101\"}]";
        
        // create new vendor to be saved
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("groceries_test@trader_joes.com");
        vendor.setCompanyName("Trader Joe's");
        vendor.setIndustry("Grocery");
        vendor.setZipcode("11101");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(vendor);

        when(vendorService.saveVendor(vendor)).thenReturn(List.of(vendor));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendors").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(vendorService).saveVendor(vendor);
        assertEquals(EXPECTED_RESPONSE, content);
    }
 
    @Test
    public void testSaveVendorDuplicate() throws Exception{
        final String EXPECTED_RESPONSE =  "ERROR: check input values;"
        + " vendor ID must not already exist in DB";

        // create new vendor to be saved
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("groceries_test@trader_joes.com");
        vendor.setCompanyName("Trader Joe's");
        vendor.setIndustry("Grocery");
        vendor.setZipcode("11101");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(vendor);

        when(vendorService.saveVendor(vendor)).thenThrow(new ResourceNotFoundException("vendor ID already exists"
            + " - must use unique vendorID"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendors").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(vendorService).saveVendor(vendor);
        assertEquals(EXPECTED_RESPONSE, content);
    }
 
    @Test
    public void testUpdateVendor() throws Exception{
        final String EXPECTED_RESPONSE = "[{\"vendorID\":\"1\",\"email\":\"updated_email@trader_joes.com\",\"companyName\":\"Trader Joe's\","
        + "\"industry\":\"Grocery\",\"zipcode\":\"10260\"}]";
        
        // create updated vendor
        Vendor updatedVendor = new Vendor();
        updatedVendor.setVendorID("1");
        updatedVendor.setEmail("updated_email@trader_joes.com");
        updatedVendor.setCompanyName("Trader Joe's");
        updatedVendor.setIndustry("Grocery");
        updatedVendor.setZipcode("10260");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(updatedVendor);

        when(vendorService.updateVendor(updatedVendor)).thenReturn(List.of(updatedVendor));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/vendors/1").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(vendorService).updateVendor(updatedVendor);
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testUpdateVendorIDNotFound() throws Exception{

        final String EXPECTED_RESPONSE = "ERROR: check input values;"
        + " be sure to include vendorID";
        // create updated vendor
        Vendor updatedVendor = new Vendor();
        updatedVendor.setVendorID("1");
        updatedVendor.setEmail("updated_email@trader_joes.com");
        updatedVendor.setCompanyName("Trader Joe's");
        updatedVendor.setIndustry("Grocery");
        updatedVendor.setZipcode("10260");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(updatedVendor);

        when(vendorService.updateVendor(updatedVendor)).thenThrow(new ResourceNotFoundException("Vendor ID not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/vendors/1").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(vendorService).updateVendor(updatedVendor);
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testDeleteVendorByID() throws Exception {

        // create new vendor to be deleted
         Vendor updatedVendor = new Vendor();
         updatedVendor.setVendorID("1");
         updatedVendor.setEmail("updated_email@trader_joes.com");
         updatedVendor.setCompanyName("Trader Joe's");
         updatedVendor.setIndustry("Grocery");
         updatedVendor.setZipcode("10260");

        doNothing().when(vendorService).deleteVendorByID("1");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/vendors/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(vendorService).deleteVendorByID("1");
        assertEquals("", content);
    }

    @Test
    public void testDeleteVendorByIDNotFOund() throws Exception {

        final String EXPECTED_RESPONSE = "ERROR: check vendorID value (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";
         // create new vendor to be deleted
         Vendor updatedVendor = new Vendor();
         updatedVendor.setVendorID("1");
         updatedVendor.setEmail("updated_email@trader_joes.com");
         updatedVendor.setCompanyName("Trader Joe's");
         updatedVendor.setIndustry("Grocery");
         updatedVendor.setZipcode("10260");

        doThrow(new ResourceNotFoundException("Vendor ID not found")).when(vendorService).deleteVendorByID("1");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/vendors/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(vendorService).deleteVendorByID("1");
        assertEquals(EXPECTED_RESPONSE, content);
    }
}