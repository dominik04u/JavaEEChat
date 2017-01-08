package com.example.chat.protocol;
import java.io.Serializable;

public class Message implements Serializable {
	
	private final String mess;
	private final String author;
	
	public Message(String mess, String author){
		this.mess=mess;
		this.author=author;
	}
	
	public String getMessage(){
		return mess;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public Message clone(){
		return(new Message(this.mess, this.author));
	}
}
