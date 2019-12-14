/******************************************************************************
 
 *  Purpose: To create a Api Gateway server Application using Spring Boot 
 *           for user service and note service 
 *        
 *          
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   09-12-2019
 *
 ******************************************************************************/
package com.birdgelabz.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
