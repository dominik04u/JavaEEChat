package com.example.chat.hessian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.example.chat.protocol.IChatService;

@Configuration
public class HessianConfig {
	
	@Autowired
	private IChatService hessianService;
	
	@Bean(name="/hessianService")
	public HessianServiceExporter hessianService(){
		HessianServiceExporter exporter=new HessianServiceExporter();
		exporter.setService(hessianService);
		exporter.setServiceInterface(IChatService.class);
		
		return exporter;
	}
	
}