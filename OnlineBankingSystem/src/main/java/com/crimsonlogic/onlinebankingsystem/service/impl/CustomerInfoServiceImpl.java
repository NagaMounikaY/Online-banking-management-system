package com.crimsonlogic.onlinebankingsystem.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.onlinebankingsystem.dto.CustomerDTO;
import com.crimsonlogic.onlinebankingsystem.dto.LoginResponseDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.BankUser;
import com.crimsonlogic.onlinebankingsystem.entity.CustomerInfo;
import com.crimsonlogic.onlinebankingsystem.entity.UserRole;
import com.crimsonlogic.onlinebankingsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.onlinebankingsystem.repository.BankRepository;
import com.crimsonlogic.onlinebankingsystem.repository.BankUserRepository;
import com.crimsonlogic.onlinebankingsystem.repository.CustomerInfoRepository;
import com.crimsonlogic.onlinebankingsystem.repository.UserRoleRepository;
import com.crimsonlogic.onlinebankingsystem.service.CustomerInfoService;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankUserRepository bankUserRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private String decodePassword(String encodedPassword) {
        return new String(Base64.getDecoder().decode(encodedPassword));
    }

    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDTO) {
        // Create and save the BankUser entity
        BankUser bankUser = modelMapper.map(customerDTO.getBankUser(), BankUser.class);
        bankUser.setUserPassword(encodePassword(bankUser.getUserPassword())); 
        bankUser = bankUserRepository.save(bankUser);

        // Check if the bank name is provided
        if (customerDTO.getBankName() == null) {
            throw new RuntimeException("Bank Name must be provided");
        }
        
        // Fetch the existing Bank entity using the bank name from DTO
        Bank bank = bankRepository.findByBankName(customerDTO.getBankName());

        // Create the CustomerInfo entity
        CustomerInfo customerInfo = modelMapper.map(customerDTO, CustomerInfo.class);
        customerInfo.setBankUser(bankUser); // Set the BankUser for the customer
        customerInfo.setBank(bank); // Set the existing Bank entity

        // Save the CustomerInfo entity
        customerInfo = customerInfoRepository.save(customerInfo);

        // Map and return the saved CustomerInfo as CustomerDTO
        return modelMapper.map(customerInfo, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        // Retrieve all CustomerInfo entities
        List<CustomerInfo> customers = customerInfoRepository.findAll();
        // Map each CustomerInfo to CustomerDTO and return the list
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getCustomersByBankName(String bankName) {
        // Fetch the Bank entity by its name
        Bank bank = bankRepository.findByBankName(bankName);
        if (bank == null) {
            // Handle the case where the bank is not found
            throw new RuntimeException("Bank not found");
        }
        // Retrieve customers associated with the bank
        List<CustomerInfo> customers = customerInfoRepository.findByBank(bank);
        // Convert CustomerInfo to CustomerDTO and return
        return customers.stream()
                .map(this::convertToDTO) // Use helper method for conversion
                .collect(Collectors.toList());
    }

    private CustomerDTO convertToDTO(CustomerInfo customerInfo) {
        // Map fields from CustomerInfo to CustomerDTO
        return new CustomerDTO(
            customerInfo.getCustomerId(),
            customerInfo.getCustomerName(),
            customerInfo.getCustomerMobileNumber(),
            customerInfo.getCustomerGender(),
            customerInfo.getCustomerAge(),
            customerInfo.getCustomerStreet(),
            customerInfo.getCustomerCity(),
            customerInfo.getCustomerPincode(),
            customerInfo.getBankUser().getUserEmail(),
            null, // Assuming you don't need the password in the DTO
            customerInfo.getBank().getBankName(), // Set the bank name
            null // Handle BankUserDTO mapping if necessary
        );
    }
    
    @Override
    public LoginResponseDTO loginCustomer(CustomerDTO customerDTO) throws ResourceNotFoundException {
        // Retrieve BankUser entity by email
        BankUser user = bankUserRepository.findByUserEmail(customerDTO.getUserEmail());
        // Validate user credentials
        if (user != null && decodePassword(user.getUserPassword()).equals(customerDTO.getUserPassword())) {
            UserRole role = roleRepository.findByBankUser(user);
            String userId = user.getCustomer().getCustomerId();

            // Create and populate LoginResponseDTO
            LoginResponseDTO response = new LoginResponseDTO();
            response.setCustomerInfo(modelMapper.map(user, CustomerDTO.class));
            response.setUserRole(role.getUserRole());
            response.setUserIds(userId);

            return response;
        } else {
            throw new ResourceNotFoundException("Invalid username or password");
        }
    }
    
    @Override
    public CustomerDTO getCustomerById(String customerId) throws ResourceNotFoundException {
        // Fetch the CustomerInfo entity using the customerId
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        
        // Map the CustomerInfo entity to CustomerDTO and return
        return modelMapper.map(customerInfo, CustomerDTO.class);
    }
}
