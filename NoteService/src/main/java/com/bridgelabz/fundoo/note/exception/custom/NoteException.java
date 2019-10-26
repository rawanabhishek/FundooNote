/******************************************************************************
 
 *  Purpose: To create a custom exception handler for NoteService.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.exception.custom;

public class NoteException  extends RuntimeException {
	
private static final long serialVersionUID = 1L;
	
	

	/**
	 *Purpose: To create a custom exception handler for ForgotPassword
	 *         API of UserService. 
	 * @param  message containing custom exception String.
	 */
	public NoteException(String message ) {
		super(message);
	}

}
