/******************************************************************************
 
 *  Purpose: To create a custom exception handler for IsVerified API of 
 *           UserService.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   21-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.exception.custom;

public class IsVerifiedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 *Purpose: to create a custom exception handler for IsVerified
	 *         API of UserService. 
	 * @param  message containing custom exception String.
	 */
	public IsVerifiedException(String message) {
		super(message);
	}

}
