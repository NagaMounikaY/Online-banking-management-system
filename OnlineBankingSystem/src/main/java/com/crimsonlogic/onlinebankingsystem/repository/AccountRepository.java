package com.crimsonlogic.onlinebankingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.CustomerInfo;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    
    // Find accounts associated with a specific customer
    List<Account> findByCustomer(CustomerInfo customer);
    
    // Find accounts associated with a specific bank
    List<Account> findByBank(Bank bank);
}
