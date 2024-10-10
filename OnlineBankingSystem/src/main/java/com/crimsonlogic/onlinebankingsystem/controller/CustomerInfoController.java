package com.crimsonlogic.onlinebankingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crimsonlogic.onlinebankingsystem.dto.CustomerDTO;
import com.crimsonlogic.onlinebankingsystem.dto.LoginResponseDTO;
import com.crimsonlogic.onlinebankingsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.onlinebankingsystem.service.CustomerInfoService;

/**
 * Controller for managing customer-related operations in the online banking system.
 */
@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerInfoController {

    @Autowired
    private CustomerInfoService customerInfoService;

    /**
     * Registers a new customer in the system.
     *
     * @param customerDTO the data transfer object containing customer details
     * @return ResponseEntity containing the registered CustomerDTO
     */
    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerInfoService.registerCustomer(customerDTO);
        return ResponseEntity.ok(savedCustomer);
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return ResponseEntity containing a list of CustomerDTOs
     */
    @GetMapping("/customerList")
    public ResponseEntity<List<CustomerDTO>> getCustomerList() {
        List<CustomerDTO> customers = customerInfoService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Retrieves a list of customers associated with a specific bank name.
     *
     * @param bankName the name of the bank
     * @return ResponseEntity containing a list of CustomerDTOs associated with the specified bank
     */
    @GetMapping("/byBank")
    public ResponseEntity<List<CustomerDTO>> getCustomersByBankName(@RequestParam String bankName) {
        List<CustomerDTO> customers = customerInfoService.getCustomersByBankName(bankName);
        return ResponseEntity.ok(customers);
    }

    /**
     * Logs in a customer using their credentials.
     *
     * @param userDTO the data transfer object containing login credentials
     * @return ResponseEntity containing a LoginResponseDTO if login is successful, or a bad request response if not
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody CustomerDTO userDTO) {
        try {
            LoginResponseDTO loggedInUser = customerInfoService.loginCustomer(userDTO);
            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves customer details by customer ID.
     *
     * @param id the ID of the customer to be retrieved
     * @return ResponseEntity containing the CustomerDTO
     * @throws ResourceNotFoundException if the customer is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id) throws ResourceNotFoundException {
        CustomerDTO customerDTO = customerInfoService.getCustomerById(id);
        return ResponseEntity.ok(customerDTO);
    }
}
