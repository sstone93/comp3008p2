package model;

import java.util.ArrayList;
import java.util.Collections;

/*
 * This class will be a representation of our password scheme.
 * it will have fields for each part of the password and an equality method
 */
public class Password {
	private ArrayList<String> landscape = new ArrayList<String>(3);
	private ArrayList<String> emojis = new ArrayList<String>(3);
	private String   randomWord;
	
	public Password() {
		randomWord = "";
	}
	
	public Password(String word, ArrayList<String> land, ArrayList<String> emoji ) {
		landscape  = land;
		emojis     = emoji;
		randomWord = word;
	}
	
	// method to compare that two passwords are equal
	public boolean equals(Password pw) {
		if (pw == null){ return false; }
		
		if (! pw.randomWord.contentEquals( this.randomWord)) {
			return false;
		}
		
		Collections.sort(pw.landscape);
		Collections.sort(this.landscape);
		if (! pw.landscape.equals( this.landscape)) {
			return false;
		}
		
		Collections.sort(pw.emojis);
		Collections.sort(this.emojis);
		if (! pw.emojis.equals(this.emojis) ) {
			return false;
		}
		return true ;
	}
	
	// below: getter/setters
	
	public String getRandomWord() {
		return randomWord;
	}
	
	public ArrayList<String> getLandscape() {
		return landscape;
	}
	
	public ArrayList<String> getEmojis() {
		return emojis;
	}
	
	public void setRandomWord(String word) {
		randomWord = word;
	}
	
	public void addLandscape(String image) {
		landscape.add(image);
	}
	
	public void addAllLandscape(ArrayList<String> ls) {
		landscape = ls;
	}
	
	public void addEmoji(String e) {
		emojis.add(e);
	}
	
	public void addAllEmojis(ArrayList<String> es) {
		emojis = es;
	}
		
}