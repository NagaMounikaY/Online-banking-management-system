package com.crimsonlogic.onlinebankingsystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.onlinebankingsystem.dto.UserRoleDTO;
import com.crimsonlogic.onlinebankingsystem.entity.UserRole;
import com.crimsonlogic.onlinebankingsystem.repository.UserRoleRepository;
import com.crimsonlogic.onlinebankingsystem.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Retrieves all user roles from the repository and converts them to DTOs.
     *
     * @return a list of UserRoleDTOs containing role details.
     */
    public List<UserRoleDTO> getUserRoles() {
        // Fetch all user roles from the repository
        List<UserRole> userRoles = userRoleRepository.findAll();
        
        // Convert the UserRole entities to UserRoleDTOs
        return userRoles.stream()
                .map(role -> new UserRoleDTO(role.getRoleId(), role.getUserRole())) // Create a DTO for each role
                .collect(Collectors.toList()); // Collect and return the list of DTOs
    }
}
