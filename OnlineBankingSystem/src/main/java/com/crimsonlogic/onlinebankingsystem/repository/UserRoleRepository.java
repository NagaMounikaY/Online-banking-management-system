package com.crimsonlogic.onlinebankingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.onlinebankingsystem.entity.BankUser;
import com.crimsonlogic.onlinebankingsystem.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    
    // Find a user role associated with a specific bank user
    UserRole findByBankUser(BankUser user);
    
    // Find user roles by role ID and role description
    List<UserRole> findByRoleIdAndUserRole(String roleId, String userRole);
}
