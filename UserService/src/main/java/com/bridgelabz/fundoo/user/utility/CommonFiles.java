 
/******************************************************************************
 *  Purpose: To create a  Common File class which holds all the 
 *           static references which can be used in user Service
 *
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   23-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.utility;

public class CommonFiles {
	
	//userService related message
	public static final String LOGIN_SUCCESS="Login successful";
	public static final String LOGIN_FAILED="logging failed";
	public static final String REGISTER_EMAIL_FOUND=" emailId already existed";
	public static final String REGISTER_SUCCESS="Registeration Successful";
	public static final String EMAIL_SUCCESS="Email send to the user successfully";
	public static final String EMAIL_FAILED="no user found with this email id ";
	public static final String SET_PASSWORD_FAILED="unable to set the password";
	public static final String SET_PASSWORD_SUCCESS="Password set successfully";
	public static final String USER_NOT_VERIFIED="User not verified";
	public static final String USER_VERIFIED="User  verified successfully";
	public static final String PHOTO_UPDATED_SUCCESS = "profile pic updated successfully";
	public static final String PHOTO_ADDED_SUCCESS = "photo added suvvessfully";
	public static final String PHOTO_REMOVED_SUCCESS = "photo removed successfully";
	public static final String USER_FOUND_FAILED = "user not found";
	public static final String PROFILE_PIC_PATH = "/home/admin1/FundooNote/UserService/src/main/java/com/bridgelabz/fundoo/user/ImageFiles/";
	public static final String NO_PROFILE_PIC = "There is no profile pic to update";
	public static final String PATH_FEATCHED="Profile pic path fetched successfully";
	
	
	//Email Subject messages
	public static final String EMAIL_SUBJECT_SETPASSWORD="Link For resetting password";
	public static final String EMAIL_SUBJECT_VERIFY="Verification mail for user Authorization";
	
	//token key message
	public static final String TOKEN_KEY="secretKey";
	
	//rabbit mq
	public static final String ROUTING_KEY="my.queue.key";
	public static final String QUEUE_EXCHANGE="my_queue_exchange";
	public static final String MAIL_SENDER_METHOD="mailSender";
	
	
	//url message for different functionality of user service
	public static final String VERIFY_URL="http://localhost:4200/verify/";	
	public static final String SET_PASSWORD_URL="http://localhost:4200/setpassword?token=";

	
	//User service Methods messages
	public static final String SERVICE_LOGIN_METHOD="Login service method";
	public static final String SERVICE_FORGOTPASSWORD_METHOD="Forgot Password service method";
	public static final String SERVICE_SETPASSWORD_METHOD="Set password service method";
	public static final String SERVICE_REGISTER_METHOD="Register service method";
	public static final String SERVICE_ISVERIFIED_METHOD="Is verified service method";
	public static final String SERVICE_SENDMAIL_METHOD="Send mail service method";
	
	
	
	//User controller messages
	public static final String CONTROLLER_LOGIN_METHOD="Login controller method";
	public static final String CONTROLLER_FORGOTPASSWORD_METHOD="Forgot Password controller method";
	public static final String CONTROLLER_SETPASSWORD_METHOD="Set password controller method";
	public static final String CONTROLLER_REGISTER_METHOD="Register controller method";
	public static final String CONTROLLER_ISVERIFIED_METHOD="Is verified controller method";
	public static final String GET_ALL_USER = "User fetched success ";
	
	
	

}
