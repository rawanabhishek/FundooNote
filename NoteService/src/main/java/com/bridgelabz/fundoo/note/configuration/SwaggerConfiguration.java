/******************************************************************************
 
 *  Purpose: A configuration class which holds all the configuration
 *  		 related to the Swagger2 and Create the bean
 *           for the swagger using Bean Annotation .
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   30-10-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	
	
	@Bean
	public Docket  api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis
				(RequestHandlerSelectors.basePackage("com.bridgelabz.fundoo.note.controller")).build().apiInfo(metaData());
	}
	
	
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Fundoo Note",
                "1.0",
                "Terms of service",
                new Contact("Abhishek Rawat",null,"rawatabhishek2012@gmail.com" ),
               "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
	
	

 
}
