package Ui;

public class Launcher {

	public static void main(String[] args) {
		Controller controller = new Controller();
		PosUi ui = new PosUi(controller);
		
		ui.launch();

	}
}
