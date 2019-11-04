package com.bridgelabz.rabbitmq.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.rabbitmq.UserUtility;

@Component
public class MessageListenerImpl  implements MessageListener{

	
	@Autowired
	private JavaMailSender mail;
	
	@Override
	public String onMessage(String message) {
		
		
		System.out.println(new Date());
		  try {
		   Thread.sleep(5000);
		  } catch (InterruptedException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  
		  SimpleMailMessage simpleMailMessage = UserUtility.mailSender(message);
			mail.send(simpleMailMessage);
		  System.out.println("Message Received:"+message);
		  System.out.println(new Date());
		  return "Message Received:"+message;
		 }
		
	}


