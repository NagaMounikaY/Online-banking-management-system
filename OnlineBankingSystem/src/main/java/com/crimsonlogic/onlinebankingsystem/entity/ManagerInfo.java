package com.crimsonlogic.onlinebankingsystem.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author nagamounikay Maintaining the ManagerInfo table
 * 
 * @version 1.1
 */
@Entity
@Table(name="manager_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerInfo {
	
	@Id
	@GeneratedValue(generator = "custom-prefix-generator")
	@GenericGenerator(name = "custom-prefix-generator", strategy = "com.crimsonlogic.onlinebankingsystem.util.CustomPrefixIdentifierGenerator", parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "MGR"))
	private String userId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_mobile_number")
	private String userMobileNumber;
	
	@Column(name = "user_gender")
	private String userGender;
	
	@Column(name = "user_age")
	private int userAge;
	
	@Column(name = "user_street")
	private String userStreet;
	
	@Column(name = "user_city")
	private String userCity;
	
	@Column(name = "user_pincode")
	private int userPincode;
	
	@OneToMany(mappedBy = "user")
	private List<Bank> bank;
	
	@OneToOne
	@JsonBackReference
    @JoinColumn(name = "bankUserId", foreignKey = @ForeignKey(name = "FK_BANKUSER"))
    private BankUser bankUser;  // The reference back to the User entity

}
