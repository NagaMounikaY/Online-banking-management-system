package com.crimsonlogic.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankUserDTO {
    private String bankUserId;
    private String userEmail; // Email
    private String userPassword; // Password
    private UserRoleDTO role; // Include UserRoleDTO for role details
}
