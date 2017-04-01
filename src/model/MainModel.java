package model;

import java.util.ArrayList;
import java.util.HashMap;

public class MainModel {
	public enum MODE {TRAINING, TESTING, PASSWORD_ENTERED };
	public enum LOGIN_STATUS { SUCCESS, FAILURE};
	public enum TYPE { BANK, FACEBOOK, SCHOOL};
	private HashMap<TYPE, Password> assignedPasswords = new HashMap<TYPE, Password>(3);
	private TYPE currentType;
	private MODE currentMode;
	private LOGIN_STATUS currentStatus;
	private int attempts;
	
	public MainModel() {
		currentType = TYPE.BANK;
		currentMode = MODE.TRAINING;
		currentStatus = LOGIN_STATUS.FAILURE;
		attempts = 0;
	}
	
	public HashMap<TYPE, Password> getAssignedPasswords() {
		return assignedPasswords;
	}
	
	public void addAssignedPassword(TYPE key, Password value) {
		assignedPasswords.put(key, value);
	}
	
	public int getAttempts() {
		return attempts;
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
