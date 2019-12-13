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

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.dto.SetPasswordDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.service.IUserService;
import com.bridgelabz.fundoo.user.utility.CommonFiles;
import com.bridgelabz.fundoo.user.utility.TokenUtility;



@RestController
@RequestMapping("/user")
@CrossOrigin
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
	public ResponseEntity<Response> userSetPassword(@Valid @RequestBody SetPasswordDTO setPasswordDTO ,@RequestHeader String token) {

		LOG.info(CommonFiles.CONTROLLER_SETPASSWORD_METHOD);
		return new ResponseEntity<>(new Response(200, CommonFiles.SET_PASSWORD_SUCCESS,userService.userSetPassword(setPasswordDTO ,TokenUtility.tokenParser(token))), HttpStatus.OK);
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
		return new ResponseEntity<>(userService.isVerified(TokenUtility.tokenParser(token)), HttpStatus.OK);

	}
	
	/**
	 * Purpose: Creating add profile picture controller which will add profile picture
	 *          of user to the note
	 * @param emailIdToken for authorization to check the user has authority for Verifying
	 *              the account.
	 * @param file containing image for adding profile picture to the user 
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/addprofilepic")
	public ResponseEntity<Response> addProfilePic( @RequestHeader("emailIdToken")
			String emailIdToken  , @RequestParam("picture") MultipartFile file) throws IOException{
		
		return new ResponseEntity<>(new Response(200, CommonFiles.PHOTO_ADDED_SUCCESS,userService.addProfilePic(TokenUtility.tokenParser(emailIdToken) , file)),HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: Creating remove profile picture controller which will remove profile picture
	 *          of user from note 
	 * @param emailIdToken for authorization to check the user has authority for Verifying
	 *              the account.
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity..
	 * @throws IOException
	 */
	@PutMapping("/removeprofilepic")
	public ResponseEntity<Response> removeProfilePic( @RequestHeader("emailIdToken")
			String emailIdToken  ) throws IOException{
		
		return new ResponseEntity<>(new Response(200, CommonFiles.PHOTO_REMOVED_SUCCESS,userService.removeProfilePic(TokenUtility.tokenParser(emailIdToken))),HttpStatus.OK);
	}
	
	/**
	 * Purpose: Creating update profile picture controller which will update the profile picture
	 *          of user
	 * @param emailIdToken for authorization to check the user has authority for Verifying
	 *              the account.
	 * @param file containing image for updating user profile picture
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 * @throws IOException
	 */
	@PutMapping("/updateprofilepic")
	public ResponseEntity<Response> updateProfilePic(@RequestHeader("emailIdToken")
			String emailIdToken ,@RequestParam("picture") MultipartFile file ) throws IOException{
		   System.out.println("Update profile pic controller");
		return new ResponseEntity<>(new Response(200, CommonFiles.PHOTO_UPDATED_SUCCESS,userService.updateProfilePic(TokenUtility.tokenParser(emailIdToken) ,file)),HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: To fetch the profile picture of particular users
	 * @param emailIdToken  for authorization to check the user has authority for Verifying
	 *              the account.
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 * @throws IOException
	 */
	@GetMapping("/profilepic")
	public ResponseEntity<Response> getProfilePic(@RequestHeader()
	String emailIdToken) throws IOException{

     return new ResponseEntity<>(userService.getProfilePic(TokenUtility.tokenParser(emailIdToken)),HttpStatus.OK);
}
	
	/**
	 * Purpose: To fetch all users from database
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 */
	@GetMapping("/alluser")
	public ResponseEntity<Response> getAllUser(){
		return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
	}
	
	
	/**
	 * Purpose: To fetch particular user by emailId
	 * @param emaiIdToken for authorization to check the user has authority for Verifying
	 *              the account.
	 * @return  ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 */
	@GetMapping("/userbyid")
	public User getUser(@RequestParam String emailIdToken){
		System.out.println("get user by id controller");
		return  userService.getUser(TokenUtility.tokenParser(emailIdToken));
	}
	
	
	/**
	 * Purpose: To fetch the profile picture of particular collaborator
	 * @param emailIdToken  for authorization to check the user has authority for Verifying
	 *              the account.
	 * @return ResponseEntity which is holding the user object and HttpStatus in
	 *         that entity.
	 * @throws IOException
	 */
	@GetMapping("/collaboratorprofilepic")
	public ResponseEntity<Response> getCollaboratorProfilePic(@RequestHeader()
	String collaboratorEmailId) throws IOException{

     return new ResponseEntity<>(userService.getProfilePic(collaboratorEmailId),HttpStatus.OK);
}
	
	

}
