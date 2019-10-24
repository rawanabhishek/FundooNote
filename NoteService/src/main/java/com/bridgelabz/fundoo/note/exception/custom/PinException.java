/******************************************************************************
 
 *  Purpose: To create a custom exception handler for Pin API of 
 *           UserService.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.exception.custom;

public class PinException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Purpose: To create a custom exception handler for SetPassword API of
	 *          UserService. 
	 * @param   message containing custom exception String.
	 */
	public PinException(String message) {
		super(message);
	}

}
