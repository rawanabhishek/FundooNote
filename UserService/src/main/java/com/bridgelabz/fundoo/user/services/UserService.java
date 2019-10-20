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




public interface UserService {
	
	
	/**
	 * Purpose: method for validating weather the user is active or not .
	 * @param   email  for sending the mail to user emailId . 
	 * @return  if the user has validate his/her emailId then its return true or 
	 *          else it returns false.
	 */
	public boolean userEmailValidate(String email);


	/**
	 * Purpose: method for resetting the password of particular user.
	 * @param   password the new password which user to set for his id .
	 * @param   token for checking the user is authorized or not for setting new password.
	 */
	public void userSetPassword(String password, String token);
	
	
	/**
	 * Purpose: method for login user into the UserService.
	 * @param   login object containing user emailId and user password (in encoded 
	 *          format ).
	 * @return  true if the credentials are correct or return false .
	 */
	public boolean userLogin(LoginDTO login);
	
	
	/**
	 * Purpose: method for registration of new user into UserService.
	 * @param   register object contains users firstName, lastName ,contact , emailId and 
	 *          password (in encoded format) and then mapping it to user Model .
	 * @return  true if the user has been successfully register or else return false.
	 */
	boolean userRegister(RegisterDTO register);




	/**
	 * Purpose: method for send mail to the user emailId if he/she has forgot his/her 
	 *          password.
	 * @param   email to which the mail has to send the mail will contains a link to  reset 
	 *          new password.
	 * @return  true if the mail has been send or else returns false.
	 */
	boolean userForgotPassword(String email);
	
	
	
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
	 * @return  true if the is verified for authorization or else return false.
	 */
	public boolean isVerified(String token);


	
	
	

}
