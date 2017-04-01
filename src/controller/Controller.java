package controller;

import view.View;
import model.*;
import model.MainModel.LOGIN_STATUS;
import model.MainModel.MODE;
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
import java.util.Set; 

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
	
	public Set<TYPE> createRandomOrder() {
		 Set<TYPE> keys = mainModel.getAssignedPasswords().keySet();
		 return keys;
	}
	
	public void handleSubmit(){
		Password assignedPassword = getPasswordBasedOnType();
		boolean success = assignedPassword == enteredPassword;
		
		// TODO: change the state based off of success
		mainModel.changeCurrentMode(MODE.PASSWORD_ENTERED);
		
		String event = success ? "success" : "failure";
		if (success) {
			mainModel.changeLoginStatus(LOGIN_STATUS.SUCCESS);
		} else {
			mainModel.changeLoginStatus(LOGIN_STATUS.FAILURE);
			mainModel.addAttempt();
		}
		
		
//		TODO: uncomment this when view has it
//		view.update();
		
		MODE currentMode = mainModel.getCurrentMode();
		
		if (currentMode == MODE.TRAINING) {
			if (success) {
//				logEvent("user,ourScheme,login," + event);
				mainModel.addAttempt();
				if (mainModel.getAttempts() >= 2) {
					// changeModeTraining(); 
				}
			}
			
		} else if (currentMode == MODE.TESTING) {
			
			if (success) {
				logEvent("user,ourScheme,login," + event);
				changeModeTesting();
				
			} else {
				if (mainModel.getAttempts() > 3) {
					// log failure event
					logEvent("user,ourScheme,login," + event);	
					changeModeTesting();
				}
			}
		}
	}
	
	public boolean changeModeTesting() {
		if (randomOrder.size() == 1) { // this was the last type
			mainModel.changeCurrentMode(MODE.FINISHED);
			mainModel.resetAttempts();
			return false;
		}
		randomOrder.remove(0);
		mainModel.changeCurrentType(randomOrder.get(0));
		return true;
	}
	
	public void handleTextEnter(String textpw){
		enteredPassword.setRandomWord(textpw);
		//TODO: update that password has been entered
	}
	
	//adds emoji to list of emojis in the users enteredPassword
	public void handleEmojiClicked(String emojiID){
		enteredPassword.addEmoji(emojiID);
		//TODO: update number of landscapes clicked
	}
	
	//add landscape to the list of landscapes in the users entered password
	public void handleLandscapeClicked(String landscapeID){
		enteredPassword.addLandscape(landscapeID);
		//TODO: update number of landscapes clicked
	}
	
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
	
	public MainModel getMainModel() {
		return mainModel;
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
