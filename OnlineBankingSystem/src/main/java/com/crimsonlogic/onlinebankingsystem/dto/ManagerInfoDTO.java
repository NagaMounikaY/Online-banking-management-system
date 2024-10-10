package com.crimsonlogic.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerInfoDTO {
    private String userId; // Unique identifier for the manager
    private String userName; // Name of the manager
    private String userMobileNumber; // Mobile number of the manager
    private String userGender; // Gender of the manager
    private int userAge; // Age of the manager
    private String userStreet; // Street address of the manager
    private String userCity; // City where the manager resides
    private int userPincode; // Postal code of the manager's address
    
    private String bankNames; // Names of the banks associated with the manager
    private String userEmail; // Email address of the manager
    private String userPassword; // Password for the manager's account
    
    private BankUserDTO bankUser; // Include BankUserDTO for email and password
}
