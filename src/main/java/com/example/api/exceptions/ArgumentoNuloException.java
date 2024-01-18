package com.example.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ArgumentoNuloException extends MyException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;

	public ArgumentoNuloException(String msg, String msg1) {
		super(msg);
		this.msg = msg1;
	}

	public String getMsg() {
		return msg;
	}
}