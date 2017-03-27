package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

@SuppressWarnings("serial")
public class View extends JFrame {
	
	private PasswordPanel passwordPanel;
	private Controller controller;
	private JPanel contentPane;
	
	public View(Controller c){
		this.controller = c;
		
		setResizable(false);
		setSize(new Dimension(1000, 500));
		getContentPane().setLayout(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordPanel = new PasswordPanel(controller);
		contentPane.add(passwordPanel);
		
		
	}

}