package com.example.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.chat.event.MessagesEvent;
import com.example.chat.protocol.Message;

@Component
public class MessageLoaderService {

	private final ApplicationEventPublisher publisher;
	private final CommunicationService communicationService;

	@Autowired
	public MessageLoaderService(CommunicationService communicationService, ApplicationEventPublisher publisher) {
		this.communicationService = communicationService;
		this.publisher = publisher;
	}

	@Scheduled(fixedRate = 1000)
	private void readMessages() {
		if (Statics.getLoggedUser() != null) {
			List<Message> messages = communicationService.readMessage();
			MessagesEvent messages2 = new MessagesEvent(this, messages);
			publisher.publishEvent(messages2);
		}
	}
}
