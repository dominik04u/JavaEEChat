package com.example.chat.service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.chat.burlap.BurlapClient;
import com.example.chat.hessian.HessianClient;
import com.example.chat.model.User;
import com.example.chat.protocol.IChatService;
import com.example.chat.protocol.Message;
import com.example.chat.protocol.Protocol;
import static com.example.chat.service.Statics.*;

@Component
public class CommunicationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommunicationService.class);
	
	private static final Map<Protocol, IChatService> CHAT_SERVICES=new HashMap<>();
	private final BurlapClient burlapClient;
	private final HessianClient hessianClient;
	
	@Autowired
	public CommunicationService(BurlapClient burlapClient, HessianClient hessianClient) throws MalformedURLException {
		this.burlapClient=burlapClient;
		this.hessianClient=hessianClient;
		burlap();
		hessian();
	}
	
	private void burlap(){
		IChatService burlapService = burlapClient.getService();
		CHAT_SERVICES.put(Protocol.BURLAP, burlapService);
	}
	
	private void hessian(){
		IChatService hessianService = hessianClient.getService();
		CHAT_SERVICES.put(Protocol.HESSIAN, hessianService);
	}
	
	public void login(String username){
		long userId=CHAT_SERVICES.get(getSelectedProtocol()).login(username);
		Statics.setLoggedUser(new User(userId, username));
		LOGGER.info("User <{}> logged in, his id <{}>", username, userId);
	}
	
	public void logout(){
		if(getLoggedUser()!=null){
			CHAT_SERVICES.get(getSelectedProtocol()).logout(getLoggedUser().getId());
			LOGGER.info("User <{}> logged out", getLoggedUser().getUsername());
		}
	}
	
	public void sendMessage(String messageText){
		Message message=new Message(messageText, getLoggedUser().getUsername());
		CHAT_SERVICES.get(getSelectedProtocol()).sendMessage(getLoggedUser().getId(),message);
	}
	
	public List<Message> readMessage(){
		List<Message> messages=CHAT_SERVICES.get(getSelectedProtocol()).getMyMessages(getLoggedUser().getId());
		return messages;
	}
	
	public void changeProtocol(){
		CHAT_SERVICES.get(getSelectedProtocol()).changeTechnology(getLoggedUser().getId(), getSelectedProtocol());
		LOGGER.info("Joining protocol <{}>");
	}
}
