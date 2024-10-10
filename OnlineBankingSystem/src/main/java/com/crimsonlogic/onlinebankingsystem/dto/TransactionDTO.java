package com.crimsonlogic.onlinebankingsystem.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    
    private String transactionId; // Unique identifier for the transaction
    private float balance; // Account balance after the transaction
    private String transactionType; // Type of transaction (e.g., deposit, withdrawal, transfer)
    private String description; // Description of the transaction
    private LocalDate transactionDate; // Date when the transaction occurred
    private String fromAccountId; // ID of the account from which funds are transferred (for transfers)
    private String toAccountId;   // ID of the account to which funds are transferred (for transfers)
    private String fromBank;       // Name of the bank from which funds are transferred (for transfers)
    private String toBank;         // Name of the bank to which funds are transferred (for transfers)
}
