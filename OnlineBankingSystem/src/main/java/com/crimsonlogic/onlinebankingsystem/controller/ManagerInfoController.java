package com.crimsonlogic.onlinebankingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crimsonlogic.onlinebankingsystem.dto.LoginResponseDTO;
import com.crimsonlogic.onlinebankingsystem.dto.ManagerInfoDTO;
import com.crimsonlogic.onlinebankingsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.onlinebankingsystem.service.ManagerInfoService;

/**
 * Controller for managing user-related operations, particularly for bank managers,
 * in the online banking system.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000") // Allow this origin
public class ManagerInfoController {

    @Autowired
    private ManagerInfoService managerInfoService;

    /**
     * Creates a new user in the system.
     *
     * @param userInfoDTO the data transfer object containing user details
     * @return ResponseEntity containing the created ManagerInfoDTO
     * @throws ResourceNotFoundException if any required resource is not found
     */
    @PostMapping("/insert")
    public ResponseEntity<ManagerInfoDTO> createUser(@RequestBody ManagerInfoDTO userInfoDTO) throws ResourceNotFoundException {
        ManagerInfoDTO createdUser = managerInfoService.createUser(userInfoDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Retrieves user details by user ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return ResponseEntity containing the ManagerInfoDTO
     */
    @GetMapping("/get/{userId}")
    public ResponseEntity<ManagerInfoDTO> getUserById(@PathVariable String userId) {
        ManagerInfoDTO user = managerInfoService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return ResponseEntity containing a list of ManagerInfoDTOs
     */
    @GetMapping("/all")
    public ResponseEntity<List<ManagerInfoDTO>> getAllUsers() {
        List<ManagerInfoDTO> users = managerInfoService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Updates user details based on the provided user ID.
     *
     * @param userId the ID of the user to be updated
     * @param userInfoDTO the updated user information
     * @return ResponseEntity containing the updated ManagerInfoDTO
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<ManagerInfoDTO> updateUser(@PathVariable String userId, @RequestBody ManagerInfoDTO userInfoDTO) {
        ManagerInfoDTO updatedUser = managerInfoService.updateUser(userId, userInfoDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Retrieves a list of bank managers.
     *
     * @return ResponseEntity containing a list of ManagerInfoDTOs for bank managers
     */
    @GetMapping("/managers")
    public ResponseEntity<List<ManagerInfoDTO>> getBankManagers() {
        List<ManagerInfoDTO> managers = managerInfoService.getBankManagers();
        return ResponseEntity.ok(managers);
    }

    /**
     * Logs in a manager using their credentials.
     *
     * @param userDTO the data transfer object containing login credentials
     * @return ResponseEntity containing a LoginResponseDTO if login is successful,
     *         or a bad request response if not
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody ManagerInfoDTO userDTO) {
        try {
            LoginResponseDTO loggedInUser = managerInfoService.login(userDTO);
            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves a list of customers associated with bank managers.
     *
     * @return ResponseEntity containing a list of ManagerInfoDTOs for bank customers
     */
    @GetMapping("/customers")
    public ResponseEntity<List<ManagerInfoDTO>> getCustomers() {
        List<ManagerInfoDTO> managers = managerInfoService.getBankCustomers();
        return ResponseEntity.ok(managers);
    }
}
