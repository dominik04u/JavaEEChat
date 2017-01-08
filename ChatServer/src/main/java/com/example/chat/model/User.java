package com.example.chat.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.example.chat.protocol.Message;

public class User {

	private static final Random RANDOM = new Random();
	private static final Set<Long> EXISTINGS_USERS = new HashSet<>();

	public static void removeID(long userId) {
		EXISTINGS_USERS.remove(userId);
	}

	private final long id;
	private final String username;
	private final List<Message> messages = new ArrayList<>();

	public User(String username) {
		long id;
		do {
			id = RANDOM.nextLong();
		} while (EXISTINGS_USERS.contains(id));
		EXISTINGS_USERS.add(id);
		this.id = id;
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void newMessage(Message message) {
		messages.add(message);
	}

	public List<Message> readMessages() {
		List<Message> mess = new ArrayList<>();
		for (Message m : messages) {
			mess.add(m.clone());
		}
		messages.clear();
		return mess;
	}

}
