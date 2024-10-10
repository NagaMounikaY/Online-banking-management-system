package com.crimsonlogic.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    private String roleId; // Unique identifier for the user role
    private String userRole; // Role description (e.g., admin, customer, manager)
}
