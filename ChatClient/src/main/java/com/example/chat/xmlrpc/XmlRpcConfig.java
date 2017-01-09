package com.example.chat.xmlrpc;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.client.util.ClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlRpcConfig {
    private static final String SERVICE_URL = "/xml-rpc";
    
    @Autowired
    @Value("${server.url}")
    private String url;


    @Bean
    public XmlRpcClientConfigImpl xmlRpcClientConfig() throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(url+SERVICE_URL));
        config.setEnabledForExtensions(true);
        config.setEnabledForExceptions(true);

        return config;
    }

    @Bean
    public XmlRpcClient xmlRpcClient(XmlRpcClientConfig config) {
        XmlRpcClient xmlRpcClient = new XmlRpcClient();
        xmlRpcClient.setTransportFactory(new XmlRpcCommonsTransportFactory(xmlRpcClient));
        xmlRpcClient.setConfig(config);

        return xmlRpcClient;
    }

    @Bean
    public ClientFactory xmlRpcClientFactory(XmlRpcClient client) {
        return new ClientFactory(client);
    }
}
