/******************************************************************************
 
 *  Purpose: To create a custom exception handler for Register API of 
 *           UserService.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   21-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.exception.custom;

public class RegisterException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	/**
	 *Purpose: To create a custom exception handler for Login
	 *         API of UserService. 
	 * @param  message containing custom exception String.
	 */
	public RegisterException(String message ) {
		super(message);
	}


}
