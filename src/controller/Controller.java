package controller;

import view.View;
import model.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Controller {
	
	private View view;
	private Password assignedPassword; // password system gives them
	private Password enteredPassword; // what they give us
	
	public Controller(){
		//TODO: randomly assign a password here
		assignedPassword = new Password(/*take in assigned values */ );
		
		enteredPassword = new Password();
		view = new View(this);
		view.setVisible(true);
	}
	
	public void handleSubmit(){
		boolean success = assignedPassword == enteredPassword;
		
		// TODO: change the state based off of success
		
		String event = success ? "success" : "failure";
		// log success or failure event
		logEvent("user,scheme,login," + event);	
	}
	
	
	public void handleTextEnter(String textpw){
		enteredPassword.setRandomWord(textpw);
	}
	
	// Input: A string to be logged in the specified format
	// FORMAT: user,scheme,mode,event
	public static void logEvent(String logMe) {
		Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  
 
	    try {  
	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("C:/Users/Chantal/Documents/hcilog/temp.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        Date time = new java.util.Date();	        
	        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        String date = DATE_FORMAT.format(time);
	        
	        logger.info(date + "," + logMe);  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}

	public static void main(String[] args) {
		//initialize the controller which opens the window
		Controller controller = new Controller();
		
		logEvent("hey");
	}

}
