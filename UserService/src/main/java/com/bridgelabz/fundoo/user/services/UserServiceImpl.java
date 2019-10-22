/******************************************************************************
 
 *  Purpose: This is a service class for userService implementing userService
 *           interface methods 
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.services;

import java.util.Date;

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
import com.bridgelabz.fundoo.user.utility.UserUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService {

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

	public static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	
	/**
	 * Purpose: Method for user login  into the UserService.
	 * @param   login object containing user emailId and user password (in encoded 
	 *          format ).
	 * @return  A login success message on success or else returns a failed message .
	 */
	@Override
	public String userLogin(LoginDTO login) {
		LOG.info("Login Service Api");

		if (userRepository.findAll().stream()
				.anyMatch(i -> i.getEmail().equals(login.getEmail())
						&& userConfiguration.passwordEncoder().matches(login.getPassword(), i.getPassword())
						&& i.isVerified())) {
			return "Login successful";
		} else {
			throw new LoginException("logging failed");
		}

	}
	
	

	
	/**
	 * Purpose: Method for registration of new user into UserService.
	 * @param   register object contains users firstName, lastName ,contact , emailId and 
	 *          password (in encoded format) and then mapping it to user Model .
	 * @return  a message saying weather the user is verified or not.
	 */
	@Override
	public User userRegister(RegisterDTO register) {
		LOG.info("Register Service Api");

		if (userRepository.findByEmail(register.getEmail()).isEmpty()) {
			System.out.println("register impl");
			sendMail(register.getEmail());
			register.setPassword(userConfiguration.passwordEncoder().encode(register.getPassword()));
			User user = modelMapper.map(register, User.class);
			 return userRepository.save(user);

		} else {
			throw new LoginException(register.getEmail() + " already present in the database");
		}

	}
	
	

	
	/**
	 * Purpose: Method for send mail to the user emailId if he/she has forgot his/her 
	 *          password.
	 * @param   email to which the mail has to send the mail will contains a link to  reset 
	 *          new password.
	 * @return  A message saying that the mail is send or not.
	 */
	@Override
	public String userForgotPassword(String email)  {
		LOG.info("ForgotPassword Service Api");

		if (userRepository.findByEmail(email) != null) {
			String token = Jwts.builder().setSubject(email).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretKey").compact();
			String subject = "Link For resetting password";
			SimpleMailMessage simpleMailMessage = userUtility.mailSender(email, token, subject);
			emailSender.send(simpleMailMessage);
			return "Email send to the user successfully";

		} else {
			throw new ForgotPasswordException(email + " no user found with this email id ");
		}

	}
	
	
	

	
	/**
	 * Purpose: Method for resetting the password of particular user.
	 * @param   password the new password which user to set for his id .
	 * @param   token for checking the user is authorized or not for setting new password.
	 * @return  setPassWordDTO Object containing user New Password Details.
	 */
	@Override
	public User userSetPassword(SetPasswordDTO setPasswordDTO, String token) {
		LOG.info("SetPassword Service Api");

		Claims claims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
		
		User user = userRepository.findAll().stream().filter(i -> i.getEmail().equals(claims.getSubject())).findAny()
				.orElse(null);

		if (user != null && setPasswordDTO.getPassword().equals(setPasswordDTO.getConfirmPassword())) {
			user.setPassword(userConfiguration.passwordEncoder().encode(setPasswordDTO.getPassword()));

			return userRepository.save(user);

		} else {
			throw new SetPasswordException("unable to set the password");
		}

	}
	
	

	/**
	 * Purpose: Method for send mail to a particular mailId
	 * @param   email to which the mail has to be send 
	 */
	@Override
	public void sendMail(String email) {
		LOG.info("sendMail Service Api");

		String token = Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "verifykey").compact();
		String subject = "Verification mail for user Authorization";
		SimpleMailMessage simpleMailMessage = userUtility.mailSender(email, token, subject);
		emailSender.send(simpleMailMessage);

	}

	
	/**
	 * Purpose: Method for verifying the user in which the user get authorization to 
	 *          use UserSevices.
	 * @param   token to verify the user and granting him/her the authorization to access
	 *          the userServices.
	 * @return  weather the user is verified or not.
	 */
	@Override
	public User isVerified(String token)  {
		LOG.info("Verified Service Api");

		Claims claims = Jwts.parser().setSigningKey("verifykey").parseClaimsJws(token).getBody();
		User user = userRepository.findAll().stream().filter(i -> i.getEmail().equals(claims.getSubject())).findAny()
				.orElse(null);

		if (user != null) {
			user.setVerified(true);
			return userRepository.save(user);

		} else {
			throw new IsVerifiedException("User not verified");
		}

	}

}
