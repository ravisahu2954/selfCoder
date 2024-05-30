package com.selfcoder.exception;

public class ProblemAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public ProblemAlreadyExistException(String message) {
		super(message);
		
	}
	
}
