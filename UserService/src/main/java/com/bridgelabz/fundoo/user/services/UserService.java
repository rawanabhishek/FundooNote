/******************************************************************************
 
 *  Purpose:  
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   18-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.services;


import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;



public interface UserService {
	
	
	public boolean userEmailValidate(String email);


	public void userSetPassword(String password, String token);
	public boolean userLogin(LoginDTO login);
	boolean userRegister(RegisterDTO register);




	boolean userForgotPassword(String email);


	
	
	

}
