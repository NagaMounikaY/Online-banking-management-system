package com.crimsonlogic.onlinebankingsystem.entity;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author nagamounikay Maintaining the Transaction table
 * 
 * @version 1.1
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
	
	@Id
	@GeneratedValue(generator = "custom-prefix-generator")
	@GenericGenerator(name = "custom-prefix-generator", strategy = "com.crimsonlogic.onlinebankingsystem.util.CustomPrefixIdentifierGenerator", parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "BNK"))
	private String transactionId;
	
	@Column(name = "balance")
	private float balance;
	
	@Column(name = "transaction_type")
	private String transactionType;
	
	@Column(name = "description")
	private String discription;
	
	@Column(name = "transaction_date")
	private LocalDate transactionDate;
	
	@ManyToOne
	@JoinColumn(name = "accountId", foreignKey = @ForeignKey(name = "FK_account"))
	private Account account;
	
	 @ManyToOne
	    @JoinColumn(name = "from_account_id", foreignKey = @ForeignKey(name = "FK_from_account"))
	    private Account fromAccount;

	    @ManyToOne
	    @JoinColumn(name = "to_account_id", foreignKey = @ForeignKey(name = "FK_to_account"))
	    private Account toAccount;

	    // Added for logging banks
	    @Column(name = "from_bank")
	    private String fromBank;

	    @Column(name = "to_bank")
	    private String toBank;

}
