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
package com.bridgelabz.fundoo.user.exception.global;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.bridgelabz.fundoo.user.exception.custom.UserException;
import com.bridgelabz.fundoo.user.response.Response;

@ControllerAdvice
public class GlobalExceptionHelper {

	/**
	 * Purpose: To create a global exception handler for userService .
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
	 * Purpose: to create a global exception handler for LoginException custom
	 *          exception. 
	 * @param   ex the exception message .
	 * @return  ResponseEntity showing Http status , exception message 
	 *          and object.
	 */
	@ExceptionHandler(UserException.class)
	public final ResponseEntity<Response> userException(UserException ex) {
		Response responseMessage = new Response(400, ex.getMessage(), null);

		return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
	}


}
