package com.selfcoder.exception;

public class TagAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;
  
	public TagAlreadyExistException(String msg)
	{
		super(msg);
	}

}
