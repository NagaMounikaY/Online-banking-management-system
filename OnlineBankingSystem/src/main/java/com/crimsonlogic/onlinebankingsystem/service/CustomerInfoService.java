package com.crimsonlogic.onlinebankingsystem.service;

import java.util.List;

import com.crimsonlogic.onlinebankingsystem.dto.CustomerDTO;
import com.crimsonlogic.onlinebankingsystem.dto.LoginResponseDTO;
import com.crimsonlogic.onlinebankingsystem.exception.ResourceNotFoundException;

public interface CustomerInfoService {
    
    // Register a new customer and return its DTO representation
    CustomerDTO registerCustomer(CustomerDTO customerDTO);
    
    // Retrieve a list of all customers
    List<CustomerDTO> getAllCustomers();
    
    // Retrieve a list of customers associated with a specific bank name
    List<CustomerDTO> getCustomersByBankName(String bankName);
    
    // Log in a customer and return a response containing their info
    LoginResponseDTO loginCustomer(CustomerDTO customerDTO) throws ResourceNotFoundException;
    
    // Retrieve customer details by their ID
    CustomerDTO getCustomerById(String customerId) throws ResourceNotFoundException;
}
