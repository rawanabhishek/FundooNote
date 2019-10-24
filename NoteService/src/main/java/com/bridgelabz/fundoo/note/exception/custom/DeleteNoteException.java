/******************************************************************************
 
 *  Purpose: To create a custom exception handler for DeleteNote API of 
 *           UserService.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.exception.custom;

public class DeleteNoteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 *Purpose: To create a custom exception handler for IsVerified
	 *         API of UserService. 
	 * @param  message containing custom exception String.
	 */
	public DeleteNoteException(String message) {
		super(message);
	}

}
