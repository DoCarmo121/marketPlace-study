package com.example.demo.services.exceptions;

import java.io.Serializable;

public class DataBaseException extends RuntimeException implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public DataBaseException(String msg) {
		super(msg);
	}
}
