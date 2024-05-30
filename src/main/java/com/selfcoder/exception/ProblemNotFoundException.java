package com.selfcoder.exception;

public class ProblemNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
  
	public ProblemNotFoundException(String msg)
	{
		super(msg);
	}
	
}
