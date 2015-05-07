import controller.Controller;

public class Driver {

	public static void main(String[] args) {
		Controller.getInstance().startGame();
		System.out.println("Winner is: " + Controller.getWinners().get(0));
	}

}
