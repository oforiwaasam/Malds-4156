package com.malds.groceriesProject.services;

import java.util.List;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Vendor;
import com.malds.groceriesProject.repositories.VendorRepository;

@Service
public class VendorService {
    /**
     * VendorService. Carries out the operations
     * and interacts with the persistence layer, vendor
     * repository, to create, read, update, and delete from the database.
     */

    @Autowired
    private VendorRepository vendorRepo;
    /**
     * max length for email.
     */
    public static final int EMAIL_UPPER_LENGTH = 320;
    /**
     * max length for company name.
     */
    public static final int COMPANY_NAME_UPPER_LENGTH = 128;
    /**
     * max length for industry.
     */
    public static final int INDUSTRY_UPPER_LENGTH = 128;
    /**
     * max length for zipcode.
     */
    public static final int ZIPCODE_UPPER_LENGTH = 10;

    /**
     * VendorService Constructor.
     * @param vendorRepository
     */
    public VendorService(final VendorRepository vendorRepository) {
        this.vendorRepo = vendorRepository;
    }

    /**
     * Saves vendor into Vendor table and returns saved vendor in a list.
     * Throws Exception if vendorID already exists.
     * @param vendor
     * @return List containing the saved vendor.
     * @throws Exception
     */
    public List<Vendor> saveVendor(final Vendor vendor) throws Exception {
        if (vendorRepo.existsByID(vendor.getVendorID())) {
            throw new Exception("vendor ID already exists"
            + " - must use unique vendorID");
        }
        return vendorRepo.saveVendor(vendor);
    }

    /**
     * Given vendorID, searches for vendor
     * with vendorID and return vendor. Throws ResourceNotFoundException if
     *  vendor with vendorID does not exist.
     * @param vendorID
     * @return List containing Vendor object with specified vendorID
     * @throws ResourceNotFoundException
     */
    public List<Vendor> getVendorByID(final String vendorID)
    throws ResourceNotFoundException {
        if (vendorRepo.existsByID(vendorID)) {
            return vendorRepo.getVendorByID(vendorID);
        } else {
            throw new ResourceNotFoundException("Vendor ID not found");
        }
    }

    /**
     * Deletes vendor by vendorID provided.
     * Throws ResourceNotFoundException if vendor with vendorID does not exist.
     * @param vendorID
     * @throws ResourceNotFoundException
     */
    public void deleteVendorByID(final String vendorID)
    throws ResourceNotFoundException {
        if (vendorRepo.existsByID(vendorID)) {
            vendorRepo.deleteVendorByID(vendorID);
        } else {
            throw new ResourceNotFoundException("Vendor ID not found");
        }
    }

    /**
     * Updates existing vendor info and returns updated vendor in a list.
     * Throws ResourceNotFoundException if vendor does not exist.
     * @param vendor
     * @return List containing the updated Vendor object
     * @throws ResourceNotFoundException
     */
    public List<Vendor> updateVendor(final Vendor vendor)
    throws ResourceNotFoundException {
        if (vendorRepo.existsByID(vendor.getVendorID())) {
            return vendorRepo.updateVendor(vendor);
        } else {
            throw new ResourceNotFoundException("Vendor ID not found");
        }
    }

    /**
     * Finds and returns all existing vendors in dynamoDB Vendor table.
     * @return List of all vendors that exists
     */
    public List<Vendor> findAll() {
        return vendorRepo.findAll();
    }

    /**
     * Checks whether email is of the valid format
     * and returns true if valid, otherwise false.
     * @param email
     * @return True if email is valid, False otherwise
     */
    public boolean isValidEmail(final String email) {
        return new EmailValidator().isValid(email, null);
    }

    /**
     * Checks whether inputted values by vendors are valid,
     * is not blank, and is of accepted lengths.
     * Throws Exception if inputs are invalid.
     * @param vendor
     * @throws Exception
     */
    public void checkValidInput(final Vendor vendor) throws Exception {
        if (vendor.getVendorID() == null || vendor.getEmail() == null
        || vendor.getCompanyName() == null || vendor.getIndustry() == null
        || vendor.getZipcode() == null) {
            throw new Exception("Value cannot be null");
        }
        if (!isValidEmail(vendor.getEmail())) {
            throw new Exception("Email is invalid");
        } else if (vendor.getEmail().isBlank()
        || vendor.getEmail().length() > EMAIL_UPPER_LENGTH) {
            throw new Exception("Email must not be blank"
            + " or longer than 320 chars");
        } else if (vendor.getCompanyName().isBlank()
        || vendor.getCompanyName().length() > COMPANY_NAME_UPPER_LENGTH) {
            throw new Exception("Company Name must not be blank"
            + " or longer than 128 chars");
        } else if (vendor.getIndustry().isBlank()
                || vendor.getIndustry().length() > INDUSTRY_UPPER_LENGTH) {
            throw new Exception("Industry must not be blank"
            + " or longer than 128 chars");
        } else if (vendor.getZipcode().isBlank()
                || vendor.getZipcode().length() > ZIPCODE_UPPER_LENGTH) {
            throw new Exception("Zipcode must not be blank"
            + " or longer than 10 chars");
        }
    }

}
