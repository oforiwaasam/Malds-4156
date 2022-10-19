package groceriesProject.models;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "vendor")
public class Vendor {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    @JsonIgnore
    private Integer vendorID;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @Column(name = "company_name")
    @JsonProperty("company_name")
    private String companyName;

    @Column(name = "industry")
    @JsonProperty("industry")
    private String industry;

    @Column(name = "zipcode")
    @JsonProperty("zipcode")
    private String zipcode;

    public Vendor() {
        this.vendorID = null;
        this.email = null;
        this.companyName = null;
        this.industry = null;
        this.zipcode = null;
    }
    
    public Vendor(Integer vendorID,
                  String email,
                  String companyName,
                  String industry,
                  String zipcode) {
        this.vendorID = vendorID;
        this.email = email;
        this.companyName = companyName;
        this.industry = industry;
        this.zipcode = zipcode;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "\nVendor { " +
                "\n\t vendorID=" + vendorID +
                ",\n\t email=" + email +
                ",\n\t companyName='" + companyName + '\'' +
                ",\n\t industry='" + industry + '\'' +
                '\'' +
                "\n\t zipcode='" + zipcode + 
                '\'' +
                '}';
    }
}
