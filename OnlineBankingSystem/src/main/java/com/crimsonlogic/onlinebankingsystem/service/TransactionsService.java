package com.crimsonlogic.onlinebankingsystem.service;

import java.util.List;

import com.crimsonlogic.onlinebankingsystem.dto.TransactionDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Transactions;

public interface TransactionsService {
    
    // Process a transaction for the specified account and return a status message
    String processTransaction(String accountId, TransactionDTO request);
    
    // Transfer funds between two accounts and return a status message
    String transferFunds(String fromAccountId, String toAccountId, float amount, int bankCode);
    
    // Retrieve a list of transactions for a specific account
    List<TransactionDTO> getTransactionsForAccount(String accountId);
    
    // Generate a PDF statement for the provided list of transactions and bank name
    byte[] generateTransactionStatementPdf(List<Transactions> transactions, String bankName);
}
