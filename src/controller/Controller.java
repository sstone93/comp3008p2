package controller;

import view.View;

public class Controller {
	
	private View view;
	
	public Controller(){
		view = new View(this);
		view.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller controller = new Controller();
	}

}
