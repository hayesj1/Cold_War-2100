package testGame;

import game.ColdWar2100;
import net.client.Client;
import net.server.Server;
import net.server.ServerThread;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Driver {

	public static void main(String[] args) {
		Driver driver = new Driver(args);
	}
	private Driver(String[] args) {
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
	private void localGame() {
		try {
			ServerThread server = new ServerThread(new Socket(InetAddress.getLoopbackAddress(), Server.PORT));
			server.run();
			do {
				this.wait();
			} while(ColdWar2100.getWinners() == null);
		} catch (IOException e) {
			System.out.println(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Winner is: " + ColdWar2100.getWinners().get(0));
	}
	private void networkedGame(String serverOrClient) {
		try {
			Server server = new Server(Server.PORT);
			Client client = new Client();

			switch (serverOrClient) {
				case "SERVER":
					server.listen();
					break;
				case "CLIENT":
					client.connect(InetAddress.getByName(JOptionPane.showInputDialog("Enter the IP Address of the server: ")));
					if (client.connected()) {
						client.init();
						client.evaulateTurn();
					}
					break;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
