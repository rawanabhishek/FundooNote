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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.user.configuration.UserConfiguration;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.RegisterDTO;
import com.bridgelabz.fundoo.user.exception.UserException;
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
	private UserUtility  userUtility;
	


	@Override
	public String userLogin(LoginDTO login) {

		if( userRepository.findAll().stream().anyMatch(i -> i.getEmail().equals(login.getEmail())
				&& userConfiguration.passwordEncoder().matches(login.getPassword(), i.getPassword()) && i.isVerified())) {
			return "Login successful";
		}else {
			throw new UserException("logging failed");
		}

	}

	@Override
	public User userRegister(RegisterDTO register) {

		if (userRepository.findByEmail(register.getEmail()).isEmpty()) {
			System.out.println("register impl");
			sendMail(register.getEmail());
			register.setPassword(userConfiguration.passwordEncoder().encode(register.getPassword()));
			User user = modelMapper.map(register, User.class);
			return userRepository.save(user);
			
		}else {
			throw new UserException(register.getEmail()+" already present in the database");
		}
		 

	}

	@Override
	public String userForgotPassword(String email) {
		if (userRepository.findByEmail(email)!=null) {
			String token = Jwts.builder().setSubject(email).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretKey").compact();

			SimpleMailMessage simpleMailMessage = userUtility.forgotMail(email, token);
			emailSender.send(simpleMailMessage);
			return "Email send to the user successfully";

		}
		throw new UserException(email+ " no user found with this email id ");

	}

	@Override
	public User userSetPassword(String password ,String token) {
		Claims claims=Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
		
		User user=userRepository.findAll().stream().filter(i -> i.getEmail().equals(claims.getSubject())).findAny().orElse(null);
		
		if(user!=null) {
		user.setPassword(userConfiguration.passwordEncoder().encode(password));
		
		return userRepository.save(user);

		}
		else {
			throw new UserException("unable to set the password");
		}
	}

	

	@Override
	public void sendMail(String email) {
		String token = Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "verifykey").compact();
		
		SimpleMailMessage simpleMailMessage = userUtility.verificationMail(email, token);
		emailSender.send(simpleMailMessage);
		
		
		
	}

	@Override
	public User isVerified(String token) {
		Claims claims=Jwts.parser().setSigningKey("verifykey").parseClaimsJws(token).getBody();
	User user=userRepository.findAll().stream().filter(i-> i.getEmail().equals(claims.getSubject())).findAny().orElse(null);	
		
		if(user!=null) {
			user.setVerified(true);
			return userRepository.save(user);
			
		}else {
			throw new UserException("User not verified ");
		}
		
		
	}
	
	

}
