/******************************************************************************
 
 *  Purpose: To create a custom exception handler for setPassword API of 
 *           UserService.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   21-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.exception.custom;

public class SetPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Purpose: to create a custom exception handler for SetPassword API of
	 *          UserService. 
	 * @param   message containing custom exception String.
	 */
	public SetPasswordException(String message) {
		super(message);
	}

}