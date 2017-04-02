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

import controller.Controller;
import model.MainModel;

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
	
	public PasswordPanel(Controller c){
		controller = c;
		
		setBounds(5, 100, 985, 365);
		setBorder(new LineBorder(Color.GRAY));//TODO:remove this later
		setLayout(null);
		
		JLabel label = new JLabel("Enter text");

		label.setFont (label.getFont ().deriveFont (24.0f));
		label.setBounds(400, 110, 180, 30);
		add(label);
		
		entryTextField = new JPasswordField("");
		entryTextField.setBounds(400, 150, 180, 30);
		entryTextField.setFont(entryTextField.getFont().deriveFont(24.0f));
		entryTextField.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e){
				String t = new String(entryTextField.getPassword());
				controller.handleTextEnter(t);
				// TODO: log end time in controller
			}
			public void focusGained(FocusEvent e){
				//do nothing, we don't care about this.
				// TODO: log start time in controller
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
		
		imageButtons = new JButton[16];
		
		for (int i = 0; i < 16; i++){
			
			try{
				BufferedImage buttonIcon = ImageIO.read(this.getClass().getResource("/resources/l" + (i+1) + ".jpg"));
				imageButtons[i] = new JButton(new ImageIcon(buttonIcon));
			}catch(IOException e){
				System.out.println("IT DIDN'T WORK");
			}
			
			final String id = "i" + (i + 1);
			
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
		
		for (int i = 0; i < 16; i++){
			//System.out.println(this.getClass().getResource("/resources/words.txt"));
			
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
				//controller.something chantal will make me a thing
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
		add(showCheckBox);
	}
	
	public void update(){
		System.out.println("Update");
		MainModel model = controller.getMainModel();
		
	}

	
}
