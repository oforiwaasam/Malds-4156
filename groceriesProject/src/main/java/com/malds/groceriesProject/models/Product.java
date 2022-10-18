package groceriesProject.models;

import java.util.Hashtable;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "product")
public class Product {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @JsonIgnore
    private Integer productID;

    @Column(name = "vendor_id")
    @JsonProperty("vendor_id")
    private Integer vendorID;

    @Column(name = "price")
    @JsonProperty("price")
    private double price;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private Integer quantity;

    @Column(name = "additional_features")
    @JsonProperty("additional_features")
    private Hashtable<String, String> additionalFeatures = new Hashtable<>();

    public Product() {
        this.productID = null;
        this.vendorID = null;
        this.price = null;
        this.quantity = null;
        this.additionalFeatures = null;
    }
    
    public Product(Integer productID,
                  Integer vendorID,
                  double price,
                  Integer quantity,
                  Hashtable<String, String> additionalFeatures) {
        this.productID = productID;
        this.vendorID = vendorID;
        this.price = price;
        this.quantity = quantity;
        this.additionalFeatures = additionalFeatures;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Hashtable<String, String> getAdditionalFeatures() {
        return additionalFeatures;
    }

    public void setAdditionalFeatures(Hashtable<String, String> additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
    }

    @Override
    public String toString() {
        return "\nProduct { " +
                "\n\t productID=" + productID +
                ",\n\t vendorID=" + vendorID +
                ",\n\t price='" + price + '\'' +
                ",\n\t quantity='" + quantity + '\'' +
                '\'' +
                "\n\t additionalFeatures='" + additionalFeatures + 
                '\'' +
                '}';
    }
}
