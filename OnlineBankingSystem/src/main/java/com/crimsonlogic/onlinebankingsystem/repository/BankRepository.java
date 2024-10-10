package com.crimsonlogic.onlinebankingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.ManagerInfo;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {
    
    // Find a bank associated with a specific manager
    Optional<Bank> findByUser(ManagerInfo user);
    
    // Find a bank by its name
    Bank findByBankName(String bankName);
}
