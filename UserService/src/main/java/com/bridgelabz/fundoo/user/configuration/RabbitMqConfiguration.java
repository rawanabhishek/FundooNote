package com.bridgelabz.fundoo.user.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
	Queue queue() {
		return new Queue(CommonFiles.ROUTING_KEY, true);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(CommonFiles.QUEUE_EXCHANGE);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(CommonFiles.ROUTING_KEY);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(CommonFiles.ROUTING_KEY);
		container.setMessageListener(listenerAdapter);
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
