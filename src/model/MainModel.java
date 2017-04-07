package model;

import java.util.ArrayList;
import java.util.HashMap;


/*
 * This class will be a representation of our password scheme.
 * it will have fields for each part of the password and an equality method
 */
public class MainModel {
	public enum MODE {TRAINING, TESTING, PASSWORD_ENTERED, FINISHED };
	public enum LOGIN_STATUS { SUCCESS, FAILURE}; // do the passwords match or not?
	public enum TYPE { BANK, FACEBOOK, SCHOOL};
	public enum PW_STATE { LANDSCAPE, WORDS, EMOJI }; // indicates which part of the password user is entering
	private HashMap<TYPE, Password> assignedPasswords = new HashMap<TYPE, Password>(3);
	private TYPE currentType;
	private MODE currentMode;
	private PW_STATE currentPwState;
	private LOGIN_STATUS currentStatus;
	private int attempts;
	private int landscapeEntered;
	private int emojiEntered;
	private String userID;
	private boolean canMoveOn;
	
	public MainModel() {
		currentType = TYPE.FACEBOOK; // the site user is entering the password for currently
		currentMode = MODE.TRAINING; 
		currentStatus = LOGIN_STATUS.FAILURE; 
		currentPwState = PW_STATE.LANDSCAPE; 
		attempts = 0; // number of attempts to enter a password during testing
		landscapeEntered = 0; // count for the number of landscape images clicked
		emojiEntered = 0; // count for the number of emojis clicked
		userID = ""; // user id of the user, used for logging
		canMoveOn = false; // can the user move to the next state?
	}
	
	// BELOW: getter/setters
	
	public HashMap<TYPE, Password> getAssignedPasswords() {
		return assignedPasswords;
	}
	
	public void addAssignedPassword(TYPE key, Password value) {
		assignedPasswords.put(key, value);
	}
	
	public void changePasswordState(PW_STATE newPw) {
		currentPwState = newPw;
	}
	
	public PW_STATE getPasswordState() {
		return currentPwState;
	}
	
	public void allowMoveOn() {
		canMoveOn = true;
	}
	
	public void noMoving() {
		canMoveOn = false;
	}
	
	public boolean getCanMoveOn() {
		return canMoveOn;
	}
	
	public int getLandscapeEntered() {
		return landscapeEntered;
	}
	
	public void addLandscapeEntered() {
		landscapeEntered++;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String u) {
		userID = u;
	}
	
	public int getEmojiEntered() {
		return emojiEntered;
	}
	
	public void addEmojiEntered() {
		emojiEntered++;
	}
	
	public void resetEmojiAndLandscape() {
		emojiEntered = 0;
		landscapeEntered = 0;
	}
	
	public int getAttempts() {
		return attempts;
	}
	
	public void resetAttempts() {
		attempts = 0;
	}
	
	public void addAttempt() {
		attempts++;
	}
	
	public TYPE getCurrentType() {
		return currentType;
	}
	
	public MODE getCurrentMode() {
		return currentMode;
	}
	
	public LOGIN_STATUS getCurrentStatus() {
		return currentStatus;
	}
	
	public void changeLoginStatus(LOGIN_STATUS newStatus) {
		currentStatus = newStatus;
	}
	
	public void changeCurrentMode(MODE newMode) {
		currentMode = newMode;
	}
	
	public void changeCurrentType(TYPE newType) {
		currentType = newType;
	}
	
}
