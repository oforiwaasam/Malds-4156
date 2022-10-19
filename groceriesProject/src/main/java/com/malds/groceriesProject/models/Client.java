package com.malds.groceriesProject.models;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name = "client")
public class Client {

    // data members
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    @JsonIgnore
    private Integer clientID;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "gender")
    @JsonProperty("gender")
    private Enum gender;

    @Column(name = "date_of_birth")
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "zipcode")
    @JsonProperty("zipcode")
    private String zipcode;

    public Client() {
        this.clientID = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.dateOfBirth = null;
        this.zipcode = null;
    }
    
    public Client(Integer clientID,
                  String email,
                  String firstName,
                  String lastName,
                  Enum gender,
                  LocalDate dateOfBirth,
                  String zipcode) {
        this.clientID = clientID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.zipcode = zipcode;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Enum getGender() {
        return gender;
    }

    public void setGender(Enum gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "\nClient { " +
                "\n\t clientID=" + clientID +
                ",\n\t email=" + email +
                ",\n\t firstName='" + firstName + '\'' +
                ",\n\t lastName='" + lastName + '\'' +
                "\n\t gender='" + gender + 
                '\'' +
                "\n\t dateOfBirth='" + dateOfBirth + 
                '\'' +
                "\n\t zipcode='" + zipcode + 
                '\'' +
                '}';
    }
}
