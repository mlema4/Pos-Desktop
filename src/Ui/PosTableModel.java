package Ui;

import javax.swing.table.DefaultTableModel;

public class PosTableModel extends DefaultTableModel {
	static String[] columnNames = { "Id", "Name", "Price", "Qty", "Total" };

	public PosTableModel() {
		super(null, columnNames);
	}

	public boolean isCellEditable(int row, int col) {
		return col == 3;
	}
}
