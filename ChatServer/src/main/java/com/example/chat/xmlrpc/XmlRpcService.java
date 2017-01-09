package com.example.chat.xmlrpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.metadata.XmlRpcSystemImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.apache.xmlrpc.server.XmlRpcErrorLogger;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.example.chat.protocol.IChatService;

public class XmlRpcService implements HttpRequestHandler {

    private final XmlRpcServletServer server;

    public XmlRpcService(RequestProcessorFactoryFactory requestProcessorFactoryFactory) throws XmlRpcException {
        XmlRpcServerConfigImpl config = new XmlRpcServerConfigImpl();
        config.setEncoding(XmlRpcServerConfigImpl.UTF8_ENCODING);
        config.setEnabledForExceptions(true);
        config.setEnabledForExtensions(true);
        config.setKeepAliveEnabled(true);

        PropertyHandlerMapping handlerMapping = new PropertyHandlerMapping();
        handlerMapping.setRequestProcessorFactoryFactory(requestProcessorFactoryFactory);
        handlerMapping.addHandler(IChatService.class.getName(), IChatService.class);
        XmlRpcSystemImpl.addSystemHandler(handlerMapping);

        server = new XmlRpcServletServer();
        server.setConfig(config);
        server.setErrorLogger(new XmlRpcErrorLogger());
        server.setHandlerMapping(handlerMapping);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("POST".equals(request.getMethod())) {
            server.execute(request, response);
        } else {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(),
                    "XmlRpcService only allows POST requests");
        }
    }

}
