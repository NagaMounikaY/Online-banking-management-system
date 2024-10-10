package com.crimsonlogic.onlinebankingsystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.onlinebankingsystem.entity.BankUser;
@Repository
public interface BankUserRepository extends JpaRepository<BankUser,String>{
	
	//find by userEmail custom method
	BankUser findByUserEmail(String email);

}
