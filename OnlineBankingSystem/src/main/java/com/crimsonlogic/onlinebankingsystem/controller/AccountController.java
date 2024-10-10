package com.crimsonlogic.onlinebankingsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crimsonlogic.onlinebankingsystem.dto.AccountDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.service.AccountService;

/**
 * Controller for handling account-related operations in the online banking system.
 */
@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Creates a new account.
     *
     * @param accountDTO the account data transfer object containing account details
     * @return ResponseEntity containing the created AccountDTO
     */
    @PostMapping("/addaccount")
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return ResponseEntity.ok(createdAccount);
    }

    /**
     * Retrieves a list of accounts associated with a specific customer ID.
     *
     * @param customerId the ID of the customer whose accounts are to be retrieved
     * @return ResponseEntity containing a list of AccountDTOs
     */
    @GetMapping("/viewaccount/{customerId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByCustomerId(@PathVariable String customerId) {
        List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Retrieves a list of all accounts.
     *
     * @return ResponseEntity containing a list of all AccountDTOs
     */
    @GetMapping("/accountList")
    public ResponseEntity<List<AccountDTO>> getAccountList() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    /**
     * Retrieves accounts by the bank name.
     *
     * @param bankName the name of the bank
     * @return ResponseEntity containing a list of AccountDTOs associated with the specified bank
     */
    @GetMapping("/byBankName")
    public ResponseEntity<List<AccountDTO>> getAccountsByBankName(@RequestParam String bankName) {
        List<AccountDTO> accounts = accountService.getAccountsByBankName(bankName);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Updates the status of a specific account.
     *
     * @param accountId the ID of the account to be updated
     * @param request   a map containing the new status of the account
     * @return ResponseEntity confirming the account status update
     */
    @PatchMapping("/updateStatus/{accountId}")
    public ResponseEntity<String> updateAccountStatus(@PathVariable String accountId, @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        accountService.updateAccountStatus(accountId, newStatus);
        return ResponseEntity.ok("Account status updated to " + newStatus);
    }

    /**
     * Retrieves the account details by account ID.
     *
     * @param accountId the ID of the account to be retrieved
     * @return ResponseEntity containing the Account object
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable String accountId) {
        Account account = accountService.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));
        return ResponseEntity.ok(account);
    }
}
