package controller;

import view.View;
import model.*;

public class Controller {
	
	private View view;
	private Password assignedPassword;
	private Password enteredPassword;
	
	public Controller(){
		//TODO: randomly assign a password here
		view = new View(this);
		view.setVisible(true);
		
	}
	
	public void handleSubmit(){
		//TODO: make this do things
	}
	
	public void handleTextEnter(){
		//TODO: entered text to be added to entered password
	}

	public static void main(String[] args) {
		//initialize the controller which opens the window
		Controller controller = new Controller();
	}

}
