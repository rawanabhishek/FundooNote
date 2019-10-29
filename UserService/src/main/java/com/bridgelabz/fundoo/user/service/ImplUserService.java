/******************************************************************************
 
 *  Purpose: This is a service class for userService implementing userService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.service;



import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.user.configuration.UserConfiguration;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.dto.SetPasswordDTO;
import com.bridgelabz.fundoo.user.exception.custom.ForgotPasswordException;
import com.bridgelabz.fundoo.user.exception.custom.IsVerifiedException;
import com.bridgelabz.fundoo.user.exception.custom.LoginException;
import com.bridgelabz.fundoo.user.exception.custom.SetPasswordException;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.user.response.Response;
import com.bridgelabz.fundoo.user.utility.CommonFiles;
import com.bridgelabz.fundoo.user.utility.TokenUtility;
import com.bridgelabz.fundoo.user.utility.UserUtility;



@Service
public class ImplUserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConfiguration userConfiguration;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private UserUtility userUtility;

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
			throw new LoginException(CommonFiles.LOGIN_FAILED);
		}
        String token=TokenUtility.tokenBuild(login.getEmail());
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
			throw new LoginException(register.getEmail() + CommonFiles.REGISTER_EMAIL_FOUND);

		}

		sendMail(register.getEmail(), CommonFiles.VERIFY_URL);
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
			throw new ForgotPasswordException(email + CommonFiles.EMAIL_FAILED);

		}

		SimpleMailMessage simpleMailMessage = userUtility.mailSender(email, TokenUtility.tokenBuild(email),
				CommonFiles.EMAIL_SUBJECT_SETPASSWORD, CommonFiles.SET_PASSWORD_URL);
		emailSender.send(simpleMailMessage);
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
	public Response userSetPassword(SetPasswordDTO setPasswordDTO) {
		LOG.info(CommonFiles.SERVICE_SETPASSWORD_METHOD);

		

		User user = userRepository.findAll().stream().filter(i -> i.getEmail().equals
				(TokenUtility.tokenParser(setPasswordDTO.getToken()))).findAny()
				.orElse(null);

		if (user == null && !(setPasswordDTO.getPassword().equals(setPasswordDTO.getConfirmPassword()))) {

			throw new SetPasswordException(CommonFiles.SET_PASSWORD_FAILED);

		}
		user.setPassword(userConfiguration.passwordEncoder().encode(setPasswordDTO.getPassword()));

		return new Response(200, CommonFiles.SET_PASSWORD_SUCCESS, userRepository.save(user));

	}

	/**
	 * Purpose: Method for send mail to a particular mailId
	 * 
	 * @param email to which the mail has to be send
	 */
	@Override
	public void sendMail(String email, String url) {
		LOG.info(CommonFiles.SERVICE_SENDMAIL_METHOD);

		String token = TokenUtility.tokenBuild(email);

		SimpleMailMessage simpleMailMessage = userUtility.mailSender(email, token, CommonFiles.EMAIL_SUBJECT_VERIFY,
				url);
		emailSender.send(simpleMailMessage);

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

	
		User user = userRepository.findAll().stream().filter(i -> i.getEmail().
				equals(TokenUtility.tokenParser(token))).findAny()
				.orElse(null);

		if (user == null) {
			throw new IsVerifiedException(CommonFiles.USER_NOT_VERIFIED);

		}
		user.setVerified(true);

		return new Response(200, CommonFiles.USER_VERIFIED, userRepository.save(user));

	}

}
