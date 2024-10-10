package com.crimsonlogic.onlinebankingsystem.entity;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author nagamounikay Maintaining the BankUser table
 * 
 * @version 1.1
 */
@Entity
@Table(name="bank_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankUser {
	
	@Id
	@GeneratedValue(generator = "custom-prefix-generator")
	@GenericGenerator(name = "custom-prefix-generator", strategy = "com.crimsonlogic.onlinebankingsystem.util.CustomPrefixIdentifierGenerator", parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CUS"))
	private String bankUserId;
	
	@Column(name = "user_email" , unique = true)
	private String userEmail;

	@Column(name = "user_password")
	private String userPassword;
	
	@OneToOne(mappedBy="bankUser",cascade=CascadeType.ALL)
	@JsonManagedReference
	private ManagerInfo user;
	
	@OneToOne(mappedBy="bankUser",cascade=CascadeType.ALL)
	@JsonManagedReference
	private CustomerInfo customer;
	
	@ManyToOne
	@JoinColumn(name = "roleId", foreignKey = @ForeignKey(name = "FK_role"))
	private UserRole role;

}
