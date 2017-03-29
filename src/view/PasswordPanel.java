package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import controller.Controller;

@SuppressWarnings("serial")
public class PasswordPanel extends javax.swing.JPanel {
	
	private JPasswordField textField;
	private JButton[] imageButtons;
	private JButton[] emojiButtons;
	private JButton submitButton;
	private Controller controller;
	
	public PasswordPanel(Controller c){
		controller = c;
		
		setBounds(5, 100, 985, 365);
		setBorder(new LineBorder(Color.GRAY));//TODO:remove this later
		setLayout(null);
		
		JLabel label = new JLabel("Enter text");
		label.setBounds(440, 110, 100, 30);
		add(label);
		
		textField = new JPasswordField("");
		textField.setBounds(440, 150, 100, 30);
		textField.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e){
				controller.handleTextEnter();
			}
			public void focusGained(FocusEvent e){
				//do nothing, we don't care about this.
			}
		});
		add(textField);
		
		emojiButtons = new JButton[16];
		
		int buttonSize = 72;
		int gridSize = 4;
		int xStart = 5;
		int yStart = 5;
		
		for (int i = 0; i < 16; i++){
			/*BufferedImage buttonIcon = ImageIO.read(new File(""));
			button = new JButton(new ImageIcon(buttonIcon));*/
			emojiButtons[i] = new JButton("Testing");
			emojiButtons[i].setBounds(xStart + (i%gridSize) * buttonSize, yStart + (i/gridSize) * buttonSize, buttonSize, buttonSize);
			//emojiButtons[i].setText("testing");
			add(emojiButtons[i]);
		}
		
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
