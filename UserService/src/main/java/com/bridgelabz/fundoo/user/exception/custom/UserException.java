package com.bridgelabz.fundoo.user.exception.custom;

public class UserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Purpose: To create a custom exception handler for User's API of
	 *          UserService. 
	 * @param   message containing custom exception String.
	 */
	public UserException(String message) {
		super(message);
	}

}
