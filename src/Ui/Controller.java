package Ui;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Observer;
import java.util.Properties;

import db.WebshopFacade;
import domain.product.Product;
import domain.product.ShoppingCartProduct;

public class Controller {
	private WebshopFacade webshop;

	public Controller() {
		WebshopFacade webshop = new WebshopFacade(this.getProperties());
		this.webshop = webshop;
	}

	private Properties getProperties() {
		Properties properties = new Properties();
		try {
			InputStream is = new FileInputStream("config.env");
			properties.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public int createCart(String userId) {
		return webshop.createCart(userId).getId();
	}

	public Product getProduct(int productId) {
		return webshop.getProduct(productId);
	}

	public void addProductToCart(int currentCartId, int productId, int qty) {
		ShoppingCartProduct product = new ShoppingCartProduct(webshop.getProduct(productId), qty);
		webshop.getCart(currentCartId).addProduct(product);
	}

	public void addCartObserver(int currentCartId, Observer cartUi) {
		this.webshop.getCart(currentCartId).addObserver(cartUi);
	}

	public Double getTotalAmountFromCart(int currentCartId) {
		// TODO Auto-generated method stub
		return this.webshop.getCart(currentCartId).getTotalPrice();
	}
	
	public List<ShoppingCartProduct> getCartProducts(int cartId){
		return this.webshop.getCart(cartId).getProducts();
	}
}
