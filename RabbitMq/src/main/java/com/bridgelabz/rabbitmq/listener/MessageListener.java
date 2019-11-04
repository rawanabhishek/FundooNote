package com.bridgelabz.rabbitmq.listener;

public interface MessageListener {
	public String onMessage(String message);
}
