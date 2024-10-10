package com.crimsonlogic.onlinebankingsystem.service;

import com.crimsonlogic.onlinebankingsystem.dto.BankDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService {
    
    // Create a new bank and return its DTO representation
    BankDTO createBank(BankDTO bankDTO);
    
    // Retrieve a list of all banks
    List<BankDTO> getAllBanks();
    
    // Find a bank associated with a specific user ID
    Optional<Bank> findBankByUserId(String userId);
    
    // Convert a Bank entity to its DTO representation
    BankDTO convertToDTO(Bank bank);
}
