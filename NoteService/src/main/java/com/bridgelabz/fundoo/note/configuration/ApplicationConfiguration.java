/******************************************************************************
 
 *  Purpose: A configuration class which holds all the configuration
 *  		 related to the UserService application and Create the bean
 *           for the UserService application using Bean Annotation .
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
	
	/**
	 * Purpose:  A method which is created to achieve the ModelMapper
	 *           feature in  the NoteService application which is used to map POJO
	 *           and DTO Objects.                     
	 * @return   The object of ModelMapper class.
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();

	}

}
