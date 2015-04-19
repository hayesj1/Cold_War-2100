package player;

import controller.Controller;
import map.populationHub.PopulationHub;

public class Player {

	protected int playerID = -1;
	protected String playerName = "";

	protected PopulationHub capital = null;

// TODO remove player name literal, and ask for a name
	public Player() {
		this.playerID = Controller.getInstance().addPlayer("Bob");
	}

	public PopulationHub getCapital () { return this.capital; }
}
