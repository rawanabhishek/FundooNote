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

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bridgelabz.fundoo.user.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	

	/**
	 * Purpose: creating a userLogin controller which will fetch the request body 
	 *          and send it to the service.
	 * @param   login object containing user login credentials.
	 * @return  login successful if the user has been logged in or else returns 
	 *          login false.
	 */
	@PutMapping("/login")
	public String userLogin(@RequestBody LoginDTO login) {

		if (userService.userLogin(login)) {
			return "login successful";
		} else {
			return "Login Failed";

		}
	}

	/**
	 * Purpose: creating a userRegister controller which will fetch the request 
	 *          body and send it to the service.
	 * @param   register object containing user registration details .
	 * @return  true if the registration is done or else return false.
	 */
	@PostMapping("/register")
	public boolean userRegister(@RequestBody RegisterDTO register) {

		return userService.userRegister(register);
	}

	/**
	 * Purpose: creating a userRegister controller which will fetch the request 
	 *          header and send it to the service.
	 * @param   email object containing user email details.
	 * @return  true if the forgotPassword mail has been send to the user or
	 *          else return false.
	 */
	@PutMapping("/forgotpassword")
	public boolean userForgotPassword(@RequestHeader String email) {

		return userService.userForgotPassword(email);

	}

	/**
	 * Purpose: creating a setPassword controller which will fetch the request 
	 *          body and send it to the service.
	 * @param   setPasswordDTO object containing the user new password.
	 * @param   token for authorization to check the user has authority for 
	 *          to setPassword.
	 */
	@PutMapping("/setpassword/{token}")
	public void userSetPassword(@RequestBody SetPasswordDTO setPasswordDTO,
			@PathVariable(name = "token") String token) {
		userService.userSetPassword(setPasswordDTO.getPassword(), token);
	}

	/**
	 * Purpose: creating a userVerification controller which will fetch the 
	 *          the pathVariable  and send it to the service.
	 * @param   token for authorization to check the user has authority for 
	 *          Verifying the account.
	 */
	@PutMapping("/verify/{token}")
	public void userVerfication(@PathVariable(name = "token") String token) {
		userService.isVerified(token);

	}

}
