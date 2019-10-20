/******************************************************************************
 
 *  Purpose: This is interface of User Service class  it handles  all the 
 *           request coming from controller and  then process   in service 
 *           implementation class where all the method of this service is 
 *           implemented .
 *  		 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.services;


import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.model.User;




public interface UserService {
	
	



	/**
	 * Purpose: method for resetting the password of particular user.
	 * @param   password the new password which user to set for his id .
	 * @param   token for checking the user is authorized or not for setting new password.
	 */
	public User userSetPassword(String password, String token);
	
	
	/**
	 * Purpose: method for login user into the UserService.
	 * @param   login object containing user emailId and user password (in encoded 
	 *          format ).
	 * @return  A login success message on success or else Failed message .
	 */
	public String userLogin(LoginDTO login);
	
	
	/**
	 * Purpose: method for registration of new user into UserService.
	 * @param   register object contains users firstName, lastName ,contact , emailId and 
	 *          password (in encoded format) and then mapping it to user Model .
	 * @return  a message saying weather the user is verified or not.
	 */
	public User userRegister(RegisterDTO register);




	/**
	 * Purpose: method for send mail to the user emailId if he/she has forgot his/her 
	 *          password.
	 * @param   email to which the mail has to send the mail will contains a link to  reset 
	 *          new password.
	 * @return  A message saying that the mail is send or not.
	 */
	public String userForgotPassword(String email);
	
	
	
	/**
	 * Purpose: method for send mail to a particular mailId
	 * @param   email to which the mail has to be send 
	 */
	public  void sendMail(String email);


	/**
	 * Purpose: method for verifying the user in which the user get authorization to 
	 *          use UserSevices.
	 * @param   token to verify the user and granting him/her the authorization to access
	 *          the userServices.
	 * @return  weather the user is verified or not.
	 */
	public User isVerified(String token);


	
	
	

}
