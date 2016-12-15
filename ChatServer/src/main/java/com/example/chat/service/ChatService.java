package com.example.chat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.chat.protocol.IChatService;
import com.example.chat.protocol.Message;

public class ChatService implements IChatService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UserService.class);
	
	private final IUserService userService;
	
	public ChatService(IUserService userService){
		this.userService=userService;
	}

	@Override
	public Long login(String username) {
		LOGGER.info("użytkownik <{}> zalogował się", username);
		return userService.login(username);
	}

	@Override
	public boolean logout(long userId) {
		LOGGER.info("użytkownik <{}> wylogował się", userId);
		userService.logout(userId);
		return true;
	}

	@Override
	public boolean sendMessage(long author, Message message) {
		LOGGER.info("Użytkownik <{}> wysłał wiadomość <{}>", message.getAuthor(), message.getMessage());
		
		return true;
	}

}
