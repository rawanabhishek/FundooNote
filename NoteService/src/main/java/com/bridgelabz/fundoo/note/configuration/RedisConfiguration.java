/******************************************************************************
 
 *  Purpose: A configuration class which holds all the configuration
 *  		 related to the redis  catching and Create the bean
 *           for the UserService application using Bean Annotation .
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   05-11-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.note.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	
	@Bean
	RedisTemplate<String , Object> redisTemplate(){
		RedisTemplate<String , Object> redisTemplate =new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
			
	}

}
