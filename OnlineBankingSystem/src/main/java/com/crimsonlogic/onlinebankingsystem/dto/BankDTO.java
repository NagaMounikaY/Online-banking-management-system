package com.crimsonlogic.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {
    private String bankId;               // Unique identifier for the bank
    private String bankName;             // Name of the bank
    private int bankCode;                // Code of the bank
    private String bankAddress;           // Address of the bank
    private String bankEmail;             // Email of the bank
    private String bankContactNumber;     // Contact number of the bank
    private String bankCountry;           // Country where the bank is located
    private String userName;                // ID of the associated UserInfo
}
