/******************************************************************************
 
 *  Purpose: A configuration class which holds all the configuration
 *  		 related to the RabbitMq and Create the bean
 *           for the rabbitMq using Bean Annotation .
 *  		
 *  @author  Abhishek Rawat
 *  @version 1.0
 *  @since   04-11-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoo.user.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoo.user.utility.CommonFiles;
import com.bridgelabz.fundoo.user.utility.EmailSender;

@Configuration
public class RabbitMqConfiguration {
	
	
	@Bean
	public ConnectionFactory connectionFactory() {
	CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
	cachingConnectionFactory.setUsername("rawan");
	cachingConnectionFactory.setPassword("rawan");
	return cachingConnectionFactory;
	}

	

	@Bean
	Queue queue() {
		return new Queue(CommonFiles.ROUTING_KEY, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(CommonFiles.QUEUE_EXCHANGE);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(CommonFiles.ROUTING_KEY);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter messageListenerAdapter) {

		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(CommonFiles.ROUTING_KEY);
		container.setMessageListener(messageListenerAdapter);
		return container;
	}
	
	@Bean
	EmailSender sender() {
		return new EmailSender();
	}

	@Bean
	MessageListenerAdapter myQueueListener(EmailSender emailSender) {
		return new MessageListenerAdapter(emailSender, CommonFiles.MAIL_SENDER_METHOD);
	}
	

}
