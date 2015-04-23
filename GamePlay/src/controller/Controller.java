package controller;

import map.populationHub.PopulationHub;
import player.Player;

import javax.swing.*;
import javax.vecmath.Point2d;
import java.util.ArrayList;

public final class Controller {

	private static Controller instance;
	private static Turn turnInstance;
	private static ArrayList<Player> players = null;

	private Controller () { this(1); }

	private Controller (int initialPlayers) {
		players = new ArrayList<Player>(initialPlayers + 1);
		for (int i = 0; i < initialPlayers; i++) {
			players.add(new Player(JOptionPane.showInputDialog(null, "Enter a name for player #" + (i + 1), "Player name", JOptionPane.QUESTION_MESSAGE)));
		}
		int counter = 0;
		for (Player p : players) {
			if (p == null) { break; }
			p.setCapital(new PopulationHub(p, new Point2d(counter + 2.0, counter + 2.0)));
		}
		Controller.getTurnInstance();
	}

	/**
	 * Gets the next player id/free-index in the array players
	 * <br>
	 * if a players loses, his/her id is <b>not</b> reused in the current game
	 * @return the next free space in the array of players
	 */
	public static int addPlayer (String name) {
		return players.size() + 1;
	}

	public static ArrayList<Player> getPlayers () {return players; }

	public static Controller getInstance() {
		if (instance == null) {
			//TODO ask the user for the number of players
			instance = new Controller();
		}
		return instance;
	}
	public static Turn getTurnInstance() {
		if (turnInstance == null) {
			turnInstance = Turn.getInstance();
		}
		return turnInstance;
	}
}
