package com.example.api.exceptions;

public class InvalidToken extends RuntimeException {

	public InvalidToken() {
		super("Token Inválido...");
	}

	public InvalidToken(String msg){
		super(msg);
	}

}
