package controller;

import map.populationHub.PopulationHub;
import player.Player;
import resource.Resources;
import weapon.missile.baseMissile.Missile;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

public final class Controller {

	private static Controller instance;
	private static Turn turnInstance;
	private static ArrayList<Player> players = null;
	private static int numPlayers = -1;
	private static ArrayList<Player> winners = null;

	private static TreeMap<String, File> savedGames = new TreeMap<>();
	private static TreeMap<String, Player> previousPlayerNames;
	private static File previousPlayerNamesFile;

	private Controller () { this(2); }
	private Controller (int initialPlayers) {
		numPlayers = initialPlayers;
		//initialize all data structures
		this.initDS();
	}
	private Controller initDS() {
		getPlayers();
		PopulationHub.getAllPopHubs();
		Missile.getAllMissilesByID();
		for (Player p : getPlayers()) {
			p.getOwnedCities();
			PopulationHub.getAllPopHubsByPlayer(p);
			Missile.getAllMissilesByPlayer(p);
		}
		System.out.println("Data Structures initialized!");
		return this;
	}
	private Controller initPlayers() {
		int counter = 0;
		for (Player p : getPlayers()) {
			counter++;
			p.setCapital(new PopulationHub(p));
			PopulationHub.getAllPopHubsByPlayer(p).add(p.getCapital());
			p.getCapital().getMissilesBasedHere();
		}
		return this;
	}
	public Controller startGame() {
		this.initPlayers();
		do {} while (!Controller.getTurnInstance().doTurn());
		Controller.winners = new ArrayList<>();
		winners.add(getPlayers().get(0));
		return this;
	}
	/**
	 * Gets the next player id/free-index in the array players
	 * <br>
	 * if a players loses, his/her id is <b>not</b> reused in the current game
	 * @return the next free space in the array of players
	 */
	public static int getNextPlayerID() { return getPlayers().size(); }
	public static int getNumPlayers() { return numPlayers; }
	public static ArrayList<Player> getWinners() { return Controller.winners; }
	public static ArrayList<Player> getPlayers () {
		if (Controller.players == null) {
			players = new ArrayList<>(numPlayers);
			for (int i = 0; i < numPlayers; i++) {
				players.add(new Player());
			}
		}
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
