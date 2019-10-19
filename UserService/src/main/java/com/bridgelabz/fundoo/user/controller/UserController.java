/******************************************************************************
 
 *  Purpose:  
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   18-10-2019
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

	@PutMapping("/login")
	public String userLogin(@RequestBody LoginDTO login) {
		// System.out.println("in login");

		if (userService.userLogin(login)) {
			return "login successful";
		} else {
			return "Login Failed";

		}
	}

	@PostMapping("/register")
	public boolean userRegister(@RequestBody RegisterDTO register) {
		// System.out.println("in register");
		return userService.userRegister(register);
	}

	@PutMapping("/forgotpassword")
	public boolean userForgotPassword(@RequestHeader String email) {

		return userService.userForgotPassword(email);

	}
	
	@PutMapping("/setpassword/{token}")
	public void userSetPassword(@RequestBody SetPasswordDTO setPasswordDTO,@PathVariable(name="token") String token) {
		userService.userSetPassword(setPasswordDTO.getPassword(), token);
	}

}
