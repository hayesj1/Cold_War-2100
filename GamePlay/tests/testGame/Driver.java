package testGame;

import controller.Controller;
import net.client.Client;
import net.server.Server;
import net.server.ServerThread;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Driver {

	public static void main(String[] args) {
		switch (args[0]) {
			case "LOCAL":
				localGame();
				break;
			case "NETWORKED":
				networkedGame(args[1]);
				break;
			default:
				localGame();
				break;
		}
	}

	private static void localGame() {
		try {
			Controller.getInstance().startGame(new ServerThread(new Socket(InetAddress.getLoopbackAddress(), Server.PORT)));
		} catch (IOException e) {}
		System.out.println("Winner is: " + Controller.getWinners().get(0));
	}
	private static void networkedGame(String serverOrClient) {
		try {
			Server server = new Server();
			Client client = new Client();

			switch (serverOrClient) {
				case "SERVER":
					server.listen();
					break;
				case "CLIENT":
					client.connect(InetAddress.getByName(JOptionPane.showInputDialog("Enter the IP Address of the server: ")));
					if (client.connected()) {
						client.beginGame();
					}
					break;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
