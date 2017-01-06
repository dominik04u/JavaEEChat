package com.example.chat.service;

import java.util.List;

import com.example.chat.protocol.Message;
import com.example.chat.protocol.Protocol;

public interface IUserService {

	long login(String username);
	
	void logout(long userId);
	
	void sendMessage(long author, Message message);

	void changeTechnology(long author, Protocol protocol);

	List<Message> readMessagesForUser(long userId);
}
