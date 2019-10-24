/******************************************************************************
 
 *  Purpose: To create a custom exception handler for UpdateNote API of 
 *           UserService.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.exception.custom;

public class UpdateNoteException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	/**
	 *Purpose: To create a custom exception handler for Login
	 *         API of UserService. 
	 * @param  message containing custom exception String.
	 */
	public UpdateNoteException(String message ) {
		super(message);
	}


}
