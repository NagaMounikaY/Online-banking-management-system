package com.crimsonlogic.onlinebankingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crimsonlogic.onlinebankingsystem.dto.UserRoleDTO;
import com.crimsonlogic.onlinebankingsystem.service.UserRoleService;

/**
 * Controller for managing user roles in the online banking system.
 */
@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "http://localhost:3000") // Allow this origin
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * Retrieves all user roles from the system.
     *
     * @return ResponseEntity containing a list of UserRoleDTOs
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserRoleDTO>> getAllRoles() {
        List<UserRoleDTO> roles = userRoleService.getUserRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
