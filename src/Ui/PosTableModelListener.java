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
			int rowIndex = event.getFirstRow();
			PosTableModel model = (PosTableModel) event.getSource();
			int newQuantity = Integer.parseInt((String)model.getValueAt(rowIndex, event.getColumn()));
			int productId = Integer.parseInt((String)model.getValueAt(rowIndex, 5));
			try {
				controller.alterQuantity(cartId, productId, newQuantity);
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} finally {
				controller.updateCart(cartId);
			}
		}
	}

}
