package com.crimsonlogic.onlinebankingsystem.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * @author nagamounikay Maintaining the CustomerInfo table
 * 
 * @version 1.1
 */
@Entity
@Table(name="customer_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo {

	@Id
	@GeneratedValue(generator = "custom-prefix-generator")
	@GenericGenerator(name = "custom-prefix-generator", strategy = "com.crimsonlogic.onlinebankingsystem.util.CustomPrefixIdentifierGenerator", parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CUS"))
	private String customerId;

	@Column(name = "user_name")
	private String customerName;

	@Column(name = "user_mobile_number")
	private String customerMobileNumber;
	
	@Column(name = "user_gender")
	private String customerGender;
	
	@Column(name = "user_age")
	private int customerAge;
	
	@Column(name = "user_street")
	private String customerStreet;
	
	@Column(name = "user_city")
	private String customerCity;
	
	@Column(name = "user_pincode")
	private int customerPincode;
	
	@ManyToOne
	@JoinColumn(name = "bankId", foreignKey = @ForeignKey(name = "FK_bank"))
	private Bank bank;
	
	@OneToMany(mappedBy = "customer")
	@ToString.Exclude
	private List<Account> account;
	
	@OneToOne
    @JoinColumn(name = "bankUserId", foreignKey = @ForeignKey(name = "FK_BANKUSER"))
    private BankUser bankUser;
}
