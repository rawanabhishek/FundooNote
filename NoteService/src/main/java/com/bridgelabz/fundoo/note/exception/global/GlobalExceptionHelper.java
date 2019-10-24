/******************************************************************************
 
 *  Purpose: To create a Global Exception Handler Class which will handle all
 *           the global exception of userService API's and also handles the 
 *           the global exception for custom Exception handler of UserService
 *           API's.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   21-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.exception.global;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoo.note.exception.custom.AddNoteException;
import com.bridgelabz.fundoo.note.exception.custom.DeleteNoteException;
import com.bridgelabz.fundoo.note.exception.custom.PinException;
import com.bridgelabz.fundoo.note.exception.custom.ReadNoteException;
import com.bridgelabz.fundoo.note.exception.custom.UpdateNoteException;
import com.bridgelabz.fundoo.note.response.Response;



@ControllerAdvice
public class GlobalExceptionHelper {

	/**
	 * Purpose: To create a global exception handler for noteService .
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
	 * Purpose: to create a global exception handler for AddNoteException custom
	 *          exception. 
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(AddNoteException.class)
	public final ResponseEntity<Response> addNoteException(AddNoteException ex) {
		Response responseMessage = new Response(400, ex.getMessage(), null);

		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Purpose: to create a global exception handler for UpdateNoteException custom
	 *          exception. 
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(UpdateNoteException.class)
	public final ResponseEntity<Response> updateNoteException(UpdateNoteException ex) {

		Response responseMessage = new Response(400, ex.getMessage(), null);

		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_GATEWAY);
	}

	/**
	 * Purpose: to create a global exception handler for ReadNoteException custom
	 *          exception. 
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(ReadNoteException.class)
	public final ResponseEntity<Response> readNotePasswordException(ReadNoteException ex) {

		Response responseMessage = new Response(400, ex.getMessage(), null);

		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Purpose: to create a global exception handler PinException custom
	 *          exception. 
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(PinException.class)
	public final ResponseEntity<Response> pinException(PinException ex) {

		Response responseMessage = new Response(400, ex.getMessage(), null);

		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Purpose: to create a global exception handler for DeleteNoteException custom
	 *          exception. 
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(DeleteNoteException.class)
	public final ResponseEntity<Response> deleteNoteException(DeleteNoteException ex) {
		Response responseMessage = new Response(400, ex.getMessage(), null);

		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);

	}

}
