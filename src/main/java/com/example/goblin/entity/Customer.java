package com.example.goblin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Customer {
    // getters & setters
    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;
    private String phone;
    private String city;

    @JsonProperty("signup_date")
    private String signupDate;

    // default constructor for Jackson
    public Customer() {}

    // all-args constructor (optional)
    public Customer(String customerId, String firstName, String lastName,
                    String email, String phone, String city, String signupDate) {
        this.customerId  = customerId;
        this.firstName   = firstName;
        this.lastName    = lastName;
        this.email       = email;
        this.phone       = phone;
        this.city        = city;
        this.signupDate  = signupDate;
    }

}
