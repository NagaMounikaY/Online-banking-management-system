package com.crimsonlogic.onlinebankingsystem.entity;


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
 * @author nagamounikay Maintaining the Account table
 * 
 * @version 1.1
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(generator = "custom-prefix-generator")
	@GenericGenerator(name = "custom-prefix-generator", strategy = "com.crimsonlogic.onlinebankingsystem.util.CustomPrefixIdentifierGenerator", parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "AC"))
	private String accountId;
	
	@Column(name = "balance")
	private float balance;
	
	@Column(name = "account_type")
	private String accountType;
	
	@Column(name = "account_status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "FK_Customer"))
	private CustomerInfo customer;
	
	@ManyToOne
	@JoinColumn(name = "bankId", foreignKey = @ForeignKey(name = "FK_Bank"))
	private Bank bank;

}
