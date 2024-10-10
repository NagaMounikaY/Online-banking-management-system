package com.crimsonlogic.onlinebankingsystem.exception;

public class InsufficientFundsException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String msg) {
		super(msg);
	}

}
