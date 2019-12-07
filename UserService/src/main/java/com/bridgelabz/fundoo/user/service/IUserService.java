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

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

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

	
	public Response userSetPassword(SetPasswordDTO setPasswordDTO ,String token);
	
	

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
	 * Purpose: Method for verifying the user in which the user get authorization to
	 * use UserSevices.
	 * 
	 * @param token to verify the user and granting him/her the authorization to
	 *              access the userServices.
	 * @return Response which contains the response of the method.
	 */
	public Response isVerified(String token);
	
	/**
	 * Purpose: Method for adding profile picture to user of UserService using multi part
	 *          file
	 * @param emailIdToken
	 * @param file containing image for adding profile picture
	 * @return Response which contains the response of the method
	 * @throws IOException 
	 */
	public Response addProfilePic( String emailIdToken , MultipartFile file ) throws IOException;
	

	 /**
	 *  Purpose: Method for removing profile picture of user of UserService
	 * @param emailIdToken to verify the user and granting him/her the authorization to
	 *              access the userServices.
	 * @return Response which contains the response of the method
	 * @throws IOException
	 */
	public Response removeProfilePic(String emailIdToken) throws IOException;
	
	
	/**
	 * Purpose: Method for updating profile picture of user of userService
	 * @param emailIdToken to verify the user and granting him/her the authorization to
	 *              access the userServices.
	 * @param file containing image  for updating profile picture
	 * @return Response which contains the response of the method
	 * @throws IOException
	 */
	public Response updateProfilePic( String emailIdToken , MultipartFile file)throws IOException;
	
	
	/**
	 * Purpose: Method for getting profile picture of particular user
	 * @param emailIdToken  to verify the user and granting him/her the authorization to
	 *              access the userServices.
	 * @return Response which contains the response of the method
	 */
	public Response getProfilePic(String emailIdToken);
	
	/**
	 * Purpose: Method for getting all user present in the database
	 * @return  Response which contains the response of the method
	 */
	public Response getAllUser();

}
