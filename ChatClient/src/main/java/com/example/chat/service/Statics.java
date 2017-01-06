package com.example.chat.service;

import com.example.chat.model.User;
import com.example.chat.protocol.Protocol;

public class Statics {

	private static Protocol selectedProtocol=Protocol.HESSIAN;
	private static User loggedUser;
	
	public static Protocol getSelectedProtocol(){
		return selectedProtocol;
	}
	
	public static void setSelectedProtocol(Protocol selectedProtocol){
		Statics.selectedProtocol=selectedProtocol;
	}
	
	public static User getLoggedUser(){
		return loggedUser;
	}
	
	public static void setLoggedUser(User loggedUser){
		Statics.loggedUser=loggedUser;
	}
}
