package Ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CartUi extends JFrame implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextField txtId, txtQty, txtTotal;
	protected JButton btnAdd;
	protected Controller controller;

	protected int currentCartId;

	public CartUi(Controller controller) {
		super("Shop ui");
		this.controller = controller;

		this.currentCartId = controller.createCart(null);
		this.controller.addCartObserver(this.currentCartId, this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(200, 200, 500, 250);

		JPanel main = new JPanel();

		JPanel firstRow = new JPanel(new GridLayout(1, 2));
		JPanel secondRow = new JPanel(new GridLayout(1, 2));

		JLabel lblProductId = new JLabel("ID", SwingConstants.LEFT);
		JLabel lblQty = new JLabel("Qty", SwingConstants.LEFT);
		txtQty = new JTextField(4);

		txtId = new JTextField(4);
		btnAdd = new JButton("Add to Cart");
		btnAdd.addActionListener(this.addToCartClick());

		firstRow.add(lblProductId);
		firstRow.add(txtId);
		firstRow.add(lblQty);
		firstRow.add(txtQty);

		firstRow.add(btnAdd);

		txtTotal = new JTextField(4);
		JLabel totalLabel = new JLabel("Total amount", SwingConstants.LEFT);

		secondRow.add(totalLabel);
		secondRow.add(txtTotal);

		main.add(firstRow);
		main.add(secondRow);
		this.setContentPane(main);

	}

	public void launch() {
		this.setVisible(true);
	}

	private ActionListener addToCartClick() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int productId = Integer.parseInt(txtId.getText());
				int qty = Integer.parseInt(txtQty.getText());

				controller.addProductToCart(currentCartId, productId, qty);
			}
		};
	}

	@Override
	public void update(Observable o, Object arg) {
		this.txtTotal.setText(String.valueOf(controller.getTotalAmountFromCart(this.currentCartId)));

	}

}
