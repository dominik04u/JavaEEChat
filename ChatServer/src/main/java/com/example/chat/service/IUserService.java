package com.example.chat.service;

import com.example.chat.protocol.Message;

public interface IUserService {

	long login(String username);
	
	void logout(long userId);
	
	void sendMessage(long author, Message message);
}
