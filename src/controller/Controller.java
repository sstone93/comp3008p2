package controller;

import view.View;
import model.*;
import model.MainModel.LOGIN_STATUS;
import model.MainModel.MODE;
import model.MainModel.PW_STATE;
import model.MainModel.TYPE;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.*;
import java.util.Scanner;
import java.io.Writer;
import java.io.FileWriter;
import java.util.Set; 

/*
 * This class will control the interaction between the password model and the view
 * It has functions to update the password state as the user progresses
 */
public class Controller {
	
	private View view;
	private MainModel mainModel;
	private Password assignedPasswordFB; // password system gives them
	private Password assignedPasswordBank; // password system gives them
	private Password assignedPasswordSchool; // password system gives them
	private Password enteredPassword; // what they give us
	private ArrayList<TYPE> randomOrder = new ArrayList<TYPE>(6);
	
	public Controller() {
		
		mainModel = new MainModel();
		mainModel.setUserID(getFileUserID());
		
		for (int i = 0; i < 3; i++) {
			String randomWord = getRandomWord();
			ArrayList<String> assignedEmojis = getThreeNumbers("e");
			ArrayList<String> assignedLandscape = getThreeNumbers("l");
			
			if (i == 0) {
				assignedPasswordFB = new Password(randomWord, assignedLandscape, assignedEmojis );
				mainModel.addAssignedPassword(TYPE.FACEBOOK, assignedPasswordFB); 
			} else if (i == 1) {
				assignedPasswordBank = new Password(randomWord, assignedLandscape, assignedEmojis);
				mainModel.addAssignedPassword(TYPE.BANK, assignedPasswordBank);
			} else {
				assignedPasswordSchool = new Password(randomWord, assignedLandscape, assignedEmojis);
				mainModel.addAssignedPassword(TYPE.SCHOOL, assignedPasswordSchool);
			}
		}
		// Create the random order in which passwords will be tested
		ArrayList<TYPE> passwordOrder = new ArrayList<TYPE>();
		passwordOrder.addAll(createRandomOrder());
		Collections.shuffle(passwordOrder);
		
		randomOrder.addAll(passwordOrder);
		Collections.shuffle(passwordOrder);
		randomOrder.addAll(passwordOrder);
		
		enteredPassword = new Password();
		
		view = new View(this);
		view.setVisible(true);
		
	}
	
	// Creates a random 
	public Set<TYPE> createRandomOrder() {
		 Set<TYPE> keys = mainModel.getAssignedPasswords().keySet();
		 return keys;
	}
	
	// Function that handles when the user presses "submit"
	// Changes the state and status depending on the interaction the user had
	public void handleSubmit(){
		Password assignedPassword = getPasswordBasedOnType();
		boolean success = assignedPassword.equals(enteredPassword);
		
		String event = success ? "success" : "failure";
		MODE oldMode = mainModel.getCurrentMode();
		mainModel.changeCurrentMode(MODE.PASSWORD_ENTERED);
		
		if (success) {
			mainModel.changeLoginStatus(LOGIN_STATUS.SUCCESS);
		} else {
			mainModel.changeLoginStatus(LOGIN_STATUS.FAILURE);
			mainModel.addAttempt();
		}
		view.update();

		mainModel.changeCurrentMode(oldMode);
		MODE currentMode = mainModel.getCurrentMode();
		
		if (currentMode == MODE.TRAINING) {
			if (success) {
//				logEvent(mainModel.getUserID() + ",ourScheme,login," + event);
				mainModel.addAttempt();
				if (mainModel.getAttempts() >= 2) {
					mainModel.allowMoveOn(); // the user CAN move on, but they may want to train more.
				}
			}
			
		} else if (currentMode == MODE.TESTING) {

			// log success or failure event
            String userID = mainModel.getUserID();
            
            if (success) {
 				logEvent(userID + ",ourScheme,login," + event);
 				changeModeTesting();
            	 				
 			} else {
 				if (mainModel.getAttempts() >= 3) {		  				
 					if (mainModel.getAttempts() >= 3) {				
 						// log failure event
	 					logEvent(userID + ",ourScheme,login," + event);
	 					
	 					changeModeTesting();
	 					oldMode = mainModel.getCurrentMode();
 					}
 				}
 			}
		}
		
		mainModel.changePasswordState(PW_STATE.LANDSCAPE);
		enteredPassword = new Password();
		mainModel.resetEmojiAndLandscape();
		view.update();
		mainModel.changeCurrentMode(oldMode);
		mainModel.changeLoginStatus(LOGIN_STATUS.FAILURE);
	}
	
	// Handles on click even for next button during training
	public void handleNext() {
		changeModeTraining();
		mainModel.noMoving();
		mainModel.resetEmojiAndLandscape();
		mainModel.resetAttempts();
		enteredPassword = new Password();
		mainModel.changePasswordState(PW_STATE.LANDSCAPE);
		view.update();
	}
	
	// changes the type for when user is training
	// if they've reached the last app to train, it changes the mode to testing
	// and sets the password state
	public void changeModeTraining() {
		if (mainModel.getCurrentType() == TYPE.FACEBOOK) {
			mainModel.changeCurrentType(TYPE.BANK);
		}
		else if (mainModel.getCurrentType() == TYPE.BANK) {
			mainModel.changeCurrentType(TYPE.SCHOOL);
		} 
		else {
			mainModel.changeCurrentMode(MODE.TESTING);
			mainModel.changeCurrentType(randomOrder.get(0));
		}
	}
	
	
	// changes the type for when user is testing
	// if they've reached the last app to train, it changes the mode to finished
	public void changeModeTesting() {
		if (randomOrder.size() == 1) { // this was the last type
			mainModel.changeCurrentMode(MODE.FINISHED);
		}else {
			randomOrder.remove(0);
			mainModel.changeCurrentType(randomOrder.get(0));
		}
		mainModel.resetAttempts();
	}
	
	// Handles when a user enters the text password
	// This updates the text for the entered password
	public void handleTextEnter(String textpw){
		if (textpw.length() == 6){
			enteredPassword.setRandomWord(textpw);
			mainModel.changePasswordState(PW_STATE.EMOJI);
			view.update();
		}
		else{
			System.out.println("HandleTextEnter called, string not long enough.");
		}
	}
	
	//adds emoji to list of emojis in the users enteredPassword
	public void handleEmojiClicked(String emojiID){
		enteredPassword.addEmoji(emojiID);
		mainModel.addEmojiEntered();
//		if (mainModel.getEmojiEntered() >= 3) {
//			mainModel.resetAttempts();
//		}
		view.update();
	}
	
	//add landscape to the list of landscapes in the users entered password
	public void handleLandscapeClicked(String landscapeID){
		enteredPassword.addLandscape(landscapeID);
		
		mainModel.addLandscapeEntered(); // updates number of times user has clicked
		
		if (mainModel.getLandscapeEntered() == 1) {
			String userID = mainModel.getUserID();
	        String event = "start"; // TODO: ask Shannon what need
	        logEvent(userID + ",scheme,enter," + event);
		}
		
		if (mainModel.getLandscapeEntered() >= 3) {
			mainModel.changePasswordState(PW_STATE.WORDS);
//			mainModel.resetAttempts();
		}
        view.update();
	}
	
    // grabs the correct password based on what password user is trying to enter
	public Password getPasswordBasedOnType() {
		TYPE ct = mainModel.getCurrentType();
		if (ct == TYPE.FACEBOOK) {
			return assignedPasswordFB;
		} else if (ct == TYPE.BANK) {
			return assignedPasswordBank;
		} else {
			return assignedPasswordSchool;
		}
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
	
	// Allows the view to access the mainModel class
	public MainModel getMainModel() {
		return mainModel;
	}
	
	// grabs a random word from a text file
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
	
	// Grabs the user id from a text file and updates it
	public String getFileUserID() {
        Scanner x;
        int userNum;
        String userID = "";
        String y;
        MODE currentMode = mainModel.getCurrentMode();
        try{
            x = new Scanner(new File("./src/resources/user.txt"));
            y = x.nextLine();
            userID = "user" + y;
            userNum = Integer.parseInt(y);
                		
			if (currentMode != MODE.PASSWORD_ENTERED) {
	           userNum++;
	        }
                
            Writer wr = new FileWriter("./src/resources/user.txt");
            wr.write(String.valueOf(userNum));
            wr.flush();
            wr.close();
            x.close();
         }
        catch (Exception e){
            System.out.println(e);
        }
        
        return userID;
	}
	
	// gets three unique numbers between 1 and 16
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
	
	// helper functions, gets random number
	// input: min value, max value
	public static int getRandomNumber(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	public static void main(String[] args) {
		//initialize the controller which opens the window
		Controller controller = new Controller();
		
	}

}
