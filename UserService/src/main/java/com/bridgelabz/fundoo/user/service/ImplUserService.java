/******************************************************************************
 
 *  Purpose: This is a service class for userService implementing userService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.user.configuration.UserConfiguration;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.dto.SetPasswordDTO;

import com.bridgelabz.fundoo.user.exception.custom.UserException;
import com.bridgelabz.fundoo.user.model.EmailData;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.utility.CommonFiles;
import com.bridgelabz.fundoo.user.utility.TokenUtility;

@Service
public class ImplUserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConfiguration userConfiguration;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public static final Logger LOG = LoggerFactory.getLogger(ImplUserService.class);

	/**
	 * Purpose: Method for user login into the UserService.
	 * 
	 * @param login object containing user emailId and user password (in encoded
	 *              format ).
	 * @return Response which contains the response of the method .
	 */
	@Override
	public Response userLogin(LoginDTO login) {
		LOG.info(CommonFiles.SERVICE_LOGIN_METHOD);

		if (!userRepository.findAll().stream()
				.anyMatch(i -> i.getEmail().equals(login.getEmail())
						&& userConfiguration.passwordEncoder().matches(login.getPassword(), i.getPassword())
						&& i.isVerified())) {
			throw new UserException(CommonFiles.LOGIN_FAILED);
		}
		String token = TokenUtility.tokenBuild(login.getEmail());
		return new Response(200, CommonFiles.LOGIN_SUCCESS, token);

	}

	/**
	 * Purpose: Method for registration of new user into UserService.
	 * 
	 * @param register object contains users firstName, lastName ,contact , emailId
	 *                 and password (in encoded format) and then mapping it to user
	 *                 Model .
	 * @return Response which contains the response of the method.
	 */
	@Override
	public Response userRegister(RegisterDTO register) {
		LOG.info(CommonFiles.SERVICE_REGISTER_METHOD);

		if (!userRepository.findByEmail(register.getEmail()).isEmpty()) {
			throw new UserException(register.getEmail() + CommonFiles.REGISTER_EMAIL_FOUND);

		}

		EmailData emailData = new EmailData();
		emailData.setEmail(register.getEmail());
		emailData.setToken(TokenUtility.tokenBuild(register.getEmail()));
		emailData.setMessage(CommonFiles.EMAIL_SUBJECT_VERIFY);
		emailData.setPath(CommonFiles.VERIFY_URL);

		rabbitTemplate.convertAndSend(CommonFiles.ROUTING_KEY, emailData);

		register.setPassword(userConfiguration.passwordEncoder().encode(register.getPassword()));
		User user = modelMapper.map(register, User.class);
		userRepository.save(user);

		return new Response(200, CommonFiles.REGISTER_SUCCESS, userRepository.save(user));

	}

	/**
	 * Purpose: Method for send mail to the user emailId if he/she has forgot
	 * his/her password.
	 * 
	 * @param email to which the mail has to send the mail will contains a link to
	 *              reset new password.
	 * @return Response which contains the response of the method.
	 */
	@Override
	public Response userForgotPassword(String email) {
		LOG.info(CommonFiles.SERVICE_FORGOTPASSWORD_METHOD);

		if (userRepository.findByEmail(email) == null) {
			throw new UserException(email + CommonFiles.EMAIL_FAILED);

		}
		EmailData emailData = new EmailData();
		emailData.setEmail(email);
		emailData.setToken(TokenUtility.tokenBuild(email));
		emailData.setMessage(CommonFiles.EMAIL_SUBJECT_SETPASSWORD);
		emailData.setPath(CommonFiles.SET_PASSWORD_URL);
		rabbitTemplate.convertAndSend(CommonFiles.ROUTING_KEY, emailData);

		return new Response(200, CommonFiles.EMAIL_SUCCESS, true);

	}

	/**
	 * Purpose: Method for resetting the password of particular user.
	 * 
	 * @param password the new password which user to set for his id .
	 * @param token    for checking the user is authorized or not for setting new
	 *                 password.
	 * @return Response which contains the response of the method.
	 */
	@Override
	public Response userSetPassword(SetPasswordDTO setPasswordDTO, String token) {
		LOG.info(CommonFiles.SERVICE_SETPASSWORD_METHOD);

		User user = userRepository.findAll().stream().filter(i -> i.getEmail().equals(TokenUtility.tokenParser(token)))
				.findAny().orElse(null);

		if (user == null && !(setPasswordDTO.getPassword().equals(setPasswordDTO.getConfirmPassword()))) {

			throw new UserException(CommonFiles.SET_PASSWORD_FAILED);

		}
		user.setPassword(userConfiguration.passwordEncoder().encode(setPasswordDTO.getPassword()));

		return new Response(200, CommonFiles.SET_PASSWORD_SUCCESS, userRepository.save(user));

	}

	/**
	 * Purpose: Method for verifying the user in which the user get authorization to
	 * use UserSevices.
	 * 
	 * @param token to verify the user and granting him/her the authorization to
	 *              access the userServices.
	 * @return Response which contains the response of the method.
	 */
	@Override
	public Response isVerified(String token) {
		LOG.info(CommonFiles.SERVICE_ISVERIFIED_METHOD);

		User user = userRepository.findAll().stream().filter(i -> i.getEmail().equals(TokenUtility.tokenParser(token)))
				.findAny().orElse(null);

		if (user == null) {
			throw new UserException(CommonFiles.USER_NOT_VERIFIED);

		}
		user.setVerified(true);

		return new Response(200, CommonFiles.USER_VERIFIED, userRepository.save(user));

	}

	/**
	 * Purpose: Method for adding profile picture to user of UserService using multi
	 * part file
	 * 
	 * @param emailIdToken
	 * @param file         containing image for adding profile picture
	 * @return Response which contains the response of the method
	 * @throws IOException
	 */
	@Override
	public Response addProfilePic(String emailIdToken, MultipartFile file) throws IOException {
		String email = TokenUtility.tokenParser(emailIdToken);
		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			throw new UserException(CommonFiles.USER_FOUND_FAILED);
		}

		byte[] bytes = file.getBytes();
		Path path = Paths.get(CommonFiles.PROFILE_PIC_PATH + file.getOriginalFilename());
		Files.write(path, bytes);

		user.setProfilePic(CommonFiles.PROFILE_PIC_PATH + file.getOriginalFilename());

		return new Response(200, CommonFiles.PHOTO_ADDED_SUCCESS, userRepository.save(user));

	}

	/**
	 * Purpose: Method for removing profile picture of user of UserService
	 * 
	 * @param emailIdToken to verify the user and granting him/her the authorization
	 *                     to access the userServices.
	 * @return Response which contains the response of the method
	 * @throws IOException
	 */
	@Override
	public Response removeProfilePic(String emailIdToken) throws IOException {
		String email = TokenUtility.tokenParser(emailIdToken);
		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			throw new UserException(CommonFiles.USER_FOUND_FAILED);
		}
		Path path = Paths.get(user.getProfilePic());

		Files.delete(path);
		user.setProfilePic(null);
		return new Response(200, CommonFiles.PHOTO_REMOVED_SUCCESS, userRepository.save(user));
	}

	/**
	 * Purpose: Method for updating profile picture of user of userService
	 * 
	 * @param emailIdToken to verify the user and granting him/her the authorization
	 *                     to access the userServices.
	 * @param file         containing image for updating profile picture
	 * @return Response which contains the response of the method
	 * @throws IOException
	 */
	@Override
	public Response updateProfilePic(String emailIdToken, MultipartFile file) throws IOException {
		String email = TokenUtility.tokenParser(emailIdToken);
		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			throw new UserException(CommonFiles.USER_FOUND_FAILED);
		}
		if(user.getProfilePic()==null) {
			throw new UserException(CommonFiles.NO_PROFILE_PIC);
		}
		byte[] bytes = file.getBytes();
		Path path = Paths.get(CommonFiles.PROFILE_PIC_PATH + file.getOriginalFilename());
		Files.write(path, bytes);

		user.setProfilePic(CommonFiles.PROFILE_PIC_PATH + file.getOriginalFilename());

		return new Response(200, CommonFiles.PHOTO_UPDATED_SUCCESS, userRepository.save(user));
	}

}
