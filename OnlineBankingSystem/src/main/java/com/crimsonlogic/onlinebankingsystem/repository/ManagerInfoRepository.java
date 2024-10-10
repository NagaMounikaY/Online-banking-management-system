package com.crimsonlogic.onlinebankingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.onlinebankingsystem.entity.ManagerInfo;

@Repository
public interface ManagerInfoRepository extends JpaRepository<ManagerInfo, String> {
    
    // Find manager users by their role ID using a custom query
    @Query("SELECT u FROM ManagerInfo u JOIN u.bankUser b WHERE b.role.roleId = :roleId")
    List<ManagerInfo> findUsersByRoleId(String roleId);
    
    // Find a manager by their username
    Optional<ManagerInfo> findByUserName(String userName);
}
