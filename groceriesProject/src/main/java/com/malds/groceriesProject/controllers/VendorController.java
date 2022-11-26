package com.malds.groceriesProject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Vendor;
import com.malds.groceriesProject.services.VendorService;

@RestController
public class VendorController extends BaseController {
    /**
     * Vendor Controller.
     * Processes the requests received by interacting with the vendor service
     * layer.
     */
    @Autowired
    private VendorService vendorService;

    /**
     * Saves new vendor into the Vendor table in dynamoDB
     * and returns the saved vendor. Throws
     * Exception if vendorID alredy exists or invalid inputs.
     * @param vendor
     * @return List containing the saved vendor object
     * @throws Exception
     */
    @PostMapping("/vendors")
    public List<Vendor> saveVendor(@RequestBody final Vendor vendor)
    throws Exception {
        try {
            vendorService.checkValidInput(vendor);
            return vendorService.saveVendor(vendor);
        } catch (Exception e) {
            throw new Exception(
                    "ERROR: check input values;"
                    + " vendor ID must not already exist in DB");
        }
    }

    /**
     * given vendorID input, searches the Vendor table in dynamoDB
     * and returns the vendor with vendorID.
     * Throws ResourceNotFoundException if vendorID does not exist.
     * @param vendorID
     * @return List containing the vendor with specified vendorID
     * @throws ResourceNotFoundException
     */
    @GetMapping("/vendors/{id}")
    public List<Vendor> getVendorByID(@PathVariable("id") final String vendorID)
            throws ResourceNotFoundException {
        try {
            return vendorService.getVendorByID(vendorID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check vendorID value");
        }
    }

    /**
     * Deletes vendor in the Vendor table in dynamoDB
     * Throws ResourceNotFoundException if vendorID
     * does not exist.
     * @param vendorID
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/vendors/{id}")
    public void deleteVendorByID(@PathVariable("id") final String vendorID)
            throws ResourceNotFoundException {
        try {
            vendorService.deleteVendorByID(vendorID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check vendorID value");
        }
    }

    /**
     * Updates existing vendor in the Vendor table in dynamoDB
     * and returns the updated vendor in a list.
     * @param vendor
     * @return List containing the updated vendor object
     * @throws Exception
     */
    @PutMapping("/vendors/{id}")
    public List<Vendor> updateVendor(@RequestBody final Vendor vendor)
    throws Exception {
        try {
            vendorService.checkValidInput(vendor);
            return vendorService.updateVendor(vendor);
        } catch (Exception e) {
            throw new Exception("ERROR: check input values;"
            + " be sure to include vendorID");
        }
    }
}
