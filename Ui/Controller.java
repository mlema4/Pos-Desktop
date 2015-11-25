package Ui;

import java.util.Properties;

import db.WebshopFacade;

public class Controller {
	private WebshopFacade webshop;

	public Controller() {
		WebshopFacade webshop = new WebshopFacade(this.getProperties());
		this.webshop = webshop;
	}

	private Properties getProperties() {
		return null;
	}
}
