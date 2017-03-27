package controller;


import view.View;
import model.*;
import java.util.logging.*;

public class Controller {
	
	private View view;
	private Password assignedPassword; // password system gives them
	private Password enteredPassword; // what they give us
	
	public Controller(){
		//TODO: randomly assign a password here
		assignedPassword = new Password(/*take in assigned values */ );
//		private final static Logger logger = Logger.getLo
		
		enteredPassword = new Password();
		view = new View(this);
		view.setVisible(true);
		
	}
	
	public void handleSubmit(){
		boolean success = assignedPassword == enteredPassword;
		
		// TODO: change the state based off of success
		
		// log success or failure event
		
		
	}
	
	public void handleTextEnter(String textpw){
		enteredPassword.setRandomWord(textpw);
	}

	public static void main(String[] args) {
		//initialize the controller which opens the window
		Controller controller = new Controller();
	}

}
