package Ui;

public class Launcher {

	public static void main(String[] args) {
		Controller controller = new Controller();
		CartUi ui = new CartUi(controller);
		ui.launch();
		
	}
}
