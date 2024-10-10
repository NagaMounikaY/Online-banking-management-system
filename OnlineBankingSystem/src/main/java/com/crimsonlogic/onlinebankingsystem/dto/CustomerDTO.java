package com.crimsonlogic.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String customerId; // Unique identifier for the customer
    private String customerName; // Name of the customer
    private String customerMobileNumber; // Mobile number of the customer
    private String customerGender; // Gender of the customer
    private int customerAge; // Age of the customer
    private String customerStreet; // Street address of the customer
    private String customerCity; // City where the customer resides
    private int customerPincode; // Postal code of the customer's address
    private String userEmail; // Email address of the customer
    private String userPassword; // Password for the customer's account
    
    private String bankName; // Name of the bank associated with the customer
    
    private BankUserDTO bankUser; // Include BankUserDTO for email and password
}
