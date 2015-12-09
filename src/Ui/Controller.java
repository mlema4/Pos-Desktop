package Ui;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Observer;
import java.util.Properties;

import javax.swing.JOptionPane;

import domain.WebshopFacade;
import domain.shoppingcartproduct.ShoppingCartProduct;

/**
 * 
 * @author Milan Sanders
 *
 */
public class Controller {
	private WebshopFacade webshop;
	CashierUI cashierUi;
	CustomerUI costumerUi;

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

	public void addProductToCart(int currentCartId, int productId, int qty) {
		webshop.addProductToCart(currentCartId, productId, qty);
	}

	public void addCartObserver(int cartId, Observer cartUi) {
		webshop.addCartObserver(cartId, cartUi);
	}

	public Double getTotalAmountFromCart(int cartId) {
		return webshop.getTotalFromCart(cartId);
	}

	public List<ShoppingCartProduct> getCartProducts(int cartId) {
		return webshop.getProductsFromCart(cartId);
	}

	public void initUI() {
		int cartId = createCart(null);

		this.cashierUi = new CashierUI(this, cartId);
		this.costumerUi = new CustomerUI(this, cartId);

		this.cashierUi.launch();
		this.costumerUi.launch();
	}

	public void shutDown() {
		webshop.deleteCart(cashierUi.cartId);
		costumerUi.dispose();
		cashierUi.dispose();
	}

	public void alterQuantity(int cartId, int productId, int newQuantity) {
		webshop.alterProductInCart(cartId, productId, newQuantity);
	}

	public void updateCart(int cartId) {
		webshop.reportChangesInCart(cartId);
	}

	public void addDiscount(int cartId, String code) {
		webshop.addDiscountToCart(cartId, code);
	}

	public String getAppliedDiscountCode(int cartId) {
		return webshop.getDiscountCode(cartId);
	}

	public void endSale(int cartId) {
		double amount = webshop.getTotalFromCart(cartId);
		boolean hasPaid = false;
		while (!hasPaid) {
			Double paid = getPaidAmount(amount);
			if (paid == null)
				return;
			try {
				webshop.pay(cartId, paid);
				hasPaid = true;
				reset();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}

	}

	private Double getPaidAmount(double amount) {
		boolean success = false;
		double paid = 0;
		while (!success) {
			String amt = JOptionPane.showInputDialog(null,
					"Amount to pay: " + amount + " \n Please enter the amount: ");
			if (amt == null)
				return null;
			try {
				paid = Double.parseDouble(amt);
				success = true;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please give a valid number");
			}
		}
		return paid;
	}

	private void reset() {
		this.cashierUi.dispose();
		this.costumerUi.dispose();

		initUI();
	}

}
