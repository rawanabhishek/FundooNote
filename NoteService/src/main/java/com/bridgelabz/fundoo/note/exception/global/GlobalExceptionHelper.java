/******************************************************************************
 
 *  Purpose: To create a Global Exception Handler Class which will handle all
 *           the global exception of noteService API's and labelService and also
 *           handles the the global exception for custom Exception handler of 
 *           UserService and labelService API's.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.exception.global;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoo.note.exception.custom.NoteException;

import com.bridgelabz.fundoo.note.exception.custom.LabelException;

import com.bridgelabz.fundoo.note.response.Response;



@ControllerAdvice
public class GlobalExceptionHelper {

	/**
	 * Purpose: To create a global exception handler for noteService and label .
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Response> somethingWentWrong(Exception ex) {
		Response responseMessage = new Response(500, ex.getMessage(), null);
		return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Purpose: to create a global exception handler for NoteException custom
	 *          exception. 
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(NoteException.class)
	public final ResponseEntity<Response> NoteException(NoteException ex) {
		Response responseMessage = new Response(400, ex.getMessage(), null);

		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Purpose: to create a global exception handler for LabelException custom
	 *          exception. 
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(LabelException.class)
	public final ResponseEntity<Response> updateNoteException(LabelException ex) {

		Response responseMessage = new Response(400, ex.getMessage(), null);

		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_GATEWAY);
	}

	

}
