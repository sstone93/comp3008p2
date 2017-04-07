package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import model.*;

/*
This class is the main view for our GUI
*/

@SuppressWarnings("serial")
public class View extends JFrame {
	
	private PasswordPanel passwordPanel;
	private Controller controller;
	private JPanel contentPane;
	private JLabel instituteLabel;

    /*
    Constructor: initializes and displays the view
    */
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
		
        //This label lets the user know what password they are using
		instituteLabel = new JLabel("");
		instituteLabel.setFont (instituteLabel.getFont ().deriveFont (24.0f));
		instituteLabel.setBounds(330, 10, 700, 30);
		contentPane.add(instituteLabel);
		
		update();
	}
	
    /*
    This is updates the view based on the current state of the model
    */
	public void update(){
		MainModel model = controller.getMainModel();
        
        //dialogs are shown on correct and incorrect password attempts and when the user finishes
		if(model.getCurrentMode() == MainModel.MODE.PASSWORD_ENTERED){
			if(model.getCurrentStatus() == MainModel.LOGIN_STATUS.SUCCESS){
				JOptionPane.showMessageDialog(this, "Correct password entered.", "Correct", JOptionPane.PLAIN_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Incorrect password entered.", "Incorrct", JOptionPane.PLAIN_MESSAGE);	
			}
		}
		else if (model.getCurrentMode() == MainModel.MODE.FINISHED){
			JOptionPane.showMessageDialog(this, "You have reached the end. Thank you for participating", "Thanks!", JOptionPane.PLAIN_MESSAGE);
			this.dispose();
		}
        
		else if (model.getCurrentMode() == MainModel.MODE.TRAINING){
			instituteLabel.setText("Practice entering password for: " + model.getCurrentType());
		}
		else{
			instituteLabel.setText("Enter password for: " + model.getCurrentType());

		}
		
		passwordPanel.update();
	}

}
