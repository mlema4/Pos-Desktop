package Ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 
 * @author Vijay Sapkota
 *
 */
public class CashierUiWindowListener implements WindowListener {
	Controller controller;

	public CashierUiWindowListener(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		controller.shutDown();
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
