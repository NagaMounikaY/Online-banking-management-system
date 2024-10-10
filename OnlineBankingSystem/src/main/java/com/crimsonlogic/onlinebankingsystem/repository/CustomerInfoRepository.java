package com.crimsonlogic.onlinebankingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.CustomerInfo;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, String> {
	
    List<CustomerInfo> findByBank(Bank bank); // Adjust based on your Bank entity
     
}
