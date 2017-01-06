package com.example.chat.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.chat.event.LoginEvent;
import com.example.chat.service.CommunicationService;

@Component
public class LoginEventListener implements ApplicationListener<LoginEvent> {
	
	@Autowired
	private CommunicationService cS;

	@Override
	public void onApplicationEvent(LoginEvent event) {
		// TODO Auto-generated method stub
		cS.login(event.getUsername());
	}
	
}
	