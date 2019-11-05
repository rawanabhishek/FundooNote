package com.bridgelabz.fundoo.note.exception.custom;

public class CollaboratorException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *Purpose: To create a custom exception handler for Collaborator
	 *        
	 * @param  message containing custom exception String.
	 */
	public CollaboratorException(String message) {
		super(message);
	}

}
