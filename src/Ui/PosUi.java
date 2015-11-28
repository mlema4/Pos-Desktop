package Ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import domain.product.ShoppingCartProduct;

public class PosUi extends JFrame implements Observer {

	public PosUi(Controller controller) {
		super("POS System");
		this.controller = controller;
		cartId = controller.createCart("1");
		controller.addCartObserver(cartId, this);
		this.initComponents();
	}

	JTextField txtItemId, txtQty, txtAmount;
	Controller controller;
	int cartId;
	protected DefaultTableModel itemsModal;

	protected void initComponents() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 500);

		JPanel panel = new JPanel();

		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
		this.add(panel);

		String[] columns = { "Id", "Name", "Price", "Qty", "Total" };

		String[][] datas = { { "1", "2", "3", "4", "5" } };

		JTable items = new JTable(datas, columns);
		itemsModal = new DefaultTableModel(columns, 0);
		items.setModel(itemsModal);
		itemsModal.addRow(datas);
		JScrollPane pane = new JScrollPane(items);
		panel.add(pane, 0);

		JPanel itemsPanel = new JPanel();
		itemsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));

		JLabel lblQty = new JLabel("Qty");
		JLabel lblItemId = new JLabel("Product id");

		txtItemId = new JTextField(5);
		txtQty = new JTextField(5);

		JLabel lblAmount = new JLabel("Amount");
		txtAmount = new JTextField(5);
		txtAmount.setEnabled(false);

		itemsPanel.add(lblItemId);
		itemsPanel.add(txtItemId);
		itemsPanel.add(lblQty);
		itemsPanel.add(txtQty);

		JPanel itemsPanel2 = new JPanel();
		itemsPanel2.add(lblAmount);
		itemsPanel2.add(txtAmount);
		JButton btnAdd = new JButton("Add to Cart");
		btnAdd.addActionListener(this.addToCartBtnClick());
		itemsPanel2.add(btnAdd);

		panel.add(itemsPanel);
		itemsPanel.add(itemsPanel2);
	}

	public void launch() {
		this.setVisible(true);

	}

	protected ActionListener addToCartBtnClick() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int productId = Integer.parseInt(txtItemId.getText());
				int qty = Integer.parseInt(txtQty.getText());

				controller.addProductToCart(cartId, productId, qty);
			}
		};
	}

	@Override
	public void update(Observable o, Object arg) {
		List<ShoppingCartProduct> products = controller.getCartProducts(cartId);
		for (int i = 0; i < itemsModal.getRowCount(); i++) {
			itemsModal.removeRow(i);
		}

		for (int i = 0; i < products.size(); i++) {
			ShoppingCartProduct p = products.get(i);
			itemsModal.addRow(new Object[] { p.getProduct().getId(), p.getProduct().getName(),
					p.getProduct().getPrice(), p.getQty(), p.getTotal() });
		}

		// itemsModal.addRow(new Object[] { "Column 1", "Column 2", "Column 3"
		// });
		txtAmount.setText(controller.getTotalAmountFromCart(cartId).toString());
	}
}
