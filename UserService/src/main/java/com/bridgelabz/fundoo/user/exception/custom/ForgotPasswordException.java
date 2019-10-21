/******************************************************************************
 
 *  Purpose: To create a custom exception handler for ForgotPassword API of 
 *           UserService.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.exception.custom;

public class ForgotPasswordException  extends RuntimeException {
	
private static final long serialVersionUID = 1L;
	
	

	/**
	 *Purpose: to create a custom exception handler for ForgotPassword
	 *         API of UserService. 
	 * @param  message containing custom exception String.
	 */
	public ForgotPasswordException(String message ) {
		super(message);
	}

}
