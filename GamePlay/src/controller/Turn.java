package controller;

import map.populationHub.PopulationHub;
import player.Player;

/**
 * Created by hayesj3 on 4/13/2015.
 */
public final class Turn {

	private int maxTurn = 10;
	private int currTurn = 0;

	private static Turn instance = null;

	private Turn () {
		for (int i = 0; i < Controller.getInstance().getNumActivePlayers(); i++) {
			
		}
		this.doTurn();
	}

	/**
	 * starts a turn, and at the end calls itself; this way the game is in one recursive fucntion
	 * @return true when a player wins, false if there is a tie and the max turns is reached
	 */
	private boolean doTurn () {
		if(currTurn == maxTurn)
			return false;

		currTurn++;
		for (Player p : Controller.getPlayers()) {
			for (PopulationHub ph : PopulationHub.getPlayersPopHubs(p)) {
				ph.produce();
				System.out.println(ph + " is owned by " + ph.getOwner());
			}
		}
		return this.doTurn();
	}

	public static Turn getInstance () {
		if (instance == null) {
			instance = new Turn();
		}
		return instance;
	}
}
