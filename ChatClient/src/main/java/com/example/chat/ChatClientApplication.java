package com.example.chat;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.chat.panels.Application;

@Configuration
@SpringBootApplication
@EnableScheduling
public class ChatClientApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ChatClientApplication.class)
        .headless(false)
        .web(false)
        .run(args);
	}
}
