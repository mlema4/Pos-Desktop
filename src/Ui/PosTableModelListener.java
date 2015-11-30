package Ui;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class PosTableModelListener implements TableModelListener {
	
	private final int cartId;
	private final Controller controller;
	
	public PosTableModelListener(int cartId, Controller controller) {
		this.cartId = cartId;
		this.controller = controller;
	}

	@Override
	public void tableChanged(TableModelEvent event) {
		if (event.getType() == 0) {
			int productIndex = event.getFirstRow();
			PosTableModel model = (PosTableModel) event.getSource();
			int newQuantity = Integer.parseInt((String)model.getValueAt(productIndex, event.getColumn()));
			try {
				controller.alterQuantity(cartId, productIndex, newQuantity);
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} finally {
				controller.updateCart(cartId);
			}
		}
	}

}
