package com.example.chat.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.chat.model.User;
import com.example.chat.protocol.Message;

@Service
public class UserService implements IUserService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UserService.class);
	private final Map<Long,User> users=new HashMap<>();
	
	@Override
	public long login(String username) {
		User user = new User(username);
		users.put(user.getId(), user);
		LOGGER.debug("Zalogowano <{}>, <{}>", user.getId(), user.getUsername());
		return user.getId();
	}

	@Override
	public void logout(long userId) {
		users.remove(userId);
		User.removeID(userId);
		LOGGER.debug("Wylogowano <{}>", userId);
	}

	@Override
	public void sendMessage(long author, Message message) {
		LOGGER.debug("Nowa wiadomość od <{}>", author);
	}

}
