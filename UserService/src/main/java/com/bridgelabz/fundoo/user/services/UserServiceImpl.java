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
	public boolean userLogin(LoginDTO login) {

		return userRepository.findAll().stream().anyMatch(i -> i.getEmail().equals(login.getEmail())
				&& userConfiguration.passwordEncoder().matches(login.getPassword(), i.getPassword()) && i.isVerified());

	}

	@Override
	public boolean userRegister(RegisterDTO register) {

		if (!userEmailValidate(register.getEmail())) {
			System.out.println("register impl");
			sendMail(register.getEmail());
			register.setPassword(userConfiguration.passwordEncoder().encode(register.getPassword()));
			User user = modelMapper.map(register, User.class);
			userRepository.save(user);
			return true;
		}
		return false;

	}

	@Override
	public boolean userForgotPassword(String email) {
		if (userEmailValidate(email)) {
			String token = Jwts.builder().setSubject(email).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretKey").compact();

			SimpleMailMessage simpleMailMessage = userUtility.forgotMail(email, token);
			emailSender.send(simpleMailMessage);
			return true;

		}
		return false;

	}

	@Override
	public void userSetPassword(String password ,String token) {
		Claims claims=Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
		
		User user=userRepository.findAll().stream().filter(i -> i.getEmail().equals(claims.getSubject())).findAny().orElse(null);
		user.setPassword(userConfiguration.passwordEncoder().encode(password));
		
		userRepository.save(user);

		
	}

	@Override
	public boolean userEmailValidate(String email) {
		//return userRepository.findById(email);
		return (userRepository.findAll().stream().anyMatch(i -> i.getEmail().equals(email)));
	

	}

	@Override
	public void sendMail(String email) {
		String token = Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "verifykey").compact();
		
		SimpleMailMessage simpleMailMessage = userUtility.verificationMail(email, token);
		emailSender.send(simpleMailMessage);
		
		
		
	}

	@Override
	public boolean isVerified(String token) {
		Claims claims=Jwts.parser().setSigningKey("verifykey").parseClaimsJws(token).getBody();
	User user=userRepository.findAll().stream().filter(i-> i.getEmail().equals(claims.getSubject())).findAny().orElse(null);	
		
		if(user!=null) {
			user.setVerified(true);
			userRepository.save(user);
			return true;
		}
		
		return false;
	}
	
	

}
