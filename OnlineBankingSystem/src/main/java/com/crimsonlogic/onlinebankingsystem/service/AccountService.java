package com.crimsonlogic.onlinebankingsystem.service;

import java.util.List;
import java.util.Optional;

import com.crimsonlogic.onlinebankingsystem.dto.AccountDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Account;

public interface AccountService {
    
    // Create a new account and return its DTO representation
    AccountDTO createAccount(AccountDTO accountDTO);
    
    // Retrieve a list of accounts associated with a specific customer ID
    List<AccountDTO> getAccountsByCustomerId(String customerId);
    
    // Retrieve a list of all accounts
    List<AccountDTO> getAllAccounts();
    
    // Retrieve a list of accounts associated with a specific bank name
    List<AccountDTO> getAccountsByBankName(String bankName);
    
    // Find an account by its ID
    Optional<Account> findById(String accountId);
    
    // Update the status of an account
    void updateAccountStatus(String accountId, String newStatus);
    
    // Additional methods for withdrawals and deposits can be added later
    // Account withdraw(String accountId, float amount) throws InsufficientFundsException, ResourceNotFoundException;
    // Account deposit(String accountId, float amount) throws ResourceNotFoundException;
}
