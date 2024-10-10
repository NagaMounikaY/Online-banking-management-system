package com.crimsonlogic.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String accountId; // Unique identifier for the account
    private float balance; // Current balance of the account
    private String accountType; // Type of account (e.g., savings, checking)
    private String status; // Current status of the account (e.g., active, inactive)
    private String customerId; // Unique identifier for the customer
    private String CustomerName; // Name of the customer associated with the account
    private String userEmail; // Email address of the customer
    private String customerMobileNumber; // Mobile number of the customer
    private String bankName; // Name of the bank where the account is held
    private int bankCode; // Code representing the bank
}
