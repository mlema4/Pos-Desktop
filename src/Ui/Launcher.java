package Ui;

import domain.ShoppingCart;

public class Launcher {

	public static void main(String[] args) {
		CartUi ui = new CartUi(new ShoppingCart("1"));
		
	}
}
