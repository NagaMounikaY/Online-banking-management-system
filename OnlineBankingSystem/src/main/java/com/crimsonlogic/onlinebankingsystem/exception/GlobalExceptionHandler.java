package com.crimsonlogic.onlinebankingsystem.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.crimsonlogic.onlinebankingsystem.entity.ErrorMessage;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException
	(ResourceNotFoundException e,
			WebRequest request){
		ErrorMessage err = new ErrorMessage(HttpStatus.NOT_FOUND.value(), 
				LocalDateTime.now(), e.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(err,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorMessage> userAlreadyExistException
	(UserAlreadyExistException e,
			WebRequest request){
		ErrorMessage err = new ErrorMessage(HttpStatus.FOUND.value(), 
				LocalDateTime.now(), e.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(err,HttpStatus.FOUND);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler
	(Exception e,
			WebRequest request){
		ErrorMessage err = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				LocalDateTime.now(), e.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(err,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
