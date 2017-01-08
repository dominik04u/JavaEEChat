
package com.example.chat.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.chat.event.LoginEvent;
import com.example.chat.event.MessagesEvent;
import com.example.chat.protocol.Message;
import com.example.chat.protocol.Protocol;
import com.example.chat.service.CommunicationService;
import com.example.chat.service.Statics;

@Component
public class Application extends JFrame implements ApplicationListener<MessagesEvent>{

	private final ApplicationEventPublisher publisher;
	private final StringBuilder burlapBuilder, hessianBuilder;
    private final CommunicationService communicationService;
	
    @Autowired
	public Application(ApplicationEventPublisher publisher, CommunicationService communicationService){
		this.publisher = publisher;
		this.burlapBuilder = new StringBuilder();
		this.hessianBuilder = new StringBuilder();
		this.communicationService = communicationService;
		initApp();
	}
	
	JButton sendButton, loginButton;
	JTextPane  chatMessages;
	JScrollPane scrollPane;
	JTextArea message;
	JTextField username;
	ButtonGroup btnGroup;
	JRadioButton burlap, hessian;
	private void initApp() {
		
		//wyslij wiadomosc
		sendButton = new JButton("Send");
		sendButton.setBounds(0, 105, 384, 20);
		sendButton.setPreferredSize(new Dimension(80, 20));
		sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
            	sendButtonActionPerformed(event);
            }
		});
		//pole z wiadomosciami
		chatMessages = new JTextPane();
		chatMessages.setFocusable(false);
		chatMessages.setContentType("text/html");
		chatMessages.setPreferredSize(new Dimension(250, 300));
		scrollPane = new JScrollPane (chatMessages, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(380, 180));
		
		//pole dla nowej wiadomosci
		message = new JTextArea();
		message.setBounds(0, 0, 384, 100);
		//pole na nazwe uzytkownika
		username = new JTextField(10);
		
		//radiobuttony do zmiany technologi
	    btnGroup = new ButtonGroup();

		
		JPanel pane = new JPanel();
		JPanel pane1 = new JPanel();
		pane1.setBounds(0, 0, 384, 46);
		JPanel pane2 = new JPanel();
		pane2.setBounds(0, 46, 384, 180);
		JPanel pane3 = new JPanel();
		pane3.setBounds(0, 230, 384, 125);
		GridLayout flow = new GridLayout(2,2);
		FlowLayout floww = new FlowLayout();
		pane1.setLayout(flow);
		pane2.setLayout(floww);
		pane1.add(username);
		//zaloguj
		loginButton = new JButton("Login");
		loginButton.setPreferredSize(new Dimension(80, 20));
		loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
            	loginButtonActionPerformed(event);
            }
		});
		pane1.add(loginButton);
		hessian = new JRadioButton("Hessian");
		hessian.setSelected(true);
		hessian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	hessianRadioActionPerformed(evt);
            }
		});
		btnGroup.add(hessian);
		pane1.add(hessian);
		
		pane2.add(scrollPane);
		pane3.setLayout(null);
		
		pane3.add(message);
		pane3.add(sendButton);
		pane.setLayout(null);

		
		pane.add(pane1);
		burlap = new JRadioButton("Burlap");
		burlap.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	burlapRadioActionPerformed(evt);
	            }
		 });
		btnGroup.add(burlap);
		
				
				pane1.add(burlap);
		pane.add(pane2);
		pane.add(pane3);

		//JFrame fr = new JFrame();
		this.setSize(400, 400);
		this.setContentPane(pane);  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLocation(200, 200); 
		this.setVisible(true);      
		
	}
	
	
	//akcja do przycisku logowania
	  private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
	        loginButton.setEnabled(false);
		    LoginEvent loginEvent = new LoginEvent(this, username.getText());
	        publisher.publishEvent(loginEvent);
	  }//GEN-LAST:event_loginButtonActionPerformed
	
	  //zmiana na burlap
	  private void burlapRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_burlapRadioActionPerformed
		  Statics.setSelectedProtocol(Protocol.BURLAP);
		  communicationService.changeProtocol();
          chatMessages.setText("<html><body>" + burlapBuilder.toString() + "</html></body>");
	  }

	  //zmiana na hessian
	  private void hessianRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hessianRadioActionPerformed
		  chatMessages.setText("");    
		  Statics.setSelectedProtocol(Protocol.HESSIAN);
		  communicationService.changeProtocol();
          chatMessages.setText("<html><body>" + hessianBuilder.toString() + "</html></body>");
	  }
	  
	  //akcja do wysylania widomosci
	  private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
	        sendMessage();

	  }//GEN-LAST:event_sendButtonActionPerformed
	  
	  private void sendMessage() {
	        String mess = message.getText();
	        communicationService.sendMessage(mess);
	        message.setText("");
	  }
	  
	  
	/*  public void setText(List<Message> messages) {
	        messages.stream().forEach((message) -> {
	            updateMessages(message);      
	            chatMessages.setText("<html><body>" + messagesBuilder.toString() + "</html></body>");
	        });
	    }*/

	    private void updateMessages(Message receivedMessage, StringBuilder messagesBuilder) {
	        boolean myMessage = receivedMessage.getAuthor().equals(Statics.getLoggedUser().getUsername());
	        System.out.println(receivedMessage.getMessage());
	        if (myMessage) {
	            messagesBuilder.append("<strong>");
	        } else {
	            messagesBuilder.append("<span>");
	        }
	        messagesBuilder.append(receivedMessage.getAuthor());
	        messagesBuilder.append(":");
	        if (myMessage) {
	            messagesBuilder.append("</strong>");
	        } else {
	            messagesBuilder.append("</span>");
	        }
	        messagesBuilder.append("<pre>");
	        messagesBuilder.append(receivedMessage.getMessage());
	        messagesBuilder.append("</pre>");
	        messagesBuilder.append("<hr/>");
	}
	  
	private void onMessageEvent(MessagesEvent evnt) {
		
        //System.out.println("taki rozmiar: "+evnt.getMessages().size());
		evnt.getMessages().stream().forEach((message) -> {
			System.out.println("jestemtu w srodku");
			if(Statics.getSelectedProtocol()==Protocol.BURLAP){
	            updateMessages(message,burlapBuilder);
	            chatMessages.setText("<html><body>" + burlapBuilder.toString() + "</html></body>");
			}
			else{
				updateMessages(message,hessianBuilder);
	            chatMessages.setText("<html><body>" + hessianBuilder.toString() + "</html></body>");
			}
	        });
	}

	@Override
	public void onApplicationEvent(MessagesEvent evnt) {
			onMessageEvent(evnt);
	
		
	}
	

	

}


