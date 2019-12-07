/******************************************************************************
 
 *  Purpose: To create a NoteService Application using Spring Boot and creating
 *           user API addNote , deleteNote ,getnotes ,updateNote , pin unpin 
 *           note ,archive notes ,trash notes .
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   24-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;




@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
@EnableCaching
@EnableEurekaClient
public class NoteServiceApplication {
	
	

	public static void main(String[] args) {
		SpringApplication.run(NoteServiceApplication.class, args);
		
		
		
	}

}
