package com.crimsonlogic.onlinebankingsystem.entity;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @author nagamounikay Maintaining the UserRole table
 * 
 * @version 1.1
 */
@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

	@Id
	private String roleId;

	@Column(name = "user_role")
	private String userRole;

	 @OneToMany(mappedBy = "role")
	 private List<BankUser> bankUser;

}
