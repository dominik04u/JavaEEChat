package com.example.chat.xmlrpc;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chat.protocol.IChatService;

@Service
public class XmlRpcRequestProcessorFactoryFactory implements RequestProcessorFactoryFactory {
    
    @Autowired
    private IChatService chatService;

    @SuppressWarnings("rawtypes")
    public RequestProcessorFactory getRequestProcessorFactory(Class clazz) throws XmlRpcException {
        if (clazz.equals(IChatService.class)) {
            return new XmlRpcRequestProcessorFactory(chatService);
        } else {
            throw new IllegalArgumentException("no registered handler for class <" + clazz.getName() + ">");
        }
    }

}
