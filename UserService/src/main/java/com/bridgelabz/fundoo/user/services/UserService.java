/******************************************************************************
 
 *  Purpose:  
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   18-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.services;

import com.bridgelabz.fundoo.user.model.User;

public interface UserService {
	
	public boolean userLogin(String email, String password);
	public boolean userEmailValidate(String email);
	public boolean userRegister(User user);
	public void userForgotPassword(String email);
	public void userSetPassword(String  password);
	
	

}
