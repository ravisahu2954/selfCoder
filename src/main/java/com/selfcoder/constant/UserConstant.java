package com.selfcoder.constant;

import lombok.Getter;

@Getter
public class UserConstant {

	/**
	 * API Response Constant
	 */
	public static final String USER_CREATED = "User created successfully";
	public static final String USER_DELETED = "User deleted successfully";
	public static final String ALL_USER_FETCHED = "users details fetched successfully";
	public static final String USER_UPDATED = "User updated successfully";
	
	
	/**
	 * Exception Constant
	 */
	public static final String USER_NOT_FOUND = "User Not Found!";
	public static final String USER_NOT_EXIST = "User Id %s does not exist!";
	public static final String NO_CONTENT_FOUND = "No Content Found!";
	public static final String INTERNAL_SERVER_ERROR = "Team is fixing this issue, Kindly wait for sometime!";
	public static final String USER_ALREADY_EXISTS = "User name %s already exists!";
	
	
	/**
	 * Logs Constant
	 */
	public static final String INSIDE_CREATE_USER = "## Creating user ##";
	public static final String UPDATE_USER = "## Updating user ##";
	public static final String DELETE_USER = "## Deleting user ##";
	public static final String GET_USER = "## Getting All user Details ##";
	public static final String GET_USER_BY_ID = "## Getting user Details by ID {}##";
	public static final String GET_USER_BY_MAKE = "## Getting user Details by make {}##";
	
}
