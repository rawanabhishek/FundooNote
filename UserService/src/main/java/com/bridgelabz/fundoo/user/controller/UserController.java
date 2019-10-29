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

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.dto.SetPasswordDTO;

import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.service.IUserService;
import com.bridgelabz.fundoo.user.utility.CommonFiles;



@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	public static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	/**
	 * Purpose: Creating a userLogin controller which will fetch the request body
	 * and send it to the service.
	 * 
	 * @param login object containing user login credentials.
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 * 
	 */
	@PutMapping("/login")
	public ResponseEntity<Response> userLogin(@Valid @RequestBody LoginDTO login) {
		
		LOG.info(CommonFiles.CONTROLLER_LOGIN_METHOD);
		return new ResponseEntity<>(userService.userLogin(login), HttpStatus.OK);

	}

	/**
	 * Purpose: Creating a userRegister controller which will fetch the request body
	 * and send it to the service.
	 * 
	 * @param register object containing user registration details .
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 * 
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> userRegister(@Valid @RequestBody RegisterDTO register) {
		LOG.info(CommonFiles.CONTROLLER_REGISTER_METHOD);
		return new ResponseEntity<>(userService.userRegister(register), HttpStatus.OK);
	}

	/**
	 * Purpose: Creating a userRegister controller which will fetch the request
	 * header and send it to the service.
	 * 
	 * @param email string containing user email details.
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 * 
	 */
	@PutMapping("/forgotpassword")
	public ResponseEntity<Response> userForgotPassword(@RequestHeader(name = "email") String email) {
		LOG.info(CommonFiles.CONTROLLER_FORGOTPASSWORD_METHOD);
		return new ResponseEntity<>(userService.userForgotPassword(email), HttpStatus.OK);

	}

	/**
	 * Purpose: Creating a setPassword controller which will fetch the request body
	 * and send it to the service.
	 * 
	 * @param setPasswordDTO object containing the user new password.
	 * @param tokenfor       authorization to check the user has authority for to
	 *                       setPassword.
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 * 
	 */
	@PutMapping("/setpassword")
	public ResponseEntity<Response> userSetPassword(@Valid @RequestBody SetPasswordDTO setPasswordDTO) {

		LOG.info(CommonFiles.CONTROLLER_SETPASSWORD_METHOD);
		return new ResponseEntity<>(userService.userSetPassword(setPasswordDTO), HttpStatus.OK);
	}

	/**
	 * Purpose: Creating a userVerification controller which will fetch the the
	 * pathVariable and send it to the service.
	 * 
	 * @param token for authorization to check the user has authority for Verifying
	 *              the account.
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity..
	 * 
	 */

	@PutMapping("/verify")
	public ResponseEntity<Response> userVerfication(@RequestHeader(name = "token") String token) {
		LOG.info(CommonFiles.CONTROLLER_ISVERIFIED_METHOD);
		return new ResponseEntity<>(userService.isVerified(token), HttpStatus.OK);

	}

}
