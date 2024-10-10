package com.crimsonlogic.onlinebankingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crimsonlogic.onlinebankingsystem.dto.BankDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.service.BankService;

/**
 * Controller for managing bank-related operations in the online banking system.
 */
@RestController
@RequestMapping("/banks")
@CrossOrigin(origins = "http://localhost:3000")
public class BankController {

    @Autowired
    private BankService bankService;

    /**
     * Adds a new bank to the system.
     *
     * @param bankDTO the bank data transfer object containing bank details
     * @return the created BankDTO
     */
    @PostMapping("/bankinsert")
    public BankDTO addBank(@RequestBody BankDTO bankDTO) {
        return bankService.createBank(bankDTO);
    }

    /**
     * Retrieves a list of all banks.
     *
     * @return a list of BankDTOs representing all banks in the system
     */
    @GetMapping("/getallbanks")
    public List<BankDTO> getAllBanks() {
        return bankService.getAllBanks();
    }

    /**
     * Retrieves a bank associated with a specific manager ID.
     *
     * @param userId the ID of the manager whose bank is to be retrieved
     * @return ResponseEntity containing the BankDTO if found, or a NOT_FOUND status if not
     */
    @GetMapping("/manager/{userId}")
    public ResponseEntity<BankDTO> getBankByUserId(@PathVariable String userId) {
        Optional<Bank> bank = bankService.findBankByUserId(userId);
        return bank.map(b -> ResponseEntity.ok(bankService.convertToDTO(b)))
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
