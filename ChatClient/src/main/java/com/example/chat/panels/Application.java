package com.example.chat.panels;

import java.awt.BorderLayout;
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

public class Application extends JFrame {

	
	public static void main(String[] args) {
		JButton btn = new JButton("Send");
		btn.setPreferredSize(new Dimension(80, 20));
		JTextArea  messages = new JTextArea();
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		messages.setPreferredSize(new Dimension(250, 300));
		JScrollPane scroll = new JScrollPane (messages, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(250, 300));
		JTextField message = new JTextField(25);
	    ButtonGroup btnGroup = new ButtonGroup();
		JRadioButton burlap = new JRadioButton("Burlap");
	    JRadioButton hessian = new JRadioButton("Hessian");
	    btnGroup.add(burlap);
	    btnGroup.add(hessian);

		
		JPanel pane = new JPanel();
		JPanel pane1 = new JPanel();
		JPanel pane2 = new JPanel();
		JPanel pane3 = new JPanel();
		
		BorderLayout grid = new BorderLayout();
		GridLayout flow = new GridLayout(0,2);
		FlowLayout floww = new FlowLayout();
		FlowLayout flowww = new FlowLayout();

		
		pane.setLayout(grid);
		pane1.setLayout(flow);
		pane2.setLayout(floww);
		pane3.setLayout(flowww);

		
		pane1.add(burlap);
		pane1.add(hessian);
		
		pane2.add(scroll);
		
		pane3.add(message);
		pane3.add(btn);

		
		pane.add(pane1, BorderLayout.NORTH);
		pane.add(pane2, BorderLayout.CENTER);
		pane.add(pane3, BorderLayout.SOUTH);

	/*	pane.add(new Label(" "));
		pane.add(new Label(" "));
		pane.add(field1);
		pane.add(new Label(" "));
		pane.add(btn);*/
		JFrame fr = new JFrame();
		fr.setSize(400, 400);
	    fr.setContentPane(pane);  // Use our pane as the default pane
	    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit program when frame is closed
	    fr.setLocation(200, 200); // located at (200, 200)
	    fr.setVisible(true);      // M

	}
	
	

	

}