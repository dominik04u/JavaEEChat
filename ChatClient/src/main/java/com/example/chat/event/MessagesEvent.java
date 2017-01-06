package com.example.chat.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.example.chat.protocol.Message;

public class MessagesEvent extends ApplicationEvent {

	private final List<Message> messages;
	
	public MessagesEvent(Object source, List<Message> messages) {
		super(source);
		// TODO Auto-generated constructor stub
		this.messages=messages;
	}

}
