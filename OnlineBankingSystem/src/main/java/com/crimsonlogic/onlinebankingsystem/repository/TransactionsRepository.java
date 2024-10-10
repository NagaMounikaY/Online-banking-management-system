package com.crimsonlogic.onlinebankingsystem.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.entity.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, String> {
    
    // Find transactions associated with a specific account
    List<Transactions> findByAccount(Account account);
    
    // Find transactions for a specific account within a date range
    List<Transactions> findByAccountAndTransactionDateBetween(Account account, LocalDate startDate, LocalDate endDate);
}
