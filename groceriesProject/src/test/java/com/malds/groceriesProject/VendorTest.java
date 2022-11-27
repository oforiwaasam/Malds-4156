package com.malds.groceriesProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Vendor;
import com.malds.groceriesProject.repositories.VendorRepository;
import com.malds.groceriesProject.services.VendorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VendorTest {
    
    @Autowired
    private VendorService vendorService;

    @MockBean
    private VendorRepository vendorRepo;

    @Test
    public void testGetVendorByID() throws Exception {

        final String EXPECTED_VENDOR_ID = "1";
        final String EXPECTED_EMAIL = "groceries_test@trader_joes.com";
        final String EXPECTED_COMPANY_NAME = "Trader Joe's";
        final String EXPECTED_INDUSTRY = "Grocery";
        final String EXPECTED_ZIPCODE = "11101";

        // create new vendor to be saved
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("groceries_test@trader_joes.com");
        vendor.setCompanyName("Trader Joe's");
        vendor.setIndustry("Grocery");
        vendor.setZipcode("11101");

        Mockito.when(vendorRepo.existsByID("1")).thenReturn(true);
        Mockito.when(vendorRepo.getVendorByID("1")).thenReturn(List.of(vendor));

        assertEquals(vendorService.getVendorByID("1").get(0).getVendorID(), EXPECTED_VENDOR_ID);
        assertEquals(vendorService.getVendorByID("1").get(0).getEmail(), EXPECTED_EMAIL);
        assertEquals(vendorService.getVendorByID("1").get(0).getCompanyName(), EXPECTED_COMPANY_NAME);
        assertEquals(vendorService.getVendorByID("1").get(0).getIndustry(), EXPECTED_INDUSTRY);
        assertEquals(vendorService.getVendorByID("1").get(0).getZipcode(), EXPECTED_ZIPCODE);
    }

    @Test
    public void testGetVendorByIDNotFound() throws Exception {

        final String EXPECTED_EXCEPTION = "Vendor ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";

        // create new vendor to be saved
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("groceries_test@trader_joes.com");
        vendor.setCompanyName("Trader Joe's");
        vendor.setIndustry("Grocery");
        vendor.setZipcode("11101");

        Mockito.when(vendorRepo.existsByID("1")).thenReturn(false);

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            vendorService.getVendorByID("1");
        });
        assertEquals(EXPECTED_EXCEPTION, exception.getMessage()); 
    }

    @Test
    public void testSaveVendor() throws Exception {

        final String EXPECTED_VENDOR_ID = "1";
        final String EXPECTED_EMAIL = "groceries_test@trader_joes.com";
        final String EXPECTED_COMPANY_NAME = "Trader Joe's";
        final String EXPECTED_INDUSTRY = "Grocery";
        final String EXPECTED_ZIPCODE = "11101";

        // create new vendor to be saved
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("groceries_test@trader_joes.com");
        vendor.setCompanyName("Trader Joe's");
        vendor.setIndustry("Grocery");
        vendor.setZipcode("11101");

        Mockito.when(vendorRepo.existsByID("1")).thenReturn(false);
        Mockito.when(vendorRepo.saveVendor(vendor)).thenReturn(List.of(vendor));

        assertEquals(vendorService.saveVendor(vendor).get(0).getVendorID(), EXPECTED_VENDOR_ID);
        assertEquals(vendorService.saveVendor(vendor).get(0).getEmail(), EXPECTED_EMAIL);
        assertEquals(vendorService.saveVendor(vendor).get(0).getCompanyName(), EXPECTED_COMPANY_NAME);
        assertEquals(vendorService.saveVendor(vendor).get(0).getIndustry(), EXPECTED_INDUSTRY);
        assertEquals(vendorService.saveVendor(vendor).get(0).getZipcode(), EXPECTED_ZIPCODE);
    }

    @Test
    public void testSaveVendorIDExists() throws Exception {

        final String EXPECTED_EXCEPTION = "vendor ID already exists - must use unique vendorID";

        // create new vendor to be saved
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("groceries_test@trader_joes.com");
        vendor.setCompanyName("Trader Joe's");
        vendor.setIndustry("Grocery");
        vendor.setZipcode("11101");

        Mockito.when(vendorRepo.existsByID("1")).thenReturn(true);

        Throwable exception = assertThrows(Exception.class, () -> {
           vendorService.saveVendor(vendor);
        });
        assertEquals(EXPECTED_EXCEPTION, exception.getMessage()); 
    }

    @Test
    public void testUpdateVendor() {

        final String EXPECTED_VENDOR_ID = "1";
        final String EXPECTED_EMAIL = "groceries_test@trader_joes.com";
        final String EXPECTED_COMPANY_NAME = "Trader Joe's";
        final String EXPECTED_INDUSTRY = "Grocery";
        final String EXPECTED_ZIPCODE = "10260";

        // initialize updated vendor
        Vendor updatedVendor = new Vendor();
        updatedVendor.setVendorID("1");
        updatedVendor.setEmail("groceries_test@trader_joes.com");
        updatedVendor.setCompanyName("Trader Joe's");
        updatedVendor.setIndustry("Grocery");
        updatedVendor.setZipcode("10260");

        Mockito.when(vendorRepo.existsByID("1")).thenReturn(true);
        Mockito.when(vendorRepo.updateVendor(updatedVendor)).thenReturn(List.of(updatedVendor));

        assertEquals(vendorService.updateVendor(updatedVendor).get(0).getVendorID(),
                EXPECTED_VENDOR_ID);
        assertEquals(vendorService.updateVendor(updatedVendor).get(0).getEmail(), EXPECTED_EMAIL);
        assertEquals(vendorService.updateVendor(updatedVendor).get(0).getCompanyName(),
                EXPECTED_COMPANY_NAME);
        assertEquals(vendorService.updateVendor(updatedVendor).get(0).getIndustry(),
                EXPECTED_INDUSTRY);
        assertEquals(vendorService.updateVendor(updatedVendor).get(0).getZipcode(),
                EXPECTED_ZIPCODE);
    }

    @Test
    public void testNoVendorIDUpdate() {
        // Initialize vendor update
        Vendor updatedVendor = new Vendor();
        updatedVendor.setVendorID("32");
        updatedVendor.setEmail("random_email@zara.com");
        updatedVendor.setCompanyName("Zara");
        updatedVendor.setIndustry("Fashion");
        updatedVendor.setZipcode("12345");

        Mockito.when(vendorRepo.existsByID("32")).thenReturn(false);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            vendorService.updateVendor(updatedVendor);
        });
        assertEquals(
                "Vendor ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testDeleteVendorByID() {
        // initialize Vendor to be deleted
        Vendor deleteVendor = new Vendor();
        deleteVendor.setVendorID("3");
        deleteVendor.setEmail("groceries_test@trader_joes.com");
        deleteVendor.setCompanyName("Trader Joe's");
        deleteVendor.setIndustry("Grocery");
        deleteVendor.setZipcode("10260");

        Mockito.when(vendorRepo.existsByID("3")).thenReturn(true);
        vendorService.deleteVendorByID("3");
    }

    @Test
    public void testNoVendorIDDelete() {

        Mockito.when(vendorRepo.existsByID("32")).thenReturn(false);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            vendorService.deleteVendorByID("32");
        });
        assertEquals(
                "Vendor ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testFindAllVendors() {
        // create new vendors to be saved
        Vendor vendor1 = new Vendor();
        vendor1.setVendorID("1");
        vendor1.setEmail("groceries_test@trader_joes.com");
        vendor1.setCompanyName("Trader Joe's");
        vendor1.setIndustry("Grocery");
        vendor1.setZipcode("11101");

        Vendor vendor2 = new Vendor();
        vendor2.setVendorID("2");
        vendor2.setEmail("groceries_test@whole_foods.com");
        vendor2.setCompanyName("Whole Foods");
        vendor2.setIndustry("Grocery");
        vendor2.setZipcode("11101");

        Vendor vendor3 = new Vendor();
        vendor3.setVendorID("3");
        vendor3.setEmail("fashion_test@zara.com");
        vendor3.setCompanyName("Zara");
        vendor3.setIndustry("Fashion");
        vendor3.setZipcode("11101");

        Mockito.when(vendorRepo.findAll()).thenReturn(List.of(vendor1, vendor2, vendor3));

        assertEquals(vendorService.findAll(), List.of(vendor1, vendor2, vendor3));
    }
    
    @Test
    public void testEmailValidity() {
        String invalid_email = "incorrect email input";
        String blank_email = "      ";
        String valid_email = "random_email@zara.com";

        assertFalse(vendorService.isValidEmail(invalid_email));
        assertFalse(vendorService.isValidEmail(blank_email));
        assertTrue(vendorService.isValidEmail(valid_email));
    }

    @Test
    public void testCheckInputInvalidEmail() {
        // initialize invalid email vendor - no @
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("random_emailzara.com");
        vendor.setCompanyName("Zara");
        vendor.setIndustry("Fashion");
        vendor.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            vendorService.checkValidInput(vendor);
        });
        assertEquals("Email is invalid", exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidCompanyName() {
        // initialize invalid name - too long
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("groceries_test@trader_joes.com");
        vendor.setCompanyName("Trader Joe'sTrader Joe'sTrader Joe's"
        + "Trader Joe'sTrader Joe'sTrader Joe'sTrader Joe'sTrader Joe's"
        + "Trader Joe'sTrader Joe'sTrader Joe'sTrader Joe'sTrader Joe's");
        vendor.setIndustry("Grocery");
        vendor.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            vendorService.checkValidInput(vendor);
        });
        assertEquals("Company Name must not be blank or longer than 128 chars",
                exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidIndustry() {
        // initialize invalid industry - null
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("random_emailzara.com");
        vendor.setCompanyName("Zara");
        vendor.setIndustry(null);
        vendor.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            vendorService.checkValidInput(vendor);
        });
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidZipcode() {
        // initialize invalid email zipcode - empty string
        Vendor vendor = new Vendor();
        vendor.setVendorID("1");
        vendor.setEmail("random_email@zara.com");
        vendor.setCompanyName("Zara");
        vendor.setIndustry("Fashion");
        vendor.setZipcode("");

        Throwable exception = assertThrows(Exception.class, () -> {
            vendorService.checkValidInput(vendor);
        });
        assertEquals("Zipcode must not be blank or longer than 10 chars",
        exception.getMessage());
      
    }
    
}
