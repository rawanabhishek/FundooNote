/******************************************************************************
 
 *  Purpose: To create a Eureka Discovery server Application using Spring Boot 
 *           and creating and adding user service and note service to the 
 *           discovery server
 *          
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   09-12-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FundooServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooServerApplication.class, args);
	}

}
