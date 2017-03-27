package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import controller.Controller;

@SuppressWarnings("serial")
public class PasswordPanel extends javax.swing.JPanel {
	
	private JPasswordField textField;
	private JButton[][] imageButtons;
	private JButton[][] emojiButtons;
	private JButton submitButton;
	private Controller controller;
	
	public PasswordPanel(Controller c){
		controller = c;
		
		setBounds(5, 100, 985, 365);
		setBorder(new LineBorder(Color.GRAY));//TODO:remove this later
		setLayout(null);
		
		JLabel label = new JLabel("Enter text");
		label.setBounds(275, 110, 100, 30);
		add(label);
		
		textField = new JPasswordField("");
		textField.setBounds(275, 150, 100, 30);
		textField.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e){
				String t = new String(textField.getPassword());
				controller.handleTextEnter(t);
				// TODO: log end time
			}
			public void focusGained(FocusEvent e){
				//do nothing, we don't care about this.
				// TODO: log start time
			}
		});
		add(textField);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.handleSubmit();
				
			}
		});
		
		submitButton.setBounds(850, 320, 75, 30);
		add(submitButton);
	}

	
}
