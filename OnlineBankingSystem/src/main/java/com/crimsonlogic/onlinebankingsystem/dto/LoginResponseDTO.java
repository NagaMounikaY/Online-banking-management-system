package com.crimsonlogic.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private ManagerInfoDTO managerInfo; // Information about the logged-in manager
    private CustomerDTO customerInfo; // Information about the logged-in customer
    private String userRole; // Role of the user (e.g., manager, customer)
    private String userIds; // Unique identifiers for the user (could be managerId or customerId)
}
