package testGame;

import main.java.com.jhayes.game.ColdWar2100;
import main.java.com.jhayes.net.client.Client;
import main.java.com.jhayes.net.server.Server;
import main.java.com.jhayes.net.server.ServerThread;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Driver {

	public static void main(String[] args) {
		Driver driver = new Driver(args);
	}
	private Driver(String[] args) {
		networkedGame();
	}
	private void localGame() {
		try {
			Server server = Server.getInstance(2);
			ServerThread serverThread = new ServerThread();
			ColdWar2100 game = new ColdWar2100(serverThread);
			Thread threadedServer = new Thread(new ThreadGroup("Server"), server, "server");

			threadedServer.run();
			serverThread.addClient(new Socket(InetAddress.getLoopbackAddress(), Server.LOCAL_PORT));
			serverThread.run();
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
	private void networkedGame() {
		try {
			Client client = Client.getInstance();
			client.connect(InetAddress.getByName(JOptionPane.showInputDialog("Enter the IP Address of the server:")));
			if (client.connected()) {
				System.out.println("Connected!");
				client.init();
				client.evaluateTurn();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
