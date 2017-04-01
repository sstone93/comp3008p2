package controller;

import view.View;
import model.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.*;
import java.util.Scanner; 

public class Controller {
	
	private View view;
	private Password assignedPassword; // password system gives them
	private Password enteredPassword; // what they give us
	
	public Controller(){
		//TODO: randomly assign a password here
		String randomWord = getRandomWord();
		ArrayList<String> assignedEmojis = getThreeNumbers("e");
		ArrayList<String> assignedLandscape = getThreeNumbers("l");
		
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
	
	public String getRandomWord() {
            Scanner x;
            int randNum;
            int count = 0;
            String randWord = "";
            try{
                
                x = new Scanner(new File("./src/resources/words.txt"));
                randNum = getRandomNumber(0, 1024);
                
                while (count < randNum){
                    count++;
                    randWord = x.nextLine();
                }
            }
            catch (Exception e){
                System.out.println("Could not find file");
            }
            return randWord;
	}
	
	public static ArrayList<String> getThreeNumbers(String letter) {
		int count  = 0;
		int image1 = 0;
		int image2 = 0;
		int image3 = 0;
		ArrayList<String> nums = new ArrayList<String>(3);
		
		while (count < 3) {
			int num = getRandomNumber(1, 16);
			if (image1 == 0) {
				image1 = num;
				count++;
			} else if (image2 == 0) {
				if (checkAllowed(image1, num)){
					image2 = num;
					count++;
				}
			} else if (image3 == 0) {
				if (checkAllowed(image1, num) && checkAllowed(image2, num)){
					image3 = num;
					count++;
				}
			}
		}
		
		Collections.addAll(nums, letter+image1, letter+image2, letter+image3);
		return nums;
	}
	
	// OUTPUT: true - two integers are not equal
	// 		   false - two integers are equal
	public static boolean checkAllowed(int current, int suggested) {
		if (current != suggested) {
			return true;
		}
		return false;
	}
	
	public static int getRandomNumber(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	public static void main(String[] args) {
		//initialize the controller which opens the window
		Controller controller = new Controller();
		
	}

}
