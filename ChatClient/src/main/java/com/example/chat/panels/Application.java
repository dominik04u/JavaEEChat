package com.example.chat.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.chat.event.LoginEvent;
import com.example.chat.event.MessagesEvent;
import com.example.chat.protocol.Message;
import com.example.chat.protocol.Protocol;
import com.example.chat.service.CommunicationService;
import com.example.chat.service.Statics;
import javax.swing.JTextPane;

@Component
public class Application extends JFrame implements ApplicationListener<MessagesEvent> {

	private final ApplicationEventPublisher publisher;
	private final CommunicationService communicationService;
	private final StringBuilder messagesBuilder;

	@Autowired
	public Application(ApplicationEventPublisher publisher, CommunicationService communicationService) {
		this.publisher = publisher;
		this.messagesBuilder = new StringBuilder();
		this.communicationService = communicationService;
		initApp();
	}

	JTextField username;
	TextArea message;
	JTextPane chatMessages;

	private void initApp() {

		// radiobuttony do zmiany technologi
		ButtonGroup btnGroup = new ButtonGroup();

		JPanel pane = new JPanel();
		JPanel pane1 = new JPanel();
		pane1.setBounds(0, 0, 384, 46);
		JPanel pane2 = new JPanel();
		pane2.setBounds(0, 46, 384, 195);
		JPanel pane3 = new JPanel();
		pane3.setBounds(0, 241, 384, 120);
		GridLayout flow = new GridLayout(2, 2);
		pane1.setLayout(flow);
		// pole na nazwe uzytkownika
		username = new JTextField(10);
		pane1.add(username);
		// zaloguj
		JButton login = new JButton("Login");
		pane1.add(login);
		login.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent event) {
				login.setEnabled(false);
				loginButtonActionPerformed(event);
			}
		});

		pane2.setLayout(null);
		JRadioButton burlap = new JRadioButton("Burlap");
		burlap.setBounds(59, 5, 80, 23);
		burlap.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				burlapRadioActionPerformed(evt);
			}
		});
		pane2.add(burlap);
		btnGroup.add(burlap);
		JRadioButton hessian = new JRadioButton("Hessian");
		hessian.setSelected(true);
		hessian.setBounds(260, 5, 80, 23);
		hessian.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				hessianRadioActionPerformed(evt);
			}
		});
		pane2.add(hessian);
		btnGroup.add(hessian);
		pane.setLayout(null);

		pane.add(pane1);
		pane.add(pane2);

		chatMessages = new JTextPane();
		chatMessages.setContentType("text/html");
		chatMessages.setEditable(false);
		chatMessages.setBounds(0, 33, 384, 160);
		pane2.add(chatMessages);
		pane.add(pane3);
		pane3.setLayout(null);

		message = new TextArea();
		message.setBounds(0, 0, 384, 100);
		pane3.add(message);

		// wyslij wiadomosc
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(0, 100, 384, 20);
		sendButton.setPreferredSize(new Dimension(80, 20));
		sendButton.setPreferredSize(new Dimension(80, 20));
		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent event) {
				sendButtonActionPerformed(event);
			}
		});
		pane3.add(sendButton);

		JFrame fr = new JFrame();
		fr.setSize(400, 400);
		fr.setContentPane(pane); // Use our pane as the default pane
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit program when
															// frame is closed
		fr.setLocation(200, 200); // located at (200, 200)
		fr.setVisible(true); // M

	}

	private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
		LoginEvent loginEvent = new LoginEvent(this, username.getText());
		publisher.publishEvent(loginEvent);
	}

	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
		sendMessage();
	}

	private void sendMessage() {
		String mess = message.getText();
		message.setText("");
		communicationService.sendMessage(mess);
	}

	private void burlapRadioActionPerformed(java.awt.event.ActionEvent evt) {
		chatMessages.setText("");
		Statics.setSelectedProtocol(Protocol.BURLAP);
		communicationService.changeProtocol();
	}

	// zmiana na hessian
	private void hessianRadioActionPerformed(java.awt.event.ActionEvent evt) {
		chatMessages.setText("");
		Statics.setSelectedProtocol(Protocol.HESSIAN);
		communicationService.changeProtocol();
	}

	public void setText(List<Message> messages) {
		messages.stream().forEach((message) -> {
			updateMessages(message);
			chatMessages.setText("<html><body>" + messagesBuilder.toString() + "</html></body>");
		});
	}

	private void updateMessages(Message receivedMessage) {
		boolean myMessage = receivedMessage.getAuthor().equals(Statics.getLoggedUser().getUsername());
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
		chatMessages.setText("Myslales ze dziala?");
		evnt.getMessages();
		evnt.getMessages().stream().forEach((message) -> {
			System.out.println(message.getMessage());
			updateMessages(message);
			chatMessages.setText("<html><body>" + messagesBuilder.toString() + "</html></body>");
		});
	}

	@Override
	public void onApplicationEvent(MessagesEvent evnt) {
		onMessageEvent(evnt);

	}

}