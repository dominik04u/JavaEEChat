package com.example.chat.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;

import com.example.chat.event.LoginEvent;
import com.example.chat.service.CommunicationService;

public class Application extends JFrame implements ApplicationListener<ApplicationEvent> {

	private final ApplicationEventPublisher publisher;
	private final CommunicationService communicationService;
	private final StringBuilder messagesBuilder;

	public Application(ApplicationEventPublisher publisher, CommunicationService communicationService) {
		this.publisher = publisher;
		this.messagesBuilder = new StringBuilder();
		this.communicationService = communicationService;
		initApp();
	}
	
	JTextField username;

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
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				login.setEnabled(false);
				loginButtonActionPerformed(evt);
			}
		});

		pane2.setLayout(null);
		JRadioButton burlap = new JRadioButton("Burlap");

		burlap.setSelected(true);
		burlap.setBounds(59, 5, 80, 23);
		pane2.add(burlap);
		btnGroup.add(burlap);
		JRadioButton hessian = new JRadioButton("Hessian");
		hessian.setBounds(260, 5, 80, 23);
		pane2.add(hessian);
		btnGroup.add(hessian);
		pane.setLayout(null);

		pane.add(pane1);
		pane.add(pane2);

		TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(0, 33, 384, 160);
		pane2.add(textArea);
		pane.add(pane3);
		pane3.setLayout(null);

		TextArea textArea_1 = new TextArea();
		textArea_1.setBounds(0, 0, 384, 100);
		pane3.add(textArea_1);

		// wyslij wiadomosc
		JButton btn = new JButton("Send");
		btn.setBounds(0, 100, 384, 20);
		btn.setPreferredSize(new Dimension(80, 20));
		btn.setPreferredSize(new Dimension(80, 20));
		pane3.add(btn);

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


	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		// TODO Auto-generated method stub

	}

}