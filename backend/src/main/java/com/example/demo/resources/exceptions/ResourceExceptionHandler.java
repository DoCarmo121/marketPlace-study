package com.example.demo.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.services.exceptions.DataBaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;


//intercepta alguma intercepcao na camada de REST
@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource Not Found!");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> database(DataBaseException e, HttpServletRequest request){
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Database exception!");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

}
