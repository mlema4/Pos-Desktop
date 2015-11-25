package Ui;

import db.WebshopFacade;
import domain.ShoppingCart;

public class Launcher {

	public static void main(String[] args) {
		Controller controller = new Controller();
		CartUi ui = new CartUi(new ShoppingCart("1"));
		
	}
}
