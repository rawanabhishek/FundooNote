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
package com.bridgelabz.fundoo.user.service;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.dto.SetPasswordDTO;

import com.bridgelabz.fundoo.user.response.Response;

public interface IUserService {

	/**
	 * Purpose: Method for resetting the password of particular user.
	 * 
	 * @param password the new password which user to set for his id .
	 * @param token    for checking the user is authorized or not for setting new
	 *                 password.
	 * @return Response which contains the response of the method.
	 */

	
	public Response userSetPassword(SetPasswordDTO setPasswordDTO);
	
	

	/**
	 * Purpose: Method for user login into the UserService.
	 * 
	 * @param login object containing user emailId and user password (in encoded
	 *              format ).
	 * @return Response which contains the response of the method .
	 */
	public Response userLogin(LoginDTO login);
	
	
	

	/**
	 * Purpose: Method for registration of new user into UserService.
	 * 
	 * @param register object contains users firstName, lastName ,contact , emailId
	 *                 and password (in encoded format) and then mapping it to user
	 *                 Model .
	 * @return Response which contains the response of the method.
	 */
	public Response userRegister(RegisterDTO register);
	
	

	/**
	 * Purpose: Method for send mail to the user emailId if he/she has forgot
	 * his/her password.
	 * 
	 * @param email to which the mail has to send the mail will contains a link to
	 *              reset new password.
	 * @return Response which contains the response of the method.
	 */
	public Response userForgotPassword(String email);
	
	
	

	/**
	 * Purpose: Method for send mail to a particular mailId
	 * 
	 * @param email to which the mail has to be send
	 */
	public void sendMail(String email ,String url);
	
	

	/**
	 * Purpose: Method for verifying the user in which the user get authorization to
	 * use UserSevices.
	 * 
	 * @param token to verify the user and granting him/her the authorization to
	 *              access the userServices.
	 * @return Response which contains the response of the method.
	 */
	public Response isVerified(String token);

}
