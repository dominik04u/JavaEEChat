package com.example.chat.burlap;

import com.caucho.burlap.client.BurlapProxyFactory;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.stereotype.Component;
import com.example.chat.protocol.IChatService;

@Component
public class BurlapClient {

	private static final String BURLAP_SERVICE = "/burlapService";

	@Autowired
	@Value("${server.url}")
	private String url;

	public IChatService getService(){
		String serviceUrl=url+BURLAP_SERVICE;
		
		BurlapProxyFactory burlapPF=new BurlapProxyFactory(){
			@Override
			protected URLConnection openConnection(URL url) throws IOException{
				URLConnection urlCon= super.openConnection(url);
				urlCon.setRequestProperty("UserAgent", "RC-Burlap");
				return urlCon;
		}
	};
	
	BurlapProxyFactoryBean burlapPFB = new BurlapProxyFactoryBean();
	burlapPFB.setServiceUrl(serviceUrl);
	burlapPFB.setServiceInterface(IChatService.class);
	burlapPFB.setProxyFactory(burlapPF);
	burlapPFB.afterPropertiesSet();
	IChatService chatService= (IChatService) burlapPFB.getObject();
	
	return chatService;
}
}
