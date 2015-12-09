package Ui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Milan Sanders, Vijay Sapkota
 *
 */
public class CustomerUI extends JFrame implements Observer {

	JTextField txtAmount;
	Controller controller;
	int cartId;

	public CustomerUI(Controller controller, int cartId) {
		super("POS System");
		this.controller = controller;
		this.cartId = cartId;
		controller.addCartObserver(cartId, this);
		this.initComponents();
	}

	protected void initComponents() {
		// set params for frame
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(100, 100);

		// creating panels
		JPanel panel = new JPanel();

		// creating labels and text fields
		JLabel lblAmount = new JLabel("Amount");
		txtAmount = new JTextField(5);
		txtAmount.setEnabled(false);

		// building panel
		panel.add(lblAmount);
		panel.add(txtAmount);
		// and add it to the frame
		this.add(panel);
	}

	public void launch() {
		this.setVisible(true);

	}


	@Override
	public void update(Observable o, Object arg) {
		txtAmount.setText(round(controller.getTotalAmountFromCart(cartId)).toString());
	}

	private Double round(double val) {
		val *= 100;
		val = Math.round(val);
		return val / 100;
	}

}
