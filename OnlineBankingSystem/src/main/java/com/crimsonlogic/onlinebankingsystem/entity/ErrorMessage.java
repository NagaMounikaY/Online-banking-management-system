package com.crimsonlogic.onlinebankingsystem.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
	
	private int statusCode;
	private LocalDateTime timestamp;
	private String description;
	private String message;

}
