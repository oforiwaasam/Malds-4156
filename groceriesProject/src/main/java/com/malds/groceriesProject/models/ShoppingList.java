package groceriesProject.models;

import java.util.Hashtable;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "shoppingList")
public class ShoppingList {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_list_id")
    @JsonIgnore
    private Integer shoppingListID;

    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Integer clientID;

    @Column(name = "product_id")
    @JsonProperty("product_id")
    private Integer productID;

    """
    @Column(name = "price")
    @JsonProperty("price")
    private double price;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private Integer quantity;

    @Column(name = "additional_features")
    @JsonProperty("additional_features")
    private Hashtable<String, String> additionalFeatures = new Hashtable<>();
      

    """
    
    public ShoppingList() {
        this.shoppingListID = null;
        this.clientID = null;
        this.productID = null;
    }
    
    public Product(Integer shoppingListID,
                  Integer clientID,
                  Integer productID) {
        this.shoppingListID = shoppingListID;
        this.clientID = clientID;
        this.productID = productID;
    }

    public Integer getShoppingListID() {
        return shoppingListID;
    }

    public void setShoppingListID(Integer shoppingListID) {
        this.shoppingListID = shoppingListID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "\nShoppingList { " +
                "\n\t shoppingListID=" + shoppingListID +
                ",\n\t clientID=" + clientID +
                ",\n\t productID='" + productID + '\'' +
                '}';
    }
}
