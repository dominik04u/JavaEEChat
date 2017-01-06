package com.example.chat.protocol;

import java.util.List;

public interface IChatService {

	Long login(String username);
	
	boolean logout(long userId);
	
	boolean sendMessage(long author, Message message);
	
	boolean changeTechnology(long author, Protocol protocol);

	List<Message> getMyMessages(long id);
	
}
