package controller;

import view.View;
import model.*;

import java.io.File;
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
	
	public void handleEmojiClicked(String emojiID){
		//add emoji to list of emojis
	}
	
	public void handleLandscapeClicked(String landscapeID){
		//add landscape to the list of landscapes
	}
	
	// Input: A string to be logged in the specified format
	// FORMAT: user,scheme,mode,event
	public static void logEvent(String logMe) {
		Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  
 
	    try {  
	    	String filename = "logFile.txt";
			String workingDirectory = System.getProperty("user.dir");

			String absoluteFilePath = "";
			absoluteFilePath = workingDirectory + File.separator + filename;

			System.out.println("Final filepath : " + absoluteFilePath);
	    	
	        fh = new FileHandler(absoluteFilePath);  
	        logger.addHandler(fh);

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
