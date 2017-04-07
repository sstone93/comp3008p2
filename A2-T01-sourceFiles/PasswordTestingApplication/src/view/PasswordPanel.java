package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Controller;
import model.*;

/*
This file contains the passwordPanel view component
This is the class that provide most of the visual 
display for our password scheme
*/
@SuppressWarnings("serial")
public class PasswordPanel extends javax.swing.JPanel {
	
	private JPasswordField entryTextField;
	private JTextField displayTextField;
	private JButton[] imageButtons;
	private JButton[] emojiButtons;
	private JButton submitButton;
	private JButton nextButton;
	private JCheckBox showCheckBox;
	private Controller controller;
	private JLabel clicksLabel;
	
    /*
    The constructor that initializes all of the components
    */
	public PasswordPanel(Controller c){
		controller = c;
		
		setBounds(5, 100, 985, 365);
		setBorder(new LineBorder(Color.GRAY));
		setLayout(null);
		
		JLabel label = new JLabel("Enter text");

		label.setFont (label.getFont ().deriveFont (24.0f));
		label.setBounds(400, 110, 180, 30);
		add(label);
		
		entryTextField = new JPasswordField("");
		entryTextField.setBounds(400, 150, 180, 30);
		entryTextField.setFont(entryTextField.getFont().deriveFont(24.0f));
        //a listener is added to the text field to detect the text change event
		entryTextField.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
				callController();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				callController();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				callController();
			}
			
			public void callController(){
				String t = new String(entryTextField.getPassword());
				controller.handleTextEnter(t);
			}
		});
		add(entryTextField);
		
		displayTextField = new JTextField("");
		displayTextField.setBounds(400, 190, 180, 30);
		displayTextField.setFont(displayTextField.getFont().deriveFont(24.0f));
		displayTextField.setEditable(false);
		add(displayTextField);
		
		int buttonSize = 72;
		int gridSize = 4;
		int xStart = 15;
		int yStart = 10;
		
        //The image component of the password is displayed using a grid of buttons
		imageButtons = new JButton[16];
		
		for (int i = 0; i < 16; i++){
			
			try{
				BufferedImage buttonIcon = ImageIO.read(this.getClass().getResource("/resources/l" + (i+1) + ".jpg"));
				imageButtons[i] = new JButton(new ImageIcon(buttonIcon));
			}catch(IOException e){
				System.out.println("IT DIDN'T WORK");
			}
			
			final String id = "l" + (i + 1);
			
			imageButtons[i].setName(id);
			imageButtons[i].setBounds(xStart + (i%gridSize) * buttonSize, yStart + (i/gridSize) * buttonSize, buttonSize, buttonSize);
			
			imageButtons[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					controller.handleLandscapeClicked(id);
				}
			});
			
			add(imageButtons[i]);
		}
		
		xStart = 670;
		emojiButtons = new JButton[16];
		
        //The emoji component of the password is displayed using a grid of buttons
		for (int i = 0; i < 16; i++){
			
			try{
				BufferedImage buttonIcon = ImageIO.read(this.getClass().getResource("/resources/e" + (i+1) + ".png"));
				emojiButtons[i] = new JButton(new ImageIcon(buttonIcon));
			}catch(IOException e){
				System.out.println("IT DIDN'T WORK");
			}
			final String id = "e" + (i + 1);
			
			emojiButtons[i].setName(id);
			emojiButtons[i].setBounds(xStart + (i%gridSize) * buttonSize, yStart + (i/gridSize) * buttonSize, buttonSize, buttonSize);
			
			emojiButtons[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					controller.handleEmojiClicked(id);
				}
			});
			
			add(emojiButtons[i]);
		}
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.handleSubmit();
				
			}
		});
		
		submitButton.setBounds(880, 330, 75, 30);
		add(submitButton);
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.handleNext();
			}
		});
		nextButton.setBounds(780, 330, 75, 30);
		add(nextButton);
		
		showCheckBox = new JCheckBox("Show password");
		showCheckBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				update();
			}
		});
		showCheckBox.setBounds(600, 330, 150, 30);
		showCheckBox.setSelected(true);
		add(showCheckBox);
		
		clicksLabel = new JLabel("");

		clicksLabel.setFont (clicksLabel.getFont ().deriveFont (24.0f));
		clicksLabel.setBounds(400, 330, 250, 30);
		add(clicksLabel);
	}
	
    /*
    Updates the panel based on the state of the model
    */
	public void update(){
		MainModel model = controller.getMainModel();
		
		if (model.getCurrentMode() == MainModel.MODE.TRAINING){
            //The user can choose to have the password displayed while they are training
			if(showCheckBox.isSelected()){
				Password password = model.getAssignedPasswords().get(model.getCurrentType());
				displayTextField.setText(password.getRandomWord());
				displayTextField.setVisible(true);
				for(int i = 0; i < 16; i++){
					if (password.getEmojis().contains("e" + (i + 1))){
						emojiButtons[i].setBorder(new LineBorder(Color.RED));
						emojiButtons[i].setBorderPainted(true);
					}
					else {
						emojiButtons[i].setBorder(null);
					}
					if (password.getLandscape().contains("l" + (i + 1))){

						imageButtons[i].setBorder(new LineBorder(Color.RED));
						imageButtons[i].setBorderPainted(true);
					}
					else{
						imageButtons[i].setBorder(null);
					}
				}
			}
			else{
                //ensuring that the password is never shown in testing mode
				displayTextField.setText("");
				displayTextField.setVisible(false);
				for(int i = 0; i < 16; i++){
					emojiButtons[i].setBorderPainted(false);
					imageButtons[i].setBorderPainted(false);
				}
			}
			
			if(model.getCanMoveOn()){
				nextButton.setEnabled(true);
			}
			else{
				nextButton.setEnabled(false);
			}
			
		}
		else if (model.getCurrentMode() == MainModel.MODE.TESTING){
			displayTextField.setText("");
			displayTextField.setVisible(false);
			
			for(int i = 0; i < 16; i++){
				emojiButtons[i].setBorderPainted(false);
				imageButtons[i].setBorderPainted(false);
			}
			
			nextButton.setVisible(false);
			showCheckBox.setVisible(false);
		}
		
		//Only the area the user is currently entering should be visible
		if(model.getPasswordState() == MainModel.PW_STATE.LANDSCAPE){
			entryTextField.setEnabled(false);
			entryTextField.setText("");//reset text here
			for(int i = 0; i < 16; i++){
				emojiButtons[i].setEnabled(false);
				imageButtons[i].setEnabled(true);
			}
			clicksLabel.setText(model.getLandscapeEntered() + " images clicked");
			clicksLabel.setVisible(true);
			submitButton.setEnabled(false);
		}
		else if(model.getPasswordState() == MainModel.PW_STATE.WORDS){
			entryTextField.setEnabled(true);
			for(int i = 0; i < 16; i++){
				emojiButtons[i].setEnabled(false);
				imageButtons[i].setEnabled(false);
			}
			clicksLabel.setVisible(false);
			submitButton.setEnabled(false);
		}
		else{
			entryTextField.setEnabled(false);
			for(int i = 0; i < 16; i++){
				emojiButtons[i].setEnabled(true);
				imageButtons[i].setEnabled(false);
			}
			clicksLabel.setText(model.getEmojiEntered() + " emojis clicked");
			clicksLabel.setVisible(true);
			submitButton.setEnabled(true);
		}
	}

	
}
