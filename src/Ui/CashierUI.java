package Ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import domain.product.ShoppingCartProduct;

public class CashierUI extends JFrame implements Observer {

	public CashierUI(Controller controller, int cartId) {
		super("POS System");
		this.controller = controller;
		this.cartId = cartId;
		controller.addCartObserver(cartId, this);
		this.initComponents();
	}

	JTextField txtItemId, txtQty, txtAmount;
	Controller controller;
	int cartId;
	protected DefaultTableModel itemsModel;

	protected void initComponents() {
		// set params for frame
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 500);
		
		// creating panels
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
		JPanel itemsPanel = new JPanel();

		//creating table components
		String[] columns = { "Id", "Name", "Price", "Qty", "Total" };
		String[][] datas = { { "1", "2", "3", "4", "5" } };
		itemsModel = new PosTableModel();
		//creating table and scrollPane
		JTable items = new JTable(datas, columns);
		items.setModel(itemsModel);
		JScrollPane pane = new JScrollPane(items);
		itemsModel.addTableModelListener(new PosTableModelListener(cartId, controller));
		
		//creating labels, buttons and text fields
		JLabel lblQty = new JLabel("Qty");
		JLabel lblItemId = new JLabel("Product id");
		JLabel lblAmount = new JLabel("Amount");
		JButton btnAdd = new JButton("Add to Cart");
		btnAdd.addActionListener(this.addToCartBtnClick());
		txtItemId = new JTextField(5);
		txtQty = new JTextField(5);
		txtAmount = new JTextField(5);
		txtAmount.setEnabled(false);
		
		// building frame
		// add stuff to itemspanel
		itemsPanel.add(lblItemId);
		itemsPanel.add(txtItemId);
		itemsPanel.add(lblQty);
		itemsPanel.add(txtQty);
		itemsPanel.add(lblAmount);
		itemsPanel.add(txtAmount);
		itemsPanel.add(btnAdd);
		// populating main JPanel
		panel.add(pane, 0);
		panel.add(itemsPanel, 1);
		// and add it to the frame
		this.add(panel);
	}

	public void launch() {
		this.setVisible(true);

	}

	protected ActionListener addToCartBtnClick() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int productId = 0, qty = 0;
				boolean smooth = true;
				try {
					productId = Integer.parseInt(txtItemId.getText());
				} catch (NumberFormatException exc1) {
					JOptionPane.showMessageDialog(null, txtItemId.getText()
							+ " couldn't be parsed.\nPlease input a valid number");
					smooth = false;
				}
				try {
					qty = Integer.parseInt(txtQty.getText());
				} catch (NumberFormatException exc1) {
					JOptionPane.showMessageDialog(null, txtQty.getText()
							+ " couldn't be parsed.\nPlease input a valid number");
					smooth = false;
				}
				try {
					if (smooth)
						controller.addProductToCart(cartId, productId, qty);
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, exc.getMessage());
				}
			}
		};
	}

	@Override
	public void update(Observable o, Object arg) {
		List<ShoppingCartProduct> products = controller.getCartProducts(cartId);

		// clear table
		itemsModel.setRowCount(0);

		// Add all the products again
		// working with i can lead to weird index errors because it's not always
		// obvious
		for (ShoppingCartProduct p : products) {
			itemsModel.addRow(new Object[] { p.getProduct().getId(),
					p.getProduct().getName(), p.getProduct().getPrice(),
					p.getQty(), p.getTotal() });
		}

		// itemsModal.addRow(new Object[] { "Column 1", "Column 2",
		// "Column 3"});

		txtAmount.setText(controller.getTotalAmountFromCart(cartId).toString());
	}
}
