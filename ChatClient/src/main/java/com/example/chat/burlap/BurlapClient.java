package com.example.chat.burlap;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.stereotype.Component;

import com.caucho.burlap.client.BurlapProxyFactory;
import com.example.chat.protocol.IChatService;

@Component
public class BurlapClient {

	private static final String BURLAP_SERVICE = "/burlapService";

	@Autowired
	@Value("${server.url}")
	private String url;

	public IChatService getService() {
		String serviceUrl = url + BURLAP_SERVICE;

		BurlapProxyFactory bPF = new BurlapProxyFactory() {
			@Override
			protected URLConnection openConnection(URL url) throws IOException {
				URLConnection urlCon = super.openConnection(url);
				urlCon.setRequestProperty("User-Agent", "RC-Burlap");
				return urlCon;
			}
		};

		BurlapProxyFactoryBean bPFB = new BurlapProxyFactoryBean();
		bPFB.setServiceUrl(serviceUrl);
		bPFB.setServiceInterface(IChatService.class);
		bPFB.setProxyFactory(bPF);
		bPFB.afterPropertiesSet();
		IChatService chatService = (IChatService) bPFB.getObject();

		return chatService;
	}
}
