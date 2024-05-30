package com.selfcoder.exception;

public class CustomBadRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomBadRequestException(String msg)
	{
		super(msg);
	}
}
