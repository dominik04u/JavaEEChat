package com.example.chat.hessian;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianURLConnectionFactory;
import com.example.chat.protocol.IChatService;

@Component
public class HessianClient {

	private static final String HESSIAN_SERVICE = "/hessianService";

	@Autowired
	@Value("${server.url}")
	private String url;

	public IChatService getService() {
		String serviceUrl = url + HESSIAN_SERVICE;

		HessianProxyFactory hPF = new HessianProxyFactory();
		HessianConnectionFactory hCF = new HessianURLConnectionFactory() {

			@Override
			public HessianConnection open(URL url) throws IOException {
				HessianConnection hC = super.open(url);
				hC.addHeader("User-Agent", "RC-Hessian");
				return hC;
			}
		};

		hCF.setHessianProxyFactory(hPF);
		hPF.setConnectionFactory(hCF);

		HessianProxyFactoryBean hPFB = new HessianProxyFactoryBean();
		hPFB.setProxyFactory(hPF);
		hPFB.setConnectionFactory(hCF);
		hPFB.setServiceUrl(serviceUrl);
		hPFB.setServiceInterface(IChatService.class);

		hPFB.afterPropertiesSet();
		IChatService chatService = (IChatService) hPFB.getObject();
		return chatService;
	}
}
