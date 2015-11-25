package Ui;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import db.WebshopFacade;
import domain.ShoppingCart;
import domain.product.Product;

public class Controller {
	private WebshopFacade webshop;

	public Controller() {
		WebshopFacade webshop = new WebshopFacade(this.getProperties());
		this.webshop = webshop;
	}

	private Properties getProperties() {
		Properties properties= new Properties();
		try {
			InputStream is = new FileInputStream("config.env");
			properties.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	public ShoppingCart createCart(String userId) {
		return webshop.createCart(userId);
	}

	public Product getProduct(int productId) {
		return webshop.getProduct(productId);
	}
}
