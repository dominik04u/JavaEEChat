package com.example.chat.protocol;

public interface IChatService {

	Long login(String username);
	
	boolean logout(long userId);
	
	boolean sendMessage(long author, Message message);
	
}
