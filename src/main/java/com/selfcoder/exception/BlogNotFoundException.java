package com.selfcoder.exception;

public class BlogNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public BlogNotFoundException(String msg)
    {
    	super(msg);
    }
}
