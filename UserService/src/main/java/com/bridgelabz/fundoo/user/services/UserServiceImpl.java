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

	@Override
	public User userSetPassword(String password, String token) {
		LOG.info("SetPassword Service Api");

		Claims claims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();

		User user = userRepository.findAll().stream().filter(i -> i.getEmail().equals(claims.getSubject())).findAny()
				.orElse(null);

		if (user != null) {
			user.setPassword(userConfiguration.passwordEncoder().encode(password));

			return userRepository.save(user);

		} else {
			throw new SetPasswordException("unable to set the password");
		}

	}

	@Override
	public void sendMail(String email) {
		LOG.info("sendMail Service Api");

		String token = Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "verifykey").compact();
		String subject = "Verification mail for user Authorization";
		SimpleMailMessage simpleMailMessage = userUtility.mailSender(email, token, subject);
		emailSender.send(simpleMailMessage);

	}

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
