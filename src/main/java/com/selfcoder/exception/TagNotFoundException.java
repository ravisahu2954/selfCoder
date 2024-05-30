package com.selfcoder.exception;

public class TagNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
  
	public TagNotFoundException(String msg)
	{
		super(msg);
	}
}
