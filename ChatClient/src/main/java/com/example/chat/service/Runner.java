package com.example.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.chat.panels.Application;


@Component
public class Runner implements CommandLineRunner {

	@Autowired
	private Application frame;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		java.awt.EventQueue.invokeLater(()->{
			frame.setVisible(true);
		});
	}
}
