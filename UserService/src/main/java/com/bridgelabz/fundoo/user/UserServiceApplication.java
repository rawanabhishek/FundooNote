/******************************************************************************
 
 *  Purpose: To create a UserService Application using Spring Boot and creating
 *           user API login , register ,forgotPassword and setPassword in which
 *           we will need to send mail to the use using java mail sender while 
 *           Registration  and forgot password functions.
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   20-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
@EnableCaching
@EnableEurekaClient

public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	

}
