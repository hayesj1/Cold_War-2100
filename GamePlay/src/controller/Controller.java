package controller;

import player.Player;

import java.util.ArrayList;

public final class Controller {

	private static Controller instance;
	private static Turn turnInstance;
	private static ArrayList<Player> players = null;
	private int numPlayers;

	private Controller () { this(2); }

	private Controller (int initalPlayers) {
		this.numPlayers = 0;
		players = new ArrayList<Player>(initalPlayers);

	}

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public static ArrayList<Player> getPlayers () {return players; }

	public int addPlayer () {
		return numPlayers++;
	}

	public int getNumPlayers () {
		return numPlayers;
	}
	public static Turn getTurnInstance() {
		if (turnInstance == null) {
			turnInstance = Turn.getInstance();
		}
		return turnInstance;
	}
}
