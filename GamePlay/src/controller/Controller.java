package controller;

import player.Player;

import java.util.ArrayList;

public final class Controller {

	private static Controller instance;
	private static Turn turnInstance;
	private static ArrayList<Player> players = null;
	private int numActivePlayers;

	private Controller () { this(1); }

	private Controller (int initalPlayers) {
		this.numActivePlayers = initalPlayers;
		players = new ArrayList<Player>(initalPlayers + 1);
		Controller.getTurnInstance();
	}

	/**
	 * Gets the next player id/free-index in the array players
	 * <br>
	 * if a players loses, his/her id is <b>not</b> reused in the current game
	 * @return the next free space in the array of players
	 */
	public int addPlayer (String name) {
		return players.size() + 1;
	}

	public static ArrayList<Player> getPlayers () {return players; }
	/**
	 * get the number of active players
	 * @return the number of players still in the game
	 */
	public int getNumPlayers () {
		return numActivePlayers;
	}

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
	public int getNumActivePlayers() { return numActivePlayers; }
}
