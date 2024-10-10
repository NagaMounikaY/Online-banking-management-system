package com.crimsonlogic.onlinebankingsystem.entity;



import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * @author nagamounikay Maintaining the Bank table
 * 
 * @version 1.1
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
	
	@Id
	@GeneratedValue(generator = "custom-prefix-generator")
	@GenericGenerator(name = "custom-prefix-generator", strategy = "com.crimsonlogic.onlinebankingsystem.util.CustomPrefixIdentifierGenerator", parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "BNK"))
	private String bankId;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "bank_code")
	private int bankCode;
	
	@Column(name = "bank_address")
	private String bankAddress;
	
	@Column(name = "bank_email",unique = true)
	private String bankEmail;
	
	@Column(name = "bank_phno")
	private String bankContactNumber;
	
	@Column(name = "bank_country")
	private String bankCountry;
	
	@OneToMany(mappedBy = "bank")
	private List<CustomerInfo> customer;
	
	
	@OneToMany(mappedBy = "bank",cascade=CascadeType.ALL)
	@ToString.Exclude
	private List<Account> account;
	
	@ManyToOne
	@JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "FK_user"))
	private ManagerInfo user;

}
