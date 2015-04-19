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

	private Turn () {}

	private void doTurn () {
		currTurn++;
		for (Player p : Controller.getPlayers()) {
			for (PopulationHub ph : PopulationHub.getPlayersPopHubs(p)) {
				ph.produce();
			}
		}
	}

	public static Turn getInstance () {
		if (instance == null) {
			instance = new Turn();
		}
		return instance;
	}
}
