package controller;

import view.View;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.*;

public class Controller {
	
	private View view;
	private Password assignedPassword; // password system gives them
	private Password enteredPassword; // what they give us
	
	public Controller(){
		//TODO: randomly assign a password here
//		String randomWord = getRandomWord();
		int[] wee = getThreeNumbers();
		System.out.println(wee);
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

	public static void main(String[] args) {
		//initialize the controller which opens the window
		Controller controller = new Controller();
		
	}
	
	public String getRandomWord() {
//		InputStream fis = new FileInputString("words");
//		INputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
//		BufferedReader br = new BufferedREader(isr);
		String line32 = "";
		try {
			// TODO: 
//			String absol = new File(".").getAbsolutePath();
//			System.out.println(absol);
			
//			ClassLoader classLoader = getClass().getClassLoader();
//			File file = new File(classLoader.getResource("words").getFile());
			
//			String workingDirectory = System.getProperty("user.dir");
			File file = new File("../../resources/words.txt");
			
			System.out.println("delete this when you get it working");
			URL f = Controller.class.getClassLoader().getResource(file.getPath());
			System.out.println(file);
//			line = Files.readAllLines(Paths.get(file.getPath()));
			
			line32 = Files.readAllLines(Paths.get(file.getPath())).get(32);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(line32);
		return line32;
	}
	
	public void getRandomEmojis() {
		int[] nums = getThreeNumbers();
		
	}
	
	public void getRandomLandscape() {
		int[] nums = getThreeNumbers();
		
	}
	
	public static int[] getThreeNumbers() {
		int count  = 0;
		int image1 = 0;
		int image2 = 0;
		int image3 = 0;
		
		System.out.println("heyyo");
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
		int[] three = {image1, image2, image3};
		return three;
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

}
