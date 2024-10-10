package com.crimsonlogic.onlinebankingsystem.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crimsonlogic.onlinebankingsystem.dto.TransactionDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.entity.Transactions;
import com.crimsonlogic.onlinebankingsystem.repository.AccountRepository;
import com.crimsonlogic.onlinebankingsystem.repository.TransactionsRepository;
import com.crimsonlogic.onlinebankingsystem.service.TransactionsService;

/**
 * Controller for managing transaction-related operations in the online banking system.
 */
@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionService;

    @Autowired
    private TransactionsRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Processes a transaction for a specified account.
     *
     * @param accountId the ID of the account to perform the transaction on
     * @param request the TransactionDTO containing transaction details
     * @return ResponseEntity with a message indicating success or failure
     */
    @PostMapping("/{accountId}")
    public ResponseEntity<String> performTransaction(@PathVariable String accountId, @RequestBody TransactionDTO request) {
        try {
            String message = transactionService.processTransaction(accountId, request);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Transaction failed: " + e.getMessage());
        }
    }

    /**
     * Transfers funds from one account to another.
     *
     * @param fromAccountId the ID of the account from which to transfer funds
     * @param toAccountId the ID of the account to which funds will be transferred
     * @param amount the amount to be transferred
     * @param bankCode the bank code associated with the transaction
     * @return a message indicating the result of the transfer operation
     */
    @PostMapping("/transfer")
    public String transferFunds(@RequestParam String fromAccountId,
                                 @RequestParam String toAccountId,
                                 @RequestParam float amount,
                                 @RequestParam int bankCode) {
        return transactionService.transferFunds(fromAccountId, toAccountId, amount, bankCode);
    }

    /**
     * Retrieves a list of transactions for a specified account.
     *
     * @param accountId the ID of the account for which to retrieve transactions
     * @return ResponseEntity containing a list of TransactionDTOs
     */
    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable String accountId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Downloads a transaction statement for a specified account within a date range.
     *
     * @param accountId the ID of the account for which to download the statement
     * @param fromDate the start date for the statement (inclusive)
     * @param toDate the end date for the statement (inclusive)
     * @return ResponseEntity containing the PDF of the transaction statement
     */
    @GetMapping("/download/{accountId}")
    public ResponseEntity<byte[]> downloadTransactionStatement(
            @PathVariable String accountId,
            @RequestParam String fromDate,
            @RequestParam String toDate) {

        // Define a date formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convert strings to LocalDate
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        // Fetch the Account object
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Fetch transactions based on Account object and date range
        List<Transactions> transactions = transactionRepository.findByAccountAndTransactionDateBetween(account, startDate, endDate);
        
        String bankName = account.getBank().getBankName();
        byte[] pdfBytes = transactionService.generateTransactionStatementPdf(transactions, bankName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=transaction_statement.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
