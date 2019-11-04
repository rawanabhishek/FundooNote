package com.bridgelabz.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.rabbitmq.MessageProducer;
import com.bridgelabz.rabbitmq.listener.MessageListenerImpl;

@RestController
@RequestMapping("/producer")
public class ProducerController {
	   @Autowired
	    private MessageProducer messageProducer;
	   
	   @Autowired
	   private MessageListenerImpl listener;
	    
	 @GetMapping
	 public String produce(@RequestParam String message) {
	  messageProducer.sendMessage(message);
	  return listener.onMessage(message);
	    
	 }

	

}
