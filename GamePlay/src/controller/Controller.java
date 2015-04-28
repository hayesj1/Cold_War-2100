package controller;

import map.populationHub.PopulationHub;
import player.Player;
import resource.Resources;

import javax.swing.*;
import javax.vecmath.Point2d;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

public final class Controller {

	private static Controller instance;
	private static Turn turnInstance;
	private static ArrayList<Player> players = null;
	private static TreeMap<String, File> savedGames = new TreeMap<>();

	private Controller () { this(2); }

	private Controller (int initialPlayers) {
		for (int i = 0; i < initialPlayers; i++) {
			players.add(new Player((String) JOptionPane.showInputDialog(null, "Enter a name for player #" + (i+1), "Player name",
					JOptionPane.QUESTION_MESSAGE, Resources.iconII, null,("Player" + (i+1)))));
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
	public static int addPlayer () {
		return players.size();
	}

	public static ArrayList<Player> getPlayers () {
		if (Controller.players == null)
			players = new ArrayList<>(2);
		return players;
	}
	public static Controller getInstance() {
		if (instance == null) {
			Integer numPlayers = Integer.valueOf((String) JOptionPane.showInputDialog(null, "How many players?", "How many players?",
					JOptionPane.QUESTION_MESSAGE, Resources.iconII, null, Integer.valueOf(2)));
			instance = new Controller(numPlayers);
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
