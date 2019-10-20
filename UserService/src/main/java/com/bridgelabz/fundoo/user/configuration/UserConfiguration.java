/******************************************************************************
 
 *  Purpose: A configuration class which holds all the configuration
 *  		 related to the UserService application and Create the bean
 *           for the UserService application using Bean Annotation .
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.configuration;



import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfiguration {

	/**
	 * Purpose:  A method which is created to achieve the BCryptPasswordEncoder 
	 *           feature in  the UserService application.
	 * @return   The object of BCryptPasswordEncoder class.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Purpose:  A method which is created to achieve the ModelMapper
	 *           feature in  the UserService application which is used to map POJO
	 *           and DTO Objects.                     
	 * @return   The object of ModelMapper class.
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();

	}



}
