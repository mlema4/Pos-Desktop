package Ui;

import javax.swing.table.DefaultTableModel;
/**
 * 
 * @author Milan Sanders
 *
 */
public class PosTableModel extends DefaultTableModel {

	public PosTableModel(String[] columns) {
		super(null, columns);
	}

	public boolean isCellEditable(int row, int col) {
		return col == 3;
	}
}
