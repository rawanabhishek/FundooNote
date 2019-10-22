/******************************************************************************
 
 *  Purpose: A Class  implemented for handling the request coming from the user
 *           and Controlling it through RestController annotation using spring 
 *           boot that will handle all the request related to that user.
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/

package com.bridgelabz.fundoo.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.dto.SetPasswordDTO;

import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	public static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	/**
	 * Purpose: Creating a userLogin controller which will fetch the request body
	 *          and send it to the service.
	 * 
	 * @param   login object containing user login credentials.
	 * @return  login successful if the user has been logged in return login failed.
	 * 
	 */
	@PutMapping("/login")
	public ResponseEntity<String> userLogin(@RequestBody LoginDTO login)  {
		LOG.info("Login Controller Api");
		return new ResponseEntity<>(userService.userLogin(login), HttpStatus.OK);

	}

	/**
	 * Purpose: Creating a userRegister controller which will fetch the request body
	 *          and send it to the service.
	 * 
	 * @param   Register object containing user registration details .
	 * @return  User Object Containing User details.
	 * 
	 */
	@PostMapping("/register")
	public ResponseEntity<User> userRegister(@RequestBody RegisterDTO register) {
		LOG.info("register Controller Api");
        return new ResponseEntity<>(userService.userRegister(register), HttpStatus.OK);
	}

	/**
	 * Purpose: Creating a userRegister controller which will fetch the request
	 *          header and send it to the service.
	 * 
	 * @param   Email string containing user email details.
	 * @return  a message saying weather the mail has been send to user or not.
	 *  
	 */
	@PutMapping("/forgotpassword")
	public ResponseEntity<String> userForgotPassword(@RequestHeader(name = "email") String email)  {
		LOG.info("forgotpassword Controller Api");
		return new ResponseEntity<>(userService.userForgotPassword(email), HttpStatus.OK);

	}

	/**
	 * Purpose: Creating a setPassword controller which will fetch the request body
	 *          and send it to the service.
	 * 
	 * @param   SetPasswordDTO object containing the user new password.
	 * @param   Token for authorization to check the user has authority for
	 *          to setPassword.
	 * @return  User Object containing the new Password.
	 *  
	 */
	@PutMapping("/setpassword/{token}")
	public ResponseEntity<User> userSetPassword(@RequestBody SetPasswordDTO setPasswordDTO,
			@PathVariable(name = "token") String token)  {

		LOG.info("set Password Controller Api");
		return new ResponseEntity<>(userService.userSetPassword(setPasswordDTO, token), HttpStatus.OK);
	}

	/**
	 * Purpose: Creating a userVerification controller which will fetch the the
	 *          pathVariable and send it to the service.
	 * 
	 * @param   Token for authorization to check the user has authority for Verifying
	 *          the account.
	 * @return  User Object containing details weather the user is verified or not.
	 *  
	 */
	
	@PutMapping("/verify/{token}")
	public ResponseEntity<User> userVerfication(@PathVariable(name = "token") String token)  {
		LOG.info("verifiy Controller Api");
		return new ResponseEntity<>(userService.isVerified(token), HttpStatus.OK);

	}

}
