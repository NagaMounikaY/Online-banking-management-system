package com.crimsonlogic.onlinebankingsystem.service;

import java.util.List;

import com.crimsonlogic.onlinebankingsystem.dto.LoginResponseDTO;
import com.crimsonlogic.onlinebankingsystem.dto.ManagerInfoDTO;
import com.crimsonlogic.onlinebankingsystem.exception.ResourceNotFoundException;

public interface ManagerInfoService {
    
    // Create a new user (manager) and return its DTO representation
    ManagerInfoDTO createUser(ManagerInfoDTO userInfoDTO) throws ResourceNotFoundException;
    
    // Retrieve user details by their ID
    ManagerInfoDTO getUserById(String userId);
    
    // Retrieve a list of all users (managers)
    List<ManagerInfoDTO> getAllUsers();
    
    // Update the details of an existing user and return the updated DTO
    ManagerInfoDTO updateUser(String userId, ManagerInfoDTO userInfoDTO);
    
    // Delete a user by their ID
    void deleteUser(String userId);
    
    // Retrieve a list of bank managers
    List<ManagerInfoDTO> getBankManagers();
    
    // Retrieve a list of bank customers
    List<ManagerInfoDTO> getBankCustomers();
    
    // Log in a manager and return a response containing their info
    LoginResponseDTO login(ManagerInfoDTO userDTO) throws ResourceNotFoundException;
}
